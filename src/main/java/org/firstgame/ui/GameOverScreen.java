package org.firstgame.ui;

import javax.swing.*;

import org.firstgame.RokueLikeGame;

import java.awt.*;

public class GameOverScreen extends JPanel {
    
    public GameOverScreen() {
        // Create the frame
        JFrame frame = new JFrame("Game Over");
        frame.add(this);

        // Set up the panel
        setBackground(new Color(40, 10, 35));
        setLayout(null);
        setFocusable(true);

        // Frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Create and configure the "Game Over" label
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setBounds(440, 100, 400, 80);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        add(gameOverLabel);

        // Create buttons with larger/bolder font
        JButton retryButton = createButton("Retry", 540, 300, 200, 60, this::retryGame);
        JButton mainMenuButton = createButton("Main Menu", 540, 400, 200, 60, this::goToMainMenu);
        JButton exitButton = createButton("Exit", 540, 500, 200, 60, () -> System.exit(0));

        // Add buttons to the panel
        add(retryButton);
        add(mainMenuButton);
        add(exitButton);

        // Make the frame visible after adding everything
        frame.setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height, Runnable action) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        // button.setFocusPainted(false);

        // Set a larger, bold font for readability
        // button.setFont(new Font("Arial", Font.BOLD, 24));

        // // Dark background + white text for good contrast
        // button.setBackground(Color.DARK_GRAY);
        // button.setForeground(Color.WHITE);

        // Action to perform when the button is clicked
        button.addActionListener(e -> action.run());
        return button;
    }

    private void retryGame() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.dispose();  // Close the Game Over screen
        RokueLikeGame.getInstance().initGame();  // Restart the game
    }

    private void goToMainMenu() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.dispose();  // Close the Game Over screen
        new MainMenu();  // Go back to the main menu
    }
}
