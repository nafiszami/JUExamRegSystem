package com.ju.examreg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDashboard extends JFrame {
    private String department;

    // Components for the dashboard
    private JComboBox<String> examComboBox = new JComboBox<>();
    private JTable studentTable = new JTable();
    private JScrollPane studentScrollPane = new JScrollPane(studentTable);
    private JButton viewStudentsButton = new JButton("View Students");
    private JButton logoutButton = new JButton("Logout");

    public DepartmentDashboard(String department) {
        this.department = department;

        setTitle("Department Dashboard - " + department);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("Select Exam:"));
        buttonPanel.add(examComboBox);
        buttonPanel.add(viewStudentsButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(studentScrollPane, BorderLayout.CENTER);

        // Set the student table model with non-editable behavior
        studentTable.setModel(new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Dept Approval", "Hall Approval", "Registrar Approval"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing
            }
        });

        loadExams();

        viewStudentsButton.addActionListener(e -> loadStudents());
        logoutButton.addActionListener(e -> {
            new DepartmentWindow().setVisible(true);
            dispose();
        });

        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    studentTableClicked(evt);
                }
            }
        });
    }

    private void loadExams() {
        examComboBox.removeAllItems();
        String sql = "SELECT exam_id, exam_year, semester FROM exam_info WHERE department_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String examInfo = "ID: " + rs.getInt("exam_id") +
                        " | Year: " + rs.getInt("exam_year") +
                        " | Semester: " + rs.getInt("semester");
                examComboBox.addItem(examInfo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading exams: " + ex.getMessage());
        }
    }

    private void loadStudents() {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        model.setRowCount(0); // Clear previous data
        String selectedExam = (String) examComboBox.getSelectedItem();

        if (selectedExam == null) {
            JOptionPane.showMessageDialog(this, "Please select an exam first.");
            return;
        }

        String examId = selectedExam.split(" ")[1]; // Extract exam_id

        String sql = "SELECT s.student_id, s.student_name, s.email, " +
                "r.department_approval, r.hall_approval, r.registrar_approval " +
                "FROM students s " +
                "JOIN exam_registrations r ON s.student_id = r.student_id " +
                "WHERE r.exam_id = ? AND s.department = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, examId);
            pstmt.setString(2, department);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("email"),
                        rs.getString("department_approval"),
                        rs.getString("hall_approval"),
                        rs.getString("registrar_approval")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + ex.getMessage());
        }
    }

    private void studentTableClicked(MouseEvent evt) {
        int row = studentTable.getSelectedRow();
        if (row == -1) {
            return;
        }

        String studentId = studentTable.getValueAt(row, 0).toString(); // Get student_id
        showStudentDetails(studentId);
    }

    private void showStudentDetails(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(studentId));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String studentInfo = "ID: " + rs.getInt("student_id") +
                        "\nName: " + rs.getString("student_name") +
                        "\nEmail: " + rs.getString("email") +
                        "\nAddress: " + rs.getString("address") +
                        "\nSSC GPA: " + rs.getBigDecimal("ssc_gpa") +
                        "\nHSC GPA: " + rs.getBigDecimal("hsc_gpa");

                showApprovalDialog(studentId, studentInfo);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching student details: " + ex.getMessage());
        }
    }

    private void showApprovalDialog(String studentId, String studentInfo) {
        JDialog dialog = new JDialog(this, "Student Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);

        JTextArea infoArea = new JTextArea(studentInfo);
        infoArea.setEditable(false);

        JPanel buttonPanel = new JPanel();
        JButton approveButton = new JButton("Approve");
        JButton rejectButton = new JButton("Reject");
        JButton closeButton = new JButton("Close");

        approveButton.addActionListener(e -> updateApprovalStatus(studentId, "Approved", dialog));
        rejectButton.addActionListener(e -> updateApprovalStatus(studentId, "Rejected", dialog));
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(closeButton);

        dialog.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void updateApprovalStatus(String studentId, String status, JDialog dialog) {
        String selectedExam = (String) examComboBox.getSelectedItem();
        String examId = selectedExam.split(" ")[1]; // Extract exam_id

        String sql = "UPDATE exam_registrations SET department_approval = ? WHERE student_id = ? AND exam_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, Integer.parseInt(studentId));
            pstmt.setString(3, examId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Status updated to " + status + "!");
                dialog.dispose();
                loadStudents(); // Refresh the student list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update status.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating status: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DepartmentDashboard("CSE").setVisible(true));
    }
}
