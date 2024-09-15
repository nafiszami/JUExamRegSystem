package com.ju.examreg;
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        // Load the background image
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image to fit the entire panel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
