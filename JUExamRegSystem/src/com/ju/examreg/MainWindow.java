package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    public MainWindow() {
        // Set the main window properties
        setTitle("Exam Registration System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on the screen

        // Set layout
        setLayout(new BorderLayout());

        // Set background image (optional, you can leave this out if not needed)
        JLabel background = new JLabel(new ImageIcon("E:\\slide3.jpg"));
        background.setLayout(new GridBagLayout());
        getContentPane().add(background);

        // Buttons for navigation with custom styles
        JButton studentButton = createStyledButton("Student");
        JButton departmentButton = createStyledButton("Department");
        JButton hallButton = createStyledButton("Hall");
        JButton examControlButton = createStyledButton("Exam Control Office");

        // Add action listeners to buttons to open respective windows
        studentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StudentWindow().setVisible(true);
                dispose();  // Close the main window
            }
        });

        departmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DepartmentWindow().setVisible(true);
                dispose();
            }
        });

        hallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HallWindow().setVisible(true);
                dispose();
            }
        });

        examControlButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ExamControlOfficeWindow().setVisible(true);
                dispose();
            }
        });

        // Layout for the buttons using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Padding between buttons
        gbc.gridx = 0;

        // Add the buttons to the background panel
        background.add(studentButton, gbc);
        background.add(departmentButton, gbc);
        background.add(hallButton, gbc);
        background.add(examControlButton, gbc);
    }

    // Method to create a styled button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);

        // Custom button styles
        button.setFont(new Font("Arial", Font.BOLD, 16));  // Set font size and style
        button.setBackground(new Color(70, 130, 180));  // Set background color (steel blue)
        button.setForeground(Color.WHITE);  // Set text color to white
        button.setFocusPainted(false);  // Remove the default focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Set padding inside the button

        // Optional: Change the hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));  // Lighter blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));  // Original color when not hovered
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }
}
