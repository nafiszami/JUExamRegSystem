package com.ju.examreg;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddSubjectWindow extends JFrame {
    private JTextField subjectNameField = new JTextField();
    private JTextField departmentField = new JTextField();
    private JTextField semesterField = new JTextField();
    private JTextField subjectCodeField = new JTextField();
    private JButton addButton = new JButton("Add Subject");

    public AddSubjectWindow() {
        setTitle("Add Subject");
        setSize(300, 250);
        setLayout(new GridLayout(5, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel("Subject Name:"));
        add(subjectNameField);
        add(new JLabel("Department:"));
        add(departmentField);
        add(new JLabel("Semester:"));
        add(semesterField);
        add(new JLabel("Subject Code:"));
        add(subjectCodeField);
        add(addButton);

        addButton.addActionListener(e -> addSubject());
    }

    private void addSubject() {
        String sql = "INSERT INTO subjects (subject_name, department, semester, subject_code) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subjectNameField.getText());
            pstmt.setString(2, departmentField.getText());
            pstmt.setInt(3, Integer.parseInt(semesterField.getText()));
            pstmt.setString(4, subjectCodeField.getText());

            int rowsInserted = pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, rowsInserted > 0 ? "Subject added successfully!" : "Failed to add subject.");
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
