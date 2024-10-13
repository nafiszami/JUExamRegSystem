package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDashboard extends JFrame {
    private String studentId;  // Store the student ID

    // Components for registering for exams
    private JComboBox<String> semesterCombo = new JComboBox<>();
    private JComboBox<String> departmentCombo = new JComboBox<>();
    private JComboBox<String> batchCombo = new JComboBox<>();
    private JButton registerButton = new JButton("Register for Exam");

    // Area to view exams and approval status
    private JTextArea examInfoArea = new JTextArea();
    private JScrollPane examInfoScrollPane = new JScrollPane(examInfoArea);
    private JButton refreshButton = new JButton("Refresh Exams");

    public StudentDashboard(String studentId) {
        this.studentId = studentId; // Store the student ID
        setTitle("Student Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel for Exam Registration
        JPanel registerExamPanel = new JPanel(new GridLayout(4, 2));
        registerExamPanel.add(new JLabel("Select Semester:"));
        registerExamPanel.add(semesterCombo);
        registerExamPanel.add(new JLabel("Select Department:"));
        registerExamPanel.add(departmentCombo);
        registerExamPanel.add(new JLabel("Select Batch:"));
        registerExamPanel.add(batchCombo);
        registerExamPanel.add(registerButton);

        registerButton.addActionListener(e -> registerForExam());

        // Panel for Viewing Exams
        JPanel viewExamPanel = new JPanel(new BorderLayout());
        viewExamPanel.add(new JLabel("Exam Information and Approval Status:"), BorderLayout.NORTH);
        viewExamPanel.add(examInfoScrollPane, BorderLayout.CENTER);
        refreshButton.addActionListener(e -> viewExams());
        viewExamPanel.add(refreshButton, BorderLayout.SOUTH);

        // Add panels to the frame
        add(registerExamPanel, BorderLayout.NORTH);
        add(viewExamPanel, BorderLayout.CENTER);

        // Load initial data
        loadSemesters();
        loadDepartments();
        loadBatches();
    }

    private void loadSemesters() {
        // Load semesters into the semesterCombo
        semesterCombo.removeAllItems();
        String sql = "SELECT DISTINCT semester FROM subjects"; // Adjust this query as per your database
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                semesterCombo.addItem(rs.getString("semester"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading semesters: " + ex.getMessage());
        }
    }

    private void loadDepartments() {
        // Load departments into the departmentCombo
        departmentCombo.removeAllItems();
        String sql = "SELECT DISTINCT department FROM subjects"; // Adjust this query as per your database
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                departmentCombo.addItem(rs.getString("department"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading departments: " + ex.getMessage());
        }
    }

    private void loadBatches() {
        // Load batches into the batchCombo
        batchCombo.removeAllItems();
        String sql = "SELECT DISTINCT batch FROM students"; // Adjust this query as per your database
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                batchCombo.addItem(rs.getString("batch"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading batches: " + ex.getMessage());
        }
    }

    private void registerForExam() {
        String semester = (String) semesterCombo.getSelectedItem();
        String department = (String) departmentCombo.getSelectedItem();
        String batch = (String) batchCombo.getSelectedItem();

        String sql = "INSERT INTO exam_registrations (student_id, semester, department, batch, registration_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId); // Assuming studentId is available
            pstmt.setInt(2, Integer.parseInt(semester)); // Cast to Integer if semester is an integer
            pstmt.setString(3, department);
            pstmt.setString(4, batch);
            pstmt.setString(5, "Regular"); // or "Improvement" based on your logic

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                viewExams(); // Refresh the exam list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register for exam.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please select a valid semester.");
        }
    }


    private void viewExams() {
        String sql = "SELECT e.exam_id, e.semester, e.department_name, e.batch, e.exam_year, e.exam_type, " +
                "r.approval_status " +
                "FROM exam_info e " +
                "LEFT JOIN exam_registrations r ON e.semester = r.semester AND e.department_name = r.department " +
                "AND e.batch = r.batch";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            examInfoArea.setText("");
            while (rs.next()) {
                examInfoArea.append("Exam ID: " + rs.getInt("exam_id") +
                        ", Semester: " + rs.getInt("semester") +
                        ", Department: " + rs.getString("department_name") +
                        ", Batch: " + rs.getInt("batch") +
                        ", Year: " + rs.getInt("exam_year") +
                        ", Type: " + rs.getString("exam_type") +
                        ", Approval Status: " + rs.getString("approval_status") + "\n");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // For testing, create a StudentDashboard with a dummy student ID
        String dummyStudentId = "12345"; // Replace with a real student ID for actual usage
        SwingUtilities.invokeLater(() -> new StudentDashboard(dummyStudentId).setVisible(true));
    }
}
