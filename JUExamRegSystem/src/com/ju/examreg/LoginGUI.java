import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("E-Exam Registration System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Create a panel for the form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Role selection
        JLabel roleLabel = new JLabel("Login as:");
        String[] roles = {"Student", "Department Officer", "Hall Officer", "Exam Control Office"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        // Username and password fields
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        // Buttons
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        // Add components to the form panel
        formPanel.add(roleLabel);
        formPanel.add(roleComboBox);

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(loginButton);
        formPanel.add(cancelButton);

        // Add form panel to the frame
        frame.add(formPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Perform login logic here based on the selected role
                JOptionPane.showMessageDialog(frame, "Login as " + selectedRole + " with username: " + username);
                // You would implement the actual authentication logic here.
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the input fields
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        // Set frame visibility
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
