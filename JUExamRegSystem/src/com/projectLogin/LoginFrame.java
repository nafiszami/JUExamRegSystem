package com.projectLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;
    private JButton loginButton, cancelButton;

    private static String defaultUsername = "admin";
    private static String defaultPassword = "password";

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome! Please login.");
        welcomeLabel.setBounds(120, 10, 200, 30);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel);

        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        add(usernameLabel);

        // Username Text Field
        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 150, 30);
        usernameField.setToolTipText("Enter your username here");
        add(usernameField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 30);
        add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 150, 30);
        passwordField.setToolTipText("Enter your password here");
        add(passwordField);

        // Show Password Checkbox
        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(150, 140, 150, 30);
        add(showPassword);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(50, 200, 100, 30);
        loginButton.setToolTipText("Click to login");
        loginButton.setBackground(Color.CYAN);
        add(loginButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 200, 100, 30);
        cancelButton.setBackground(Color.RED);
        add(cancelButton);

        // Add listeners to buttons and checkbox
        addListeners();

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void addListeners() {
        // Show/Hide Password checkbox action
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('*'); // Hide password
            }
        });

        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals(defaultUsername) && password.equals(defaultPassword)) {
                    JOptionPane.showMessageDialog(null, "Login Successful! Welcome " + username + "!");
                    new Dashboard(username);  // Open Dashboard with username
                    dispose(); // Close login window
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password. Please try again.");
                }
            }
        });

        // Cancel button action
        cancelButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Exiting... Have a great day!");
            System.exit(0);
        });
    }

    public static String getDefaultPassword() {
        return defaultPassword;
    }

    public static void setDefaultPassword(String newPassword) {
        defaultPassword = newPassword;
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
