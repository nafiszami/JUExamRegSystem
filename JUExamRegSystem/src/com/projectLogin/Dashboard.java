package com.projectLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {

    private JButton logoutButton, changePasswordButton;
    private String username;

    public Dashboard(String username) {
        this.username = username;

        setTitle("Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setBounds(100, 30, 200, 30);
        welcomeLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        add(welcomeLabel);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(50, 100, 100, 30);
        add(logoutButton);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(200, 100, 150, 30);
        add(changePasswordButton);

        // Logout action
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Logging out. See you soon, " + username + "!");
                new LoginFrame(); // Return to login screen
                dispose(); // Close dashboard
            }
        });

        // Change password action
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePassword(username); // Open change password window
                dispose(); // Close dashboard
            }
        });

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }
}
