package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentWindow extends JFrame {
    public StudentWindow() {
        setTitle("Student Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel background = new JLabel(new ImageIcon("E:\\slide3.jpg")); // Update with your background image path
        background.setBounds(0, 0, getWidth(), getHeight());
        add(background);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 50, 100, 30);
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setOpaque(true);
        emailLabel.setBackground(new Color(0, 0, 0, 150));

        JTextField emailField = new JTextField();
        emailField.setBounds(150, 50, 150, 30);
        emailField.setBackground(Color.WHITE);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        passLabel.setForeground(Color.WHITE);
        passLabel.setOpaque(true);
        passLabel.setBackground(new Color(0, 0, 0, 150));

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 100, 150, 30);
        passField.setBackground(Color.WHITE);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 150, 100, 30);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(70, 130, 180));

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(160, 150, 100, 30);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(70, 130, 180));

        // Action Listener for Login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passField.getPassword());
                login(email, password);
            }
        });

        // Action Listener for Register button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StudentRegistrationForm().setVisible(true);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(270, 150, 100, 30);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(70, 130, 180));

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });

        background.add(emailLabel);
        background.add(emailField);
        background.add(passLabel);
        background.add(passField);
        background.add(loginButton);
        background.add(registerButton);
        background.add(backButton);
    }

    private void login(String email, String password) {
        String sql = "SELECT * FROM Students WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password); // Ideally, you'd check against a hashed password

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Successful login, open student dashboard
                String studentId = rs.getString("student_id"); // Retrieve student ID from the result set
                new StudentDashboard(studentId).setVisible(true);
                dispose(); // Close the login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new StudentWindow().setVisible(true);
    }
}
