package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileWindow extends JFrame {
    public ProfileWindow(ResultSet rs) {
        setTitle("Student Profile");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(14, 2)); // Adjust the layout for displaying all fields

        try {
            // Display student information from ResultSet
            add(new JLabel("Student ID:"));
            add(new JLabel(rs.getString("student_id")));
            add(new JLabel("Full Name:"));
            add(new JLabel(rs.getString("student_name")));
            add(new JLabel("Father's Name:"));
            add(new JLabel(rs.getString("father_name")));
            add(new JLabel("Mother's Name:"));
            add(new JLabel(rs.getString("mother_name")));
            add(new JLabel("Department:"));
            add(new JLabel(rs.getString("department")));
            add(new JLabel("Batch:"));
            add(new JLabel(rs.getString("batch")));
            add(new JLabel("Session:"));
            add(new JLabel(rs.getString("session")));
            add(new JLabel("Semester:"));
            add(new JLabel(rs.getString("semester")));
            add(new JLabel("Hall:"));
            add(new JLabel(rs.getString("hall")));
            add(new JLabel("Exam Roll:"));
            add(new JLabel(rs.getString("exam_roll")));
            add(new JLabel("Email:"));
            add(new JLabel(rs.getString("email")));
            add(new JLabel("Date of Birth:"));
            add(new JLabel(rs.getString("date_of_birth")));
            add(new JLabel("Address:"));
            add(new JLabel(rs.getString("address")));
            add(new JLabel("SSC Year:"));
            add(new JLabel(rs.getString("ssc_year")));
            add(new JLabel("SSC GPA:"));
            add(new JLabel(rs.getString("ssc_gpa")));
            add(new JLabel("HSC Year:"));
            add(new JLabel(rs.getString("hsc_year")));
            add(new JLabel("HSC GPA:"));
            add(new JLabel(rs.getString("hsc_gpa")));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving data: " + e.getMessage());
        }

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose(); // Close the profile window
        });
        add(backButton);
    }
}
