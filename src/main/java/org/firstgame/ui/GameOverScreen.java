package org.firstgame.ui;

import javax.swing.*;

import org.firstgame.RokueLikeGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {
    public GameOverScreen() {
        JFrame frame = new JFrame("Game Over");
        frame.add(this);
        setBackground(new Color(40, 10, 35));
        setLayout(null);
        setFocusable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setBounds(440, 100, 400, 80);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        add(gameOverLabel);

        JButton retryButton = createButton("Retry", 540, 300, 200, 60, this::retryGame);
        JButton mainMenuButton = createButton("Main Menu", 540, 400, 200, 60, this::goToMainMenu);
        JButton exitButton = createButton("Exit", 540, 500, 200, 60, () -> System.exit(0));

        add(retryButton);
        add(mainMenuButton);
        add(exitButton);
    }

    private JButton createButton(String text, int x, int y, int width, int height, Runnable action) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFocusPainted(false);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 24));
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
