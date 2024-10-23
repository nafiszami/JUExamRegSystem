package com.projectLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePassword extends JFrame {

    private JPasswordField newPasswordField, confirmPasswordField;
    private JButton saveButton, cancelButton;
    private String username;

    public ChangePassword(String username) {
        this.username = username;
        setTitle("Change Password");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(30, 30, 100, 30);
        add(newPasswordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(140, 30, 150, 30);
        add(newPasswordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(30, 70, 120, 30);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(140, 70, 150, 30);
        add(confirmPasswordField);

        saveButton = new JButton("Save");
        saveButton.setBounds(50, 110, 100, 30);
        add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 110, 100, 30);
        add(cancelButton);

        // Save action
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (newPassword.equals(confirmPassword)) {
                    if (newPassword.length() < 5) {
                        JOptionPane.showMessageDialog(null, "Password must be at least 5 characters long.");
                    } else {
                        LoginFrame.setDefaultPassword(newPassword);  // Use setter to update the password
                        JOptionPane.showMessageDialog(null, "Password changed successfully for " + username + "!");
                        new Dashboard(username); // Return to the dashboard
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
                }
            }
        });

        // Cancel action
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard(username); // Return to the dashboard
                dispose();
            }
        });

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }
}
