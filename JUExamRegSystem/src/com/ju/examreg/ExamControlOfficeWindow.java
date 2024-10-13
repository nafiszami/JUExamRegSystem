package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamControlOfficeWindow extends JFrame {
    // GUI Components
    private JTextField userField = new JTextField();
    private JPasswordField passField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JButton backButton = new JButton("Back");

    public ExamControlOfficeWindow() {
        // JFrame setup
        setTitle("Exam Control Office Login/Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background image setup
        JLabel background = new JLabel(new ImageIcon("E:\\slide3.jpg"));
        background.setBounds(0, 0, getWidth(), getHeight());
        add(background);

        // User Label and Field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 30);
        userLabel.setForeground(Color.WHITE);
        userLabel.setOpaque(true);
        userLabel.setBackground(new Color(0, 0, 0, 150));
        background.add(userLabel);

        userField.setBounds(150, 50, 150, 30);
        background.add(userField);

        // Password Label and Field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        passLabel.setForeground(Color.WHITE);
        passLabel.setOpaque(true);
        passLabel.setBackground(new Color(0, 0, 0, 150));
        background.add(passLabel);

        passField.setBounds(150, 100, 150, 30);
        background.add(passField);

        // Login Button
        loginButton.setBounds(50, 150, 100, 30);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        background.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser(); // Handle login
            }
        });

        // Register Button
        registerButton.setBounds(160, 150, 100, 30);
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);
        background.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser(); // Handle registration
            }
        });

        // Back Button
        backButton.setBounds(270, 150, 100, 30);
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        background.add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });
    }

    // Method to Authenticate User Login
    private void authenticateUser() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        String sql = "SELECT * FROM exam_control_office WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                // Redirect to the Exam Control Dashboard
                new ExamControlDashboard().setVisible(true);
                dispose(); // Close the login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // Method to Register a New User
    private void registerUser() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        String sql = "INSERT INTO exam_control_office (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User Registered Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed. Please Try Again.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExamControlOfficeWindow().setVisible(true));
    }
}
