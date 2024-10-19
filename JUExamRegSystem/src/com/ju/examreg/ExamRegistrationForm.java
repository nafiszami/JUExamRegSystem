package form;

import storage.DataStorage;
import storage.Student;
import validation.InputValidator;
import dob.DateOfBirth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExamRegistrationForm {
    private JFrame frame;
    private JTextField nameField, rollField, hallField, studentIdField, departmentField, batchField, mobileField;
    private JTextField dayField, monthField, yearField;
    private JButton submitButton, resetButton, viewButton, resetFileButton;

    private static final String FILE_NAME = "registered_students.txt";

    public static void main(String[] args) {
        ExamRegistrationForm form = new ExamRegistrationForm();
        form.createForm();
    }

    public void createForm() {
        frame = new JFrame("Exam Registration Form");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create form fields
        nameField = createTextField("Name:", 30);
        rollField = createTextField("Roll No:", 70);
        hallField = createTextField("Hall Name:", 110);
        studentIdField = createTextField("Student ID:", 150);
        departmentField = createTextField("Department:", 190);
        batchField = createTextField("Batch:", 230);
        mobileField = createTextField("Mobile No:", 270);

        // Date of Birth fields
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(30, 310, 90, 25);
        frame.add(dobLabel);

        dayField = new JTextField();
        dayField.setBounds(120, 310, 50, 25);
        frame.add(dayField);

        monthField = new JTextField();
        monthField.setBounds(180, 310, 50, 25);
        frame.add(monthField);

        yearField = new JTextField();
        yearField.setBounds(240, 310, 70, 25);
        frame.add(yearField);

        // Create buttons
        submitButton = new JButton("Submit");
        submitButton.setBounds(125, 370, 100, 30);
        frame.add(submitButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(235, 370, 100, 30);
        frame.add(resetButton);

        viewButton = new JButton("View Registrations");
        viewButton.setBounds(143, 410, 170, 30);
        frame.add(viewButton);

        resetFileButton = new JButton("Reset File");
        resetFileButton.setBounds(143, 450, 170, 30);
        frame.add(resetFileButton);

        // Add button actions
        submitButton.addActionListener(new SubmitAction());
        resetButton.addActionListener(new ResetAction());
        viewButton.addActionListener(new ViewAction());
        resetFileButton.addActionListener(new ResetFileAction());

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
            String mobileNumber = mobileField.getText();

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
            if (!InputValidator.validateFields(name, rollNo, hallName, studentId, department, batch, mobileNumber)) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields correctly.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save data
            Student student = new Student(name, rollNo, hallName, studentId, department, batch, mobileNumber, dob);
            DataStorage.saveStudent(student);
            saveToFile(student);
            JOptionPane.showMessageDialog(frame, "Registration Successful!");

            // Reset fields
            new ResetAction().actionPerformed(null);
        }

        private void saveToFile(Student student) {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME, true)))) {
                writer.println(student.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving registration to file.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
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
            mobileField.setText("");
            dayField.setText("");
            monthField.setText("");
            yearField.setText("");
        }
    }

    // View registrations handler
    private class ViewAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String registrations = DataStorage.viewRegistrationsFromFile(FILE_NAME);
            JTextArea textArea = new JTextArea(registrations);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

            JOptionPane.showMessageDialog(frame, scrollPane, "Registered Students", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Reset file action handler
    private class ResetFileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME)))) {
                writer.print("");
                JOptionPane.showMessageDialog(frame, "Registered students file has been reset.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error resetting the file.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
