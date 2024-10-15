package form;

import storage.DataStorage;
import storage.Student;
import dob.DateOfBirth;
import validation.InputValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExamRegistrationForm {
    public JFrame frame;
    public JTextField nameField, rollField, hallField, studentIdField, departmentField, batchField;
    public JTextField dayField, monthField, yearField; // Added fields for DOB
    public JButton submitButton, resetButton, viewButton;
    public JRadioButton maleButton, femaleButton;
    public ButtonGroup genderGroup;

    public static void main(String[] args) {
        ExamRegistrationForm form = new ExamRegistrationForm();
        form.createForm();
    }

    public void createForm() {
        frame = new JFrame(" Exam Registration Form ");
        frame.setSize(700, 800); // Increased size to accommodate DOB fields
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create form fields
        nameField = createTextField("Name:", 40);
        rollField = createTextField("Roll No:", 80);
        hallField = createTextField("Hall Name:", 120);
        studentIdField = createTextField("Student ID:", 160);
        departmentField = createTextField("Department:", 200);
        batchField = createTextField("Batch:", 240);

        // Create gender selection
        createGenderSelection();

        // Create date of birth fields
        createDateOfBirthFields();

        // Create buttons
        submitButton = new JButton("Submit");
        submitButton.setBounds(125, 450, 100, 30);
        frame.add(submitButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(235, 450, 100, 30);
        frame.add(resetButton);

        viewButton = new JButton("View Registrations");
        viewButton.setBounds(142, 490, 180, 30);
        frame.add(viewButton);

        // Add button actions
        submitButton.addActionListener(new SubmitAction());
        resetButton.addActionListener(new ResetAction());
        viewButton.addActionListener(new ViewAction());

        frame.setVisible(true);
    }

    private JTextField createTextField(String label, int yPosition) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(30, yPosition, 90, 25);
        frame.add(jLabel);

        JTextField jTextField = new JTextField();
        jTextField.setBounds(120, yPosition, 150, 25);
        frame.add(jTextField);
        return jTextField;
    }

    private void createGenderSelection() {
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(30, 270, 90, 25);
        frame.add(genderLabel);

        maleButton = new JRadioButton("Male");
        maleButton.setBounds(120, 270, 70, 25);
        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(200, 270, 80, 25);

        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        frame.add(maleButton);
        frame.add(femaleButton);
    }

    private void createDateOfBirthFields() {
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(30, 310, 90, 25);
        frame.add(dobLabel);

        dayField = new JTextField();
        dayField.setBounds(120, 310, 40, 25);
        frame.add(dayField);

        monthField = new JTextField();
        monthField.setBounds(170, 310, 40, 25);
        frame.add(monthField);

        yearField = new JTextField();
        yearField.setBounds(220, 310, 60, 25);
        frame.add(yearField);
    }

    // Submit action handler
    private class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String rollNo = rollField.getText();
            String hallName = hallField.getText();
            String studentId = studentIdField.getText();
            String department = departmentField.getText();
            String batch = batchField.getText();
            String gender = maleButton.isSelected() ? "Male" : "Female";

            int day, month, year;
            try {
                day = Integer.parseInt(dayField.getText());
                month = Integer.parseInt(monthField.getText());
                year = Integer.parseInt(yearField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid date of birth.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DateOfBirth dob = new DateOfBirth(day, month, year);

            // Validate input
            if (!InputValidator.validateFields(name, rollNo, hallName, studentId, department, batch)) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields correctly.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save data
            Student student = new Student(name, rollNo, hallName, studentId, department, batch, gender, dob);
            DataStorage.saveStudent(student);
            JOptionPane.showMessageDialog(frame, "Registration Successful!");

            // Reset fields
            new ResetAction().actionPerformed(null);
        }
    }

    // Reset action handler
    private class ResetAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nameField.setText("");
            rollField.setText("");
            hallField.setText("");
            studentIdField.setText("");
            departmentField.setText("");
            batchField.setText("");
            genderGroup.clearSelection();
            dayField.setText("");
            monthField.setText("");
            yearField.setText("");
        }
    }

    // View registrations handler
    private class ViewAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String registrations = DataStorage.viewRegistrations();
            JOptionPane.showMessageDialog(frame, registrations, "Registered Students", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
