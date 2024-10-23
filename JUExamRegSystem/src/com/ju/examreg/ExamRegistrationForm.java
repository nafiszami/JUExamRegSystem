package form;

import storage.DataStorage;
import storage.Student;
import validation.InputValidator;
import dob.DateOfBirth;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExamRegistrationForm {
    private JFrame frame;
    private JTextField nameField, rollField, hallField, studentIdField, departmentField, batchField, mobileField;
    private JTextField dayField, monthField, yearField, searchField;
    private JButton submitButton, resetButton, viewButton, resetFileButton, searchButton;
    private JPanel textPanel, buttonPanel, titlePanel;
    private static final String FILE_NAME = "registered_students.txt";

    public static void main(String[] args) {
        ExamRegistrationForm form = new ExamRegistrationForm();
        form.createForm();
    }

    public void createForm() {
        frame = new JFrame("Exam Registration Form");
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Center the frame on screen
        frame.setLocationRelativeTo(null);

        // Add the university logo and contact information at the top of the form
        ImageIcon juLogoIcon = new ImageIcon("C:\\Users\\hp\\Downloads\\ju_logo.png");
        Image juLogoImage = juLogoIcon.getImage().getScaledInstance(560, 140, Image.SCALE_SMOOTH);
        JLabel juLogo = new JLabel(new ImageIcon(juLogoImage));
        juLogo.setBounds(0, 10, 600, 90); // Adjust the bounds as needed
        frame.add(juLogo);

        // Create title box
        titlePanel = new JPanel();
        titlePanel.setBounds(90, 115, 400, 50);
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        titlePanel.setLayout(new BorderLayout());
        frame.add(titlePanel);

        JLabel titleLabel = new JLabel("JU Exam Registration Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Create form fields in a separate box
        textPanel = new JPanel();
        textPanel.setBounds(20, 175, 535, 310);
        textPanel.setLayout(new GridLayout(8, 2, 20, 12));
        textPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        frame.add(textPanel);

        nameField = createTextField("Name:");
        rollField = createTextField("Roll No:");
        hallField = createTextField("Hall Name:");
        studentIdField = createTextField("Student ID:");
        departmentField = createTextField("Department:");
        batchField = createTextField("Batch:");
        mobileField = createTextField("Mobile No:");

        // Date of Birth fields
        JLabel dobLabel = new JLabel("Date of Birth:");
        textPanel.add(dobLabel);
        JPanel dobPanel = new JPanel(new FlowLayout());
        dobPanel.add(dayField = new JTextField(2));
        dobPanel.add(new JLabel("/"));
        dobPanel.add(monthField = new JTextField(2));
        dobPanel.add(new JLabel("/"));
        dobPanel.add(yearField = new JTextField(4));
        textPanel.add(dobPanel);

        // Create button panel
        buttonPanel = new JPanel();
        buttonPanel.setBounds(50, 500, 500, 150);
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        frame.add(buttonPanel);

        // Create buttons and add to panel
        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset");
        viewButton = new JButton("View Registrations");
        resetFileButton = new JButton("Reset File");
        searchField = new JTextField();
        searchButton = new JButton("Search");

        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(resetFileButton);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        // Add button actions
        submitButton.addActionListener(new SubmitAction());
        resetButton.addActionListener(new ResetAction());
        viewButton.addActionListener(new ViewAction());
        resetFileButton.addActionListener(new ResetFileAction());
        searchButton.addActionListener(new SearchAction());

        frame.setVisible(true);
    }

    private JTextField createTextField(String label) {
        JLabel jLabel = new JLabel(label);
        JTextField jTextField = new JTextField();
        textPanel.add(jLabel);
        textPanel.add(jTextField);
        return jTextField;
    }

    // Other inner classes (SubmitAction, ResetAction, ViewAction, ResetFileAction, SearchAction) remain unchanged










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
            searchField.setText("");
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

    // Search action handler
    private class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchTerm = searchField.getText();
            String result = DataStorage.searchRegistrations(searchTerm);
            JTextArea textArea = new JTextArea(result);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

            JOptionPane.showMessageDialog(frame, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
