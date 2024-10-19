package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrarDashboard extends JFrame {
    private JList<String> studentList;
    private JComboBox<String> examComboBox = new JComboBox<>();
    private JButton viewStudentsButton = new JButton("View Students");
    private JButton logoutButton = new JButton("Logout");

    public RegistrarDashboard() {
        setTitle("Registrar Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel for selecting exams
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Exam:"));
        topPanel.add(examComboBox);
        topPanel.add(viewStudentsButton);
        topPanel.add(logoutButton);
        add(topPanel, BorderLayout.NORTH);

        // Student list setup
        studentList = new JList<>();
        JScrollPane studentScrollPane = new JScrollPane(studentList);
        add(studentScrollPane, BorderLayout.CENTER);

        // Load exams
        loadExams();

        // Action listeners
        viewStudentsButton.addActionListener(e -> loadStudents());
        logoutButton.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });

        // Double-click to show student details
        studentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showStudentDetails();
                }
            }
        });

        // Custom renderer to highlight the selected student
        studentList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    c.setBackground(Color.BLUE);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
    }

    private void loadExams() {
        examComboBox.removeAllItems();
        String sql = "SELECT exam_id, department_name, semester, exam_year FROM exam_info";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String examInfo = "ID: " + rs.getInt("exam_id") +
                        " | Dept: " + rs.getString("department_name") +
                        " | Year: " + rs.getInt("exam_year") +
                        " | Semester: " + rs.getInt("semester");
                examComboBox.addItem(examInfo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading exams: " + ex.getMessage());
        }
    }

    private void loadStudents() {
        DefaultListModel<String> model = new DefaultListModel<>();
        studentList.setModel(model); // Clear previous data

        String selectedExam = (String) examComboBox.getSelectedItem();
        if (selectedExam == null) {
            JOptionPane.showMessageDialog(this, "Please select an exam first.");
            return;
        }

        String examId = selectedExam.split(" ")[1]; // Extract exam_id

        String sql = "SELECT s.student_id, s.student_name, s.department, s.hall, " +
                "r.department_approval, r.hall_approval, r.registrar_approval " +
                "FROM students s " +
                "JOIN exam_registrations r ON s.student_id = r.student_id " +
                "WHERE r.exam_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, examId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String studentInfo = String.format(
                        "ID: %d, Name: %s, Dept: %s, Hall: %s, " +
                                "Dept Approval: %s, Hall Approval: %s, Registrar Approval: %s",
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("department"),
                        rs.getString("hall"),
                        rs.getString("department_approval"),
                        rs.getString("hall_approval"),
                        rs.getString("registrar_approval")
                );
                model.addElement(studentInfo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + ex.getMessage());
        }
    }

    private void showStudentDetails() {
        String selectedValue = studentList.getSelectedValue();
        if (selectedValue == null) return;

        int studentId = Integer.parseInt(selectedValue.split(",")[0].split(":")[1].trim());
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String studentInfo = "ID: " + rs.getInt("student_id") +
                        "\nName: " + rs.getString("student_name") +
                        "\nEmail: " + rs.getString("email") +
                        "\nAddress: " + rs.getString("address") +
                        "\nSSC GPA: " + rs.getBigDecimal("ssc_gpa") +
                        "\nHSC GPA: " + rs.getBigDecimal("hsc_gpa") +
                        "\nDepartment: " + rs.getString("department") +
                        "\nHall: " + rs.getString("hall");

                showApprovalDialog(studentId, studentInfo);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching student details: " + ex.getMessage());
        }
    }

    private void showApprovalDialog(int studentId, String studentInfo) {
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

    private void updateApprovalStatus(int studentId, String status, JDialog dialog) {
        String selectedExam = (String) examComboBox.getSelectedItem();
        String examId = selectedExam.split(" ")[1]; // Extract exam_id

        String sql = "UPDATE exam_registrations SET registrar_approval = ? WHERE student_id = ? AND exam_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, studentId);
            pstmt.setString(3, examId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Status updated to " + status + "!");
                dialog.dispose();
                loadStudents(); // Refresh student list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update status.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating status: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrarDashboard().setVisible(true));
    }
}
