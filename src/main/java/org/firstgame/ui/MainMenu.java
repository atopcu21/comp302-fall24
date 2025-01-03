package org.firstgame.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {

    public MainMenu() {
        JFrame frame = new JFrame("Main Menu");
        frame.add(this);
        setBackground(new Color(40, 10, 35));
        this.setLayout(null);
        this.setFocusable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        initializeMenuComponents();
    }

    private void initializeMenuComponents() {
        JLabel titleLabel = createLabel();
        this.add(titleLabel);

        JButton startButton = createButton("Start", 28, 565, 200, 200, 60, BuilderWindowEarth::getInstance);
        this.add(startButton);

        JButton helpButton = createButton("Help", 28, 565, 260, 200, 60, this::showHelpDialog);
        this.add(helpButton);

        JButton exitButton = createButton("Exit", 28, 565, 320, 200, 60, () -> System.exit(0));
        this.add(exitButton);
    }

    private JLabel createLabel() {
        JLabel label = new JLabel("Main Menu", SwingConstants.CENTER);
        label.setBounds(465, 50, 400, 50);
        label.setForeground(Color.WHITE);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/org/firstgame/fonts/custom_font.ttf")).deriveFont((float) 36);
            label.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            label.setFont(new Font("Arial", Font.BOLD, 36));
        }
        return label;
    }

    private JButton createButton(String text, int fontSize, int x, int y, int width, int height, Runnable action) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFocusPainted(false);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(new Color(30, 5, 30));
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/org/firstgame/fonts/custom_font.ttf")).deriveFont((float) fontSize);
            button.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            button.setFont(new Font("Arial", Font.BOLD, fontSize));
        }
        button.addActionListener(e -> action.run());
        return button;
    }

    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this, "Instructions:\nUse Arrow keys to move your hero.\nClick on object to find runes.\nYou can collect power-ups as you go.", "Help", JOptionPane.INFORMATION_MESSAGE);
    }
}
