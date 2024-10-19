package com.ju.examreg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRegistrationForm extends JFrame {

    // Declare GUI components
    private JLabel logo = new JLabel();
    private JLabel titleLabel = new JLabel("Student Registration", SwingConstants.CENTER);
    private JLabel studentIdLabel = new JLabel("Student ID:");
    private JLabel nameLabel = new JLabel("Full Name:");
    private JLabel fatherNameLabel = new JLabel("Father's Name:");
    private JLabel motherNameLabel = new JLabel("Mother's Name:");
    private JLabel dobLabel = new JLabel("Date of Birth (YYYY-MM-DD):");
    private JLabel addressLabel = new JLabel("Address:");
    private JLabel sscYearLabel = new JLabel("SSC Year:");
    private JLabel sscGPALabel = new JLabel("SSC GPA:");
    private JLabel hscYearLabel = new JLabel("HSC Year:");
    private JLabel hscGPALabel = new JLabel("HSC GPA:");
    private JLabel departmentLabel = new JLabel("Department:");
    private JLabel batchLabel = new JLabel("Batch:");
    private JLabel sessionLabel = new JLabel("Session:");
    private JLabel hallLabel = new JLabel("Hall:");
    private JLabel examRollLabel = new JLabel("Exam Roll:");
    private JLabel emailLabel = new JLabel("Email:");
    private JLabel passwordLabel = new JLabel("Password:");

    private JTextField studentIdField = new JTextField();
    private JTextField nameField = new JTextField();
    private JTextField fatherNameField = new JTextField();
    private JTextField motherNameField = new JTextField();
    private JTextField dobField = new JTextField();
    private JTextField addressField = new JTextField();
    private JTextField sscYearField = new JTextField();
    private JTextField sscGPAField = new JTextField();
    private JTextField hscYearField = new JTextField();
    private JTextField hscGPAField = new JTextField();
    private JTextField departmentField = new JTextField();
    private JTextField batchField = new JTextField();
    private JTextField sessionField = new JTextField();
    private JTextField hallField = new JTextField();
    private JTextField examRollField = new JTextField();
    private JTextField emailField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();

    public StudentRegistrationForm() {
        setTitle("Student Registration");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(35, 2));
        Font titleFont = new Font("Arial", Font.BOLD, 18);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.BLUE);

        // Add labels and fields to the form
        add(titleLabel);
        add(new JLabel());
        add(studentIdLabel);
        add(studentIdField);
        add(nameLabel);
        add(nameField);
        add(fatherNameLabel);
        add(fatherNameField);
        add(motherNameLabel);
        add(motherNameField);
        add(dobLabel);
        add(dobField);
        add(addressLabel);
        add(addressField);
        add(departmentLabel);
        add(departmentField);
        add(batchLabel);
        add(batchField);
        add(sessionLabel);
        add(sessionField);
        add(hallLabel);
        add(hallField);
        add(examRollLabel);
        add(examRollField);
        add(sscYearLabel);
        add(sscYearField);
        add(sscGPALabel);
        add(sscGPAField);
        add(hscYearLabel);
        add(hscYearField);
        add(hscGPALabel);
        add(hscGPAField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerStudent();
            }
        });


        add(registerButton);
    }

    private void registerStudent() {
        String sql = "INSERT INTO Students (student_id, student_name, father_name, mother_name, date_of_birth, " +
                "address, ssc_year, ssc_gpa, hsc_year, hsc_gpa, department, batch, session, hall, exam_roll, email, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            try {
                pstmt.setInt(1, Integer.parseInt(studentIdField.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Student ID. Please enter a numeric value.");
                studentIdField.requestFocus();
                return;
            }

            pstmt.setString(2, nameField.getText());
            if (nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Full Name is required.");
                nameField.requestFocus();
                return;
            }

            pstmt.setString(3, fatherNameField.getText());
            if (fatherNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Father's Name is required.");
                fatherNameField.requestFocus();
                return;
            }

            pstmt.setString(4, motherNameField.getText());
            if (motherNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mother's Name is required.");
                motherNameField.requestFocus();
                return;
            }

            pstmt.setString(5, dobField.getText());
            if (dobField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Date of Birth is required.");
                dobField.requestFocus();
                return;
            }

            pstmt.setString(6, addressField.getText()); // address
            if (addressField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Address is required.");
                addressField.requestFocus();
                return;
            }

            try {
                pstmt.setInt(7, Integer.parseInt(sscYearField.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid SSC Year. Please enter a numeric value.");
                sscYearField.requestFocus();
                return;
            }

            try {
                pstmt.setBigDecimal(8, new java.math.BigDecimal(sscGPAField.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid SSC GPA. Please enter a valid decimal value.");
                sscGPAField.requestFocus();
                return;
            }

            try {
                pstmt.setInt(9, Integer.parseInt(hscYearField.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid HSC Year. Please enter a numeric value.");
                hscYearField.requestFocus();
                return;
            }

            try {
                pstmt.setBigDecimal(10, new java.math.BigDecimal(hscGPAField.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid HSC GPA. Please enter a valid decimal value.");
                hscGPAField.requestFocus();
                return;
            }

            pstmt.setString(11, departmentField.getText()); // department
            if (departmentField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Department is required.");
                departmentField.requestFocus();
                return;
            }

            try {
                pstmt.setInt(12, Integer.parseInt(batchField.getText())); // batch
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Batch. Please enter a numeric value.");
                batchField.requestFocus();
                return;
            }

            pstmt.setString(13, sessionField.getText()); // session
            if (sessionField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Session is required.");
                sessionField.requestFocus();
                return;
            }

            pstmt.setString(14, hallField.getText()); // hall
            if (hallField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hall is required.");
                hallField.requestFocus();
                return;
            }

            try {
                pstmt.setInt(15, Integer.parseInt(examRollField.getText())); // exam_roll
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Exam Roll. Please enter a numeric value.");
                examRollField.requestFocus();
                return;
            }

            pstmt.setString(16, emailField.getText()); // email
            if (emailField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email is required.");
                emailField.requestFocus();
                return;
            }

            pstmt.setString(17, new String(passwordField.getPassword())); 
            if (new String(passwordField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password is required.");
                passwordField.requestFocus();
                return;
            }

            // Execute the insert operation
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Student registered successfully!");
                dispose(); // Close the registration form
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
        new StudentRegistrationForm().setVisible(true);
    }
}
