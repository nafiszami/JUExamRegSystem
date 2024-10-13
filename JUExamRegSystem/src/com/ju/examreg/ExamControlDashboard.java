package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamControlDashboard extends JFrame {
    // Components for adding exam information
    private JTextField semesterField = new JTextField();
    private JTextField departmentField = new JTextField();
    private JTextField batchField = new JTextField();
    private JTextField yearField = new JTextField();
    private JComboBox<String> examTypeCombo = new JComboBox<>(new String[]{"regular", "improvement"});
    private JButton addExamButton = new JButton("Add Exam");

    // Components for subject linking
    private JComboBox<String> examSelectionCombo = new JComboBox<>();
    private JComboBox<String> departmentLinkCombo = new JComboBox<>();
    private JComboBox<String> semesterLinkCombo = new JComboBox<>();
    private JComboBox<String> subjectSelectionCombo = new JComboBox<>();
    private JButton linkSubjectButton = new JButton("Link Subject");

    // Area to view exams and subjects
    private JTextArea examInfoArea = new JTextArea();
    private JScrollPane examInfoScrollPane = new JScrollPane(examInfoArea);

    public ExamControlDashboard() {
        setTitle("Exam Control Dashboard");
        setSize(1000, 700); // Increased size for better visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel for adding exam information
        JPanel addExamPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // Adjusted spacing
        addExamPanel.add(new JLabel("Semester:"));
        addExamPanel.add(semesterField);
        addExamPanel.add(new JLabel("Department:"));
        addExamPanel.add(departmentField);
        addExamPanel.add(new JLabel("Batch:"));
        addExamPanel.add(batchField);
        addExamPanel.add(new JLabel("Year:"));
        addExamPanel.add(yearField);
        addExamPanel.add(new JLabel("Exam Type:"));
        addExamPanel.add(examTypeCombo);
        addExamPanel.add(addExamButton);

        addExamButton.addActionListener(e -> addExamInfo());

        // Panel for linking subjects to exams
        JPanel linkSubjectPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Adjusted spacing
        linkSubjectPanel.add(new JLabel("Select Exam:"));
        linkSubjectPanel.add(examSelectionCombo);
        linkSubjectPanel.add(new JLabel("Select Department:"));
        linkSubjectPanel.add(departmentLinkCombo);
        linkSubjectPanel.add(new JLabel("Select Semester:"));
        linkSubjectPanel.add(semesterLinkCombo);
        linkSubjectPanel.add(new JLabel("Select Subject:"));
        linkSubjectPanel.add(subjectSelectionCombo);

        JButton loadSubjectsButton = new JButton("Load Subjects");
        loadSubjectsButton.addActionListener(e -> loadSubjects());
        linkSubjectPanel.add(loadSubjectsButton);

        linkSubjectPanel.add(linkSubjectButton);
        linkSubjectButton.addActionListener(e -> linkSubjectToExam());

        // Panel for viewing exams and subjects
        JPanel viewExamPanel = new JPanel(new BorderLayout());
        viewExamPanel.add(new JLabel("Exam Information:"), BorderLayout.NORTH);
        viewExamPanel.add(examInfoScrollPane, BorderLayout.CENTER); // Using scroll pane for exam info
        JButton refreshButton = new JButton("Refresh Exams");
        refreshButton.addActionListener(e -> viewExams());
        viewExamPanel.add(refreshButton, BorderLayout.SOUTH);

        // Add all panels to the frame
        add(addExamPanel, BorderLayout.WEST);
        add(linkSubjectPanel, BorderLayout.CENTER);
        add(viewExamPanel, BorderLayout.EAST);

        // Load initial data
        loadExams();
        loadDepartments();
        loadSemesters();
    }

    private void loadExams() {
        examSelectionCombo.removeAllItems();
        String sql = "SELECT exam_id FROM exam_info";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                examSelectionCombo.addItem(rs.getString("exam_id"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading exams: " + ex.getMessage());
        }
    }

    private void loadDepartments() {
        departmentLinkCombo.removeAllItems();
        String sql = "SELECT DISTINCT department FROM subjects";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                departmentLinkCombo.addItem(rs.getString("department"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading departments: " + ex.getMessage());
        }
    }

    private void loadSemesters() {
        semesterLinkCombo.removeAllItems();
        String sql = "SELECT DISTINCT semester FROM subjects";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                semesterLinkCombo.addItem(rs.getString("semester"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading semesters: " + ex.getMessage());
        }
    }

    private void loadSubjects() {
        subjectSelectionCombo.removeAllItems();
        String department = (String) departmentLinkCombo.getSelectedItem();
        String semester = (String) semesterLinkCombo.getSelectedItem();

        if (department == null || semester == null) {
            JOptionPane.showMessageDialog(this, "Please select both department and semester.");
            return;
        }

        String sql = "SELECT subject_code FROM subjects WHERE department = ? AND semester = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department);
            pstmt.setString(2, semester);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                subjectSelectionCombo.addItem(rs.getString("subject_code"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading subjects: " + ex.getMessage());
        }
    }

    private void linkSubjectToExam() {
        int examId = Integer.parseInt((String) examSelectionCombo.getSelectedItem());
        String subjectCode = (String) subjectSelectionCombo.getSelectedItem();

        String sql = "INSERT INTO exam_subjects (exam_id, subject_code) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            pstmt.setString(2, subjectCode);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Subject linked to exam successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to link subject to exam.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void addExamInfo() {
        String sql = "INSERT INTO exam_info (semester, department_name, batch, exam_year, exam_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(semesterField.getText()));
            pstmt.setString(2, departmentField.getText());
            pstmt.setInt(3, Integer.parseInt(batchField.getText()));
            pstmt.setInt(4, Integer.parseInt(yearField.getText()));
            pstmt.setString(5, (String) examTypeCombo.getSelectedItem());
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Exam added successfully!");
                loadExams();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add exam.");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void viewExams() {
        String sql = "SELECT e.exam_id, e.semester, e.department_name, e.batch, e.exam_year, e.exam_type, " +
                "GROUP_CONCAT(s.subject_code SEPARATOR ', ') AS subjects " +
                "FROM exam_info e " +
                "LEFT JOIN exam_subjects es ON e.exam_id = es.exam_id " +
                "LEFT JOIN subjects s ON es.subject_code = s.subject_code " +
                "GROUP BY e.exam_id";
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
                        ", Subjects: " + rs.getString("subjects") + "\n");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExamControlDashboard().setVisible(true));
    }
}
