package com.ju.examreg;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkSubjectWindow extends JFrame {
    private JComboBox<String> examCombo = new JComboBox<>();
    private JComboBox<String> subjectCombo = new JComboBox<>();
    private JButton linkButton = new JButton("Link Subject");

    public LinkSubjectWindow() {
        setTitle("Link Subject to Exam");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel("Select Exam:"));
        add(examCombo);
        add(new JLabel("Select Subject:"));
        add(subjectCombo);
        add(linkButton);

        loadExams();
        loadSubjects();

        linkButton.addActionListener(e -> linkSubject());
    }

    private void loadExams() {
        String sql = "SELECT exam_id FROM exam_info";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                examCombo.addItem(rs.getString("exam_id"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading exams: " + ex.getMessage());
        }
    }

    private void loadSubjects() {
        String sql = "SELECT subject_code FROM subjects";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                subjectCombo.addItem(rs.getString("subject_code"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading subjects: " + ex.getMessage());
        }
    }

    private void linkSubject() {
        String sql = "INSERT INTO exam_subjects (exam_id, subject_code) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, (String) examCombo.getSelectedItem());
            pstmt.setString(2, (String) subjectCombo.getSelectedItem());

            int rowsInserted = pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, rowsInserted > 0 ? "Subject linked successfully!" : "Failed to link subject.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
