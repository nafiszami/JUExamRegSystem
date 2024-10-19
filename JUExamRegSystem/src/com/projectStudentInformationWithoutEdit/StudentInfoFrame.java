package com.projectStudentInformationWithoutEdit;

import javax.swing.*;
import java.awt.*;

public class StudentInfoFrame extends JFrame {

    private Student student;

    public StudentInfoFrame(Student student) {
        this.student = student;

        // Set window properties
        setTitle("Student Information");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel headerLabel = new JLabel("Student Information", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setForeground(Color.BLUE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Create student info panel
        JPanel studentInfoPanel = new JPanel();
        studentInfoPanel.setLayout(new GridLayout(11, 2, 10, 10));
        studentInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add student details with labels and fields
        studentInfoPanel.add(new JLabel("Name:"));
        studentInfoPanel.add(createDataLabel(student.getName()));

        studentInfoPanel.add(new JLabel("Father's Name:"));
        studentInfoPanel.add(createDataLabel(student.getFathersName()));

        studentInfoPanel.add(new JLabel("Mother's Name:"));
        studentInfoPanel.add(createDataLabel(student.getMothersName()));

        studentInfoPanel.add(new JLabel("Date of Birth:"));
        studentInfoPanel.add(createDataLabel(student.getDateOfBirth()));

        studentInfoPanel.add(new JLabel("Student ID:"));
        studentInfoPanel.add(createDataLabel(student.getStudentId()));

        studentInfoPanel.add(new JLabel("Exam Roll:"));
        studentInfoPanel.add(createDataLabel(student.getExamRoll()));

        studentInfoPanel.add(new JLabel("Hall:"));
        studentInfoPanel.add(createDataLabel(student.getHall()));

        studentInfoPanel.add(new JLabel("Department:"));
        studentInfoPanel.add(createDataLabel(student.getDepartment()));

        studentInfoPanel.add(new JLabel("Session:"));
        studentInfoPanel.add(createDataLabel(student.getSession()));

        studentInfoPanel.add(new JLabel("Batch:"));
        studentInfoPanel.add(createDataLabel(student.getBatch()));

        // Add the student info panel to the center
        add(studentInfoPanel, BorderLayout.CENTER);

        // Create a panel for the Close button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create Close button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setBackground(Color.RED);
        closeButton.setToolTipText("Click to close the window");
        closeButton.addActionListener(e -> System.exit(0)); // Close action
        buttonPanel.add(closeButton);

        // Add button panel to the bottom
        add(buttonPanel, BorderLayout.SOUTH);

        // Set the location of the window to center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper method to create data labels with specific style
    private JLabel createDataLabel(String data) {
        JLabel label = new JLabel(data);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);
        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        return label;
    }
}
