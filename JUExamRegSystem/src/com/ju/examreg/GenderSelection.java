package examregistration;

import javax.swing.*;
import java.awt.*;

public class GenderSelection {
    private JPanel genderPanel;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private ButtonGroup genderGroup;

    public GenderSelection() {
        // Create the panel for gender selection
        genderPanel = new JPanel();
        genderPanel.setLayout(new FlowLayout());
        genderPanel.setBounds(30, 270, 300, 50); // Set position and size of the panel

        // Create the radio buttons
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");

        // Add buttons to a ButtonGroup to make them mutually exclusive
        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        // Add buttons to the panel
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
    }

    public JPanel getGenderPanel() {
        return genderPanel;
    }

    public String getSelectedGender() {
        if (maleButton.isSelected()) {
            return "Male";
        } else if (femaleButton.isSelected()) {
            return "Female";
        } else {
            return "Not specified";
        }
    }
}
