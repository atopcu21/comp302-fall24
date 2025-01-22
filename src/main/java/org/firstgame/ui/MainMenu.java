package org.firstgame.ui;

import org.firstgame.RokueLikeGame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel {

    private Image backgroundImage1;
    private Image backgroundImage2;
    private boolean showFirstImage = true;
    private Timer timer;
    private boolean isMultiplayer;
    JButton multiPlayerButton;

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

        backgroundImage1 = new ImageIcon("src/main/java/org/firstgame/assets/background1.png").getImage();
        backgroundImage2 = new ImageIcon("src/main/java/org/firstgame/assets/background2.png").getImage();


        timer = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFirstImage = !showFirstImage;
                repaint();
            }
        });
        timer.start();

        initializeMenuComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showFirstImage) {
            g.drawImage(backgroundImage1, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.drawImage(backgroundImage2, 0, 0, getWidth(), getHeight(), this);
        }
}

    private void initializeMenuComponents() {
        JLabel titleLabel = createLabel();
        this.add(titleLabel);

        multiPlayerButton = createButton("<< 1P Mode >>", 28, 445, 390, 400, 55, this::changeMode);
        this.add(multiPlayerButton);

        JButton quickStartButton = createButton("Quick", 28, 445, 450, 195, 55, this::quickPlayAll);
        this.add(quickStartButton);

        JButton startButton = createButton("Build", 28, 650, 450, 195, 55, this::startBuilder);
        this.add(startButton);

        JButton loadButton = createButton("Load Game", 28, 445, 510, 400, 55, this::loadGame);
        this.add(loadButton);

        JButton helpButton = createButton("Help", 28, 545, 570, 200, 55, this::showHelpDialog);
        this.add(helpButton);

        JButton exitButton = createButton("Exit", 28, 545, 630, 200, 55, () -> System.exit(0));
        this.add(exitButton);

        JLabel imageLabel = createImageLabel("src/main/java/org/firstgame/assets/goRokue.png", 330, 10, 640, 360);
        this.add(imageLabel);
    }

    private void startBuilder() {
        BuilderWindowEarth.getInstance();
        Window win = SwingUtilities.getWindowAncestor(this);
        win.dispose();
    }

    private void quickPlayAll() {
        BuilderWindowEarth.quickPlay();
        BuilderWindowAir.quickPlay();
        BuilderWindowWater.quickPlay();
        BuilderWindowFire.quickPlay();
        Window win = SwingUtilities.getWindowAncestor(this);
        win.dispose();
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
        // loadGame();
        JOptionPane.showMessageDialog(this, "Instructions:\nUse Arrow keys to move your hero.\nClick on object to find runes.\nYou can collect power-ups as you go.", "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public void changeMode() {
        isMultiplayer = !isMultiplayer;
        RokueLikeGame.setMultiplayer(isMultiplayer);
        if (isMultiplayer) {
            multiPlayerButton.setText("<< 2P Mode >>");
            multiPlayerButton.updateUI();
        } else {
            multiPlayerButton.setText("<< 1P Mode >>");
            multiPlayerButton.updateUI();
        }
    }

    public void changeBackgroundColor(Color color) {
        setBackground(color);
        repaint();
    }

    public void loadGame() {
        RokueLikeGame.getInstance().loadGameState();
    }
}
