package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepartmentWindow extends JFrame {
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

        JTextField userField = new JTextField();
        userField.setBounds(150, 50, 150, 30);
        userField.setBackground(Color.WHITE);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        passLabel.setForeground(Color.WHITE);
        passLabel.setOpaque(true);
        passLabel.setBackground(new Color(0, 0, 0, 150));

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 100, 150, 30);
        passField.setBackground(Color.WHITE);

        // Buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 150, 100, 30);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(70, 130, 180));

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(160, 150, 100, 30);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(70, 130, 180));

        JButton backButton = new JButton("Back");
        backButton.setBounds(270, 150, 100, 30);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(70, 130, 180));

        // Action listener for back button to go back to the main window
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });

        // Add components to the background
        background.add(userLabel);
        background.add(userField);
        background.add(passLabel);
        background.add(passField);
        background.add(loginButton);
        background.add(registerButton);
        background.add(backButton);
    }

    public static void main(String[] args) {
        new DepartmentWindow().setVisible(true);
    }
}
