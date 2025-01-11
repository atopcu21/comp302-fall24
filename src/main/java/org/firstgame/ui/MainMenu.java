package org.firstgame.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {

    public MainMenu() {
        JFrame frame = new JFrame("Main Menu");
        frame.add(this);
        setBackground(new Color(74, 27, 32));
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

        JButton quickStartButton = createButton("Quick", 28, 545, 390, 200, 60, this::quickPlayAll);
        this.add(quickStartButton);

        JButton startButton = createButton("Start", 28, 545, 450, 200, 60, BuilderWindowEarth::getInstance);
        this.add(startButton);

        JButton helpButton = createButton("Help", 28, 545, 510, 200, 60, this::showHelpDialog);
        this.add(helpButton);

        JButton exitButton = createButton("Exit", 28, 545, 570, 200, 60, () -> System.exit(0));
        this.add(exitButton);

        JLabel imageLabel = createImageLabel("src/main/java/org/firstgame/assets/goRokue.png", 330, 10, 640, 360);
        this.add(imageLabel);
    }

    private void quickPlayAll() {
        BuilderWindowEarth.quickPlay();
        BuilderWindowAir.quickPlay();
        BuilderWindowWater.quickPlay();
        BuilderWindowFire.quickPlay();
        
    }

    private JLabel createImageLabel(String filePath, int x, int y, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(filePath);
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JLabel createLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
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

    public void changeBackgroundColor(Color color) {
        setBackground(color);
        repaint();
    }
}
