package form;

import storage.DataStorage;
import storage.Student;
import validation.InputValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExamRegistrationForm {
    private JFrame frame;
    private JTextField nameField, rollField, hallField, studentIdField, departmentField, batchField;
    private JButton submitButton, resetButton, viewButton;
    private JRadioButton maleButton, femaleButton; // Added radio buttons for gender
    private ButtonGroup genderGroup; // Group for radio buttons

    public static void main(String[] args) {
        ExamRegistrationForm form = new ExamRegistrationForm();
        form.createForm();
    }

    public void createForm() {
        frame = new JFrame("Exam Registration Form");
        frame.setSize(500, 600); // Increased size to accommodate gender selection
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create form fields
        nameField = createTextField("Name:", 30);
        rollField = createTextField("Roll No:", 70);
        hallField = createTextField("Hall Name:", 110);
        studentIdField = createTextField("Student ID:", 150);
        departmentField = createTextField("Department:", 190);
        batchField = createTextField("Batch:", 230);

        // Create gender selection
        createGenderSelection();

        // Create buttons
        submitButton = new JButton("Submit");
        submitButton.setBounds(125, 370, 100, 30);
        frame.add(submitButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(235, 370, 100, 30);
        frame.add(resetButton);

        viewButton = new JButton("View Registrations");
        viewButton.setBounds(125, 410, 210, 30);
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
            String gender = maleButton.isSelected() ? "Male" : "Female"; // Get selected gender

            // Validate input
            if (!InputValidator.validateFields(name, rollNo, hallName, studentId, department, batch)) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields correctly.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save data
            Student student = new Student(name, rollNo, hallName, studentId, department, batch, gender);
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
            genderGroup.clearSelection(); // Reset gender selection
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
