package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HallWindow extends JFrame {
    private JTextField userField = new JTextField();
    private JPasswordField passField = new JPasswordField();
    private JTextField hallField = new JTextField(); // For registration

    public HallWindow() {
        setTitle("Hall Login/Registration");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background image
        JLabel background = new JLabel(new ImageIcon("E:\\slide3.jpg"));
        background.setBounds(0, 0, getWidth(), getHeight());
        add(background);

        // Username label and field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 30);
        userLabel.setForeground(Color.WHITE);
        userLabel.setOpaque(true);
        userLabel.setBackground(new Color(0, 0, 0, 150));

        userField.setBounds(150, 50, 150, 30);
        userField.setBackground(Color.WHITE);

        // Password label and field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        passLabel.setForeground(Color.WHITE);
        passLabel.setOpaque(true);
        passLabel.setBackground(new Color(0, 0, 0, 150));

        passField.setBounds(150, 100, 150, 30);
        passField.setBackground(Color.WHITE);

        // Hall name label and field (for registration)
        JLabel hallLabel = new JLabel("Hall Name:");
        hallLabel.setBounds(50, 150, 100, 30);
        hallLabel.setForeground(Color.WHITE);
        hallLabel.setOpaque(true);
        hallLabel.setBackground(new Color(0, 0, 0, 150));

        hallField.setBounds(150, 150, 150, 30);
        hallField.setBackground(Color.WHITE);

        // Buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 200, 100, 30);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(70, 130, 180));

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(160, 200, 100, 30);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(70, 130, 180));

        JButton backButton = new JButton("Back");
        backButton.setBounds(270, 200, 100, 30);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(70, 130, 180));

        // Action listener for the login button
        loginButton.addActionListener(e -> login());

        // Action listener for the register button
        registerButton.addActionListener(e -> register());

        // Action listener for the back button
        backButton.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });

        // Add components to the background
        background.add(userLabel);
        background.add(userField);
        background.add(passLabel);
        background.add(passField);
        background.add(hallLabel);
        background.add(hallField);
        background.add(loginButton);
        background.add(registerButton);
        background.add(backButton);
    }

    // Login method
    private void login() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        String sql = "SELECT * FROM hall_admin WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Successful login
                new HallDashboard(rs.getString("hall_name")).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    // Registration method
    private void register() {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        String hallName = hallField.getText();

        String sql = "INSERT INTO hall_admin (username, password, hall_name) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, hallName);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new HallWindow().setVisible(true);
    }
}
