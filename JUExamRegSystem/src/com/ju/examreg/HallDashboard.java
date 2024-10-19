package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HallDashboard extends JFrame {
    private String hall;

    // Components for the dashboard
    private JComboBox<String> examComboBox = new JComboBox<>();
    private JList<String> studentList = new JList<>();
    private JScrollPane studentScrollPane = new JScrollPane(studentList);
    private JButton viewStudentsButton = new JButton("View Students");
    private JButton logoutButton = new JButton("Logout");

    public HallDashboard(String hall) {
        this.hall = hall;

        setTitle("Hall Dashboard - " + hall);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel for controls
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Select Exam:"));
        controlPanel.add(examComboBox);
        controlPanel.add(viewStudentsButton);
        controlPanel.add(logoutButton);

        add(controlPanel, BorderLayout.NORTH);
        add(studentScrollPane, BorderLayout.CENTER);

        // Load exams into the combo box
        loadExams();

        // Action listeners
        viewStudentsButton.addActionListener(e -> loadStudents());
        logoutButton.addActionListener(e -> {
            new HallWindow().setVisible(true);
            dispose();
        });

        // Double-click listener for student list
        studentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    studentListClicked();
                }
            }
        });

        // Custom rendering for blue background on selection
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
        String sql = "SELECT exam_id, department_name, exam_year, exam_type FROM exam_info";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String examId = rs.getString("exam_id");
                String department = rs.getString("department_name");
                String examYear = rs.getString("exam_year");
                String examType = rs.getString("exam_type");

                examComboBox.addItem("ID: " + examId + " | Dept: " + department +
                        " | Year: " + examYear + " | Type: " + examType);
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

        String examId = selectedExam.split("\\|")[0].split(":")[1].trim(); // Extract exam_id

        String sql = "SELECT s.student_id, s.student_name, s.email, r.hall_approval " +
                "FROM students s " +
                "JOIN exam_registrations r ON s.student_id = r.student_id " +
                "WHERE r.exam_id = ? AND s.hall = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, examId);
            pstmt.setString(2, hall);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                model.addElement(
                        "ID: " + rs.getInt("student_id") +
                                ", Name: " + rs.getString("student_name") +
                                ", Email: " + rs.getString("email") +
                                ", Hall Approval: " + rs.getString("hall_approval")
                );
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + ex.getMessage());
        }
    }

    private void studentListClicked() {
        String selectedValue = studentList.getSelectedValue();
        if (selectedValue == null) return;

        String studentId = selectedValue.split(",")[0].split(":")[1].trim(); // Extract student_id
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
                        "\nHSC GPA: " + rs.getBigDecimal("hsc_gpa") +
                        "\nSession: " + rs.getString("session") +
                        "\nHall: " + rs.getString("hall") +
                        "\nExam Roll: " + rs.getString("exam_roll");

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
        dialog.setSize(400, 400);

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
        String examId = selectedExam.split("\\|")[0].split(":")[1].trim(); // Extract exam_id

        String sql = "UPDATE exam_registrations SET hall_approval = ? WHERE student_id = ? AND exam_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, Integer.parseInt(studentId));
            pstmt.setString(3, examId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Status updated to " + status + "!");
                dialog.dispose();
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update status.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating status: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HallDashboard("Hall A").setVisible(true));
    }
}
