package com.ju.examreg;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.*;


public class AddExamWindow extends JFrame {
    private JTextField semesterField = new JTextField();
    private JTextField departmentField = new JTextField();
    private JTextField batchField = new JTextField();
    private JTextField yearField = new JTextField();
    private JComboBox<String> examTypeCombo = new JComboBox<>(new String[]{"Regular", "Improvement"});
    private JButton addButton = new JButton("Add Exam");

    public AddExamWindow() {
        setTitle("Add Exam");
        setSize(300, 300);
        setLayout(new GridLayout(6, 2, 10, 10));
        setLocationRelativeTo(null);

        // Add components to window
        add(new JLabel("Semester:"));
        add(semesterField);
        add(new JLabel("Department:"));
        add(departmentField);
        add(new JLabel("Batch:"));
        add(batchField);
        add(new JLabel("Year:"));
        add(yearField);
        add(new JLabel("Exam Type:"));
        add(examTypeCombo);
        add(addButton);

        addButton.addActionListener(e -> addExam());
    }

    private void addExam() {
        String sql = "INSERT INTO exam_info (semester, department_name, batch, exam_year, exam_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(semesterField.getText()));
            pstmt.setString(2, departmentField.getText());
            pstmt.setInt(3, Integer.parseInt(batchField.getText()));
            pstmt.setInt(4, Integer.parseInt(yearField.getText()));
            pstmt.setString(5, (String) examTypeCombo.getSelectedItem());

            int rowsInserted = pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, rowsInserted > 0 ? "Exam added successfully!" : "Failed to add exam.");
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
