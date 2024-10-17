package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentWindow extends JFrame {
    private JTextField userField = new JTextField();
    private JPasswordField passField = new JPasswordField();
    private JTextField departmentField = new JTextField();
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JButton backButton = new JButton("Back");

    public DepartmentWindow() {
        // Window properties
        setTitle("Department Login/Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background image
        JLabel background = new JLabel(new ImageIcon("E:\\slide3.jpg"));
        background.setBounds(0, 0, getWidth(), getHeight());
        add(background);

        // Fields and labels
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 30);
        userLabel.setForeground(Color.WHITE);
        userLabel.setOpaque(true);
        userLabel.setBackground(new Color(0, 0, 0, 150));

        userField.setBounds(150, 50, 150, 30);
        userField.setBackground(Color.WHITE);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        passLabel.setForeground(Color.WHITE);
        passLabel.setOpaque(true);
        passLabel.setBackground(new Color(0, 0, 0, 150));

        passField.setBounds(150, 100, 150, 30);
        passField.setBackground(Color.WHITE);

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setBounds(50, 150, 100, 30);
        deptLabel.setForeground(Color.WHITE);
        deptLabel.setOpaque(true);
        deptLabel.setBackground(new Color(0, 0, 0, 150));

        departmentField.setBounds(150, 150, 150, 30);
        departmentField.setBackground(Color.WHITE);

        // Action listeners
        loginButton.setBounds(50, 200, 100, 30);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.addActionListener(e -> login());

        registerButton.setBounds(160, 200, 100, 30);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.addActionListener(e -> register());

        backButton.setBounds(270, 200, 100, 30);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(70, 130, 180));
        backButton.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });

        // Add components to the background
        background.add(userLabel);
        background.add(userField);
        background.add(passLabel);
        background.add(passField);
        background.add(deptLabel);
        background.add(departmentField);
        background.add(loginButton);
        background.add(registerButton);
        background.add(backButton);
    }

    private void login() {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        String department = departmentField.getText();

        String sql = "SELECT * FROM department_admin WHERE username = ? AND password = ? AND department_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Ideally, hash this
            pstmt.setString(3, department);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new DepartmentDashboard(department).setVisible(true); // Open dashboard
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void register() {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        String department = departmentField.getText();

        String sql = "INSERT INTO department_admin (username, password, department_name) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, department);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DepartmentWindow().setVisible(true));
    }
}
