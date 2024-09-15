package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    public MainWindow() {

        setTitle("Exam Registration System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout
        setLayout(new BorderLayout());


        JLabel background = new JLabel(new ImageIcon("E:\\slide3.jpg"));
        background.setLayout(new GridBagLayout());
        getContentPane().add(background);


        JButton studentButton = createStyledButton("Student");
        JButton departmentButton = createStyledButton("Department");
        JButton hallButton = createStyledButton("Hall");
        JButton examControlButton = createStyledButton("Exam Control Office");

        studentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StudentWindow().setVisible(true);
                dispose();
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


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);


        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }
}
