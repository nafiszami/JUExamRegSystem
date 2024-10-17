package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDashboard extends JFrame {
    private int studentId;
    private String department;

    // Components for the UI
    private JComboBox<String> examComboBox = new JComboBox<>();
    private JButton registerButton = new JButton("Register for Exam");
    private JButton viewExamInfoButton = new JButton("View Exam Information");
    private JTextArea examInfoArea = new JTextArea(15, 40);
    private JButton refreshButton = new JButton("Refresh Exam List");

    public StudentDashboard(int studentId) {
        this.studentId = studentId;
        setTitle("Student Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fetch student's department
        department = fetchDepartment();

        // Panel for exam registration and exam information
        JPanel registerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        registerPanel.add(new JLabel("Select Exam:"));
        registerPanel.add(examComboBox);
        registerPanel.add(registerButton);
        registerPanel.add(viewExamInfoButton);

        registerButton.addActionListener(e -> registerForExam());
        viewExamInfoButton.addActionListener(e -> showExamDetails());

        // Panel for viewing registered exams and approval status
        JPanel viewPanel = new JPanel(new BorderLayout());
        viewPanel.add(new JScrollPane(examInfoArea), BorderLayout.CENTER);
        viewPanel.add(refreshButton, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadExamDetails());

        // Add panels to the frame
        add(registerPanel, BorderLayout.NORTH);
        add(viewPanel, BorderLayout.CENTER);

        // Load available exams
        loadExams();
    }

    private String fetchDepartment() {
        String dept = "";
        String sql = "SELECT department FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dept = rs.getString("department");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching department: " + ex.getMessage());
        }
        return dept;
    }

    private void loadExams() {
        examComboBox.removeAllItems();

        String sql = "SELECT exam_id, department_name, semester, exam_type, exam_year " +
                "FROM exam_info WHERE department_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int examId = rs.getInt("exam_id");
                String examTitle = String.format("%s | Semester %d | %s | %d",
                        rs.getString("department_name"), rs.getInt("semester"),
                        rs.getString("exam_type"), rs.getInt("exam_year"));
                examComboBox.addItem(examId + " - " + examTitle);
            }

            if (examComboBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No exams available for your department.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading exams: " + ex.getMessage());
        }
    }

    private void registerForExam() {
        String selectedExam = (String) examComboBox.getSelectedItem();
        if (selectedExam == null) {
            JOptionPane.showMessageDialog(this, "Please select an exam.");
            return;
        }

        int examId = Integer.parseInt(selectedExam.split(" - ")[0].trim());

        String sql = "INSERT INTO exam_registrations (student_id, exam_id, registration_date, registration_type) " +
                "VALUES (?, ?, CURDATE(), 'Regular')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, examId);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Exam registered successfully!");
                loadExamDetails();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register for the exam.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error registering for exam: " + ex.getMessage());
        }
    }

    private void showExamDetails() {
        String selectedExam = (String) examComboBox.getSelectedItem();
        if (selectedExam == null) {
            JOptionPane.showMessageDialog(this, "Please select an exam.");
            return;
        }

        int examId = Integer.parseInt(selectedExam.split(" - ")[0].trim());

        // Corrected SQL query to join exam_subjects and subjects using subject_code
        String sql = "SELECT s.subject_name " +
                "FROM exam_subjects es " +
                "JOIN subjects s ON es.subject_code = s.subject_code " +
                "WHERE es.exam_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, examId);
            ResultSet rs = pstmt.executeQuery();

            StringBuilder subjects = new StringBuilder("Subjects for Selected Exam:\n");
            while (rs.next()) {
                subjects.append("- ").append(rs.getString("subject_name")).append("\n");
            }

            JOptionPane.showMessageDialog(this, subjects.toString(),
                    "Exam Subjects", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading exam subjects: " + ex.getMessage());
        }
    }

    private void loadExamDetails() {
        examInfoArea.setText("");

        String sql = "SELECT e.exam_id, e.exam_type, r.department_approval, r.hall_approval, r.registrar_approval " +
                "FROM exam_info e " +
                "JOIN exam_registrations r ON e.exam_id = r.exam_id " +
                "WHERE r.student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                examInfoArea.append(String.format("Exam ID: %d, Type: %s, Dept Approval: %s, Hall Approval: %s, Registrar Approval: %s\n",
                        rs.getInt("exam_id"), rs.getString("exam_type"),
                        rs.getString("department_approval"), rs.getString("hall_approval"),
                        rs.getString("registrar_approval")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading exam details: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard(1).setVisible(true));  // Test with student ID 1
    }
}
