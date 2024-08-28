import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import com.ju.examreg.;
public class ExamRegistrationForm {
    private JFrame frame;
    private JTextField nameField;
    private JTextField rollField;
    private JButton submitButton;

    public static void main(String[] args) {
        ExamRegistrationForm form = new ExamRegistrationForm();
        form.createForm();
    }

    public void createForm() {
        frame = new JFrame("Exam Registration Form");
        frame.setSize(500, 400);
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

        submitButton = new JButton("Submit");
        submitButton.setBounds(125, 110, 110, 30);
        frame.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String rollNo = rollField.getText();
                JOptionPane.showMessageDialog(frame, "Registration Successfully done!\nName: " + name + "\nRoll No: " + rollNo);
            }
        });

        frame.setVisible(true);
    }
}
