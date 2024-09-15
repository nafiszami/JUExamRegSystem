import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExamRegistrationForm {
    private JFrame frame;
    private JTextField nameField;
    private JTextField rollField;
    private JTextField hallField;
    private JTextField studentIdField;
    private JTextField departmentField;
    private JTextField batchField;
    private JButton submitButton;

    public static void main(String[] args) {
        ExamRegistrationForm form = new ExamRegistrationForm();
        form.createForm();
    }

    public void createForm() {
        frame = new JFrame("Exam Registration Form");
        frame.setSize(500, 500);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 80, 25);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 30, 150, 25);
        frame.add(nameField);

        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(30, 70, 90, 25);
        frame.add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(120, 70, 150, 25);
        frame.add(rollField);

        JLabel hallLabel = new JLabel("Hall Name:");
        hallLabel.setBounds(30, 110, 90, 25);
        frame.add(hallLabel);

        hallField = new JTextField();
        hallField.setBounds(120, 110, 150, 25);
        frame.add(hallField);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(30, 150, 90, 25);
        frame.add(studentIdLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(120, 150, 150, 25);
        frame.add(studentIdField);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(30, 190, 90, 25);
        frame.add(departmentLabel);

        departmentField = new JTextField();
        departmentField.setBounds(120, 190, 150, 25);
        frame.add(departmentField);

        JLabel batchLabel = new JLabel("Batch:");
        batchLabel.setBounds(30, 230, 90, 25);
        frame.add(batchLabel);

        batchField = new JTextField();
        batchField.setBounds(120, 230, 150, 25);
        frame.add(batchField);

        submitButton = new JButton("Submit");
        submitButton.setBounds(125, 270, 110, 30);
        frame.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String rollNo = rollField.getText();
                String hallName = hallField.getText();
                String studentId = studentIdField.getText();
                String department = departmentField.getText();
                String batch = batchField.getText();

                JOptionPane.showMessageDialog(frame, "Registration Successfully done!\nName: " + name +
                        "\nRoll No: " + rollNo + "\nHall Name: " + hallName + "\nStudent ID: " + studentId +
                        "\nDepartment: " + department + "\nBatch: " + batch);
            }
        });

        frame.setVisible(true);
    }
}
