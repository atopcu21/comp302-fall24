package org.firstgame.ui;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Rotation;
import org.firstgame.properties.ScreenPosition;
import org.firstgame.properties.WorldPosition;
import org.firstgame.entities.GameObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameWindow extends JPanel implements KeyListener {
    private static GameWindow instance;
    private RokueLikeGame gameInstance;
    private final Timer gameTimer;
    private JLabel timeLabel;
    private long startTime;
    private boolean isPaused;

    private GameWindow() {
        setBackground(new Color(40, 10, 35));
        JFrame frame = new JFrame("First Game");
        frame.add(this);
        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        gameTimer = new Timer(16, e -> updateScreen());
        gameTimer.start();
        startTime = System.currentTimeMillis();
        isPaused = false;

        timeLabel = new JLabel("Time: 0 seconds");
        timeLabel.setBounds((getWidth() / 2) - 200, 10, 400, 30);
        timeLabel.setLayout(new FlowLayout());
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.WHITE);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/org/firstgame/fonts/custom_font.ttf")).deriveFont(14f);
            timeLabel.setFont(customFont);
        } catch (FontFormatException | IOException ignored) {

        }
        this.add(timeLabel);

        createPauseResumeButtons();

        if (!gameTimer.isRunning()) {
            gameTimer.start();
        }
    }

    public void setGameInstance(RokueLikeGame gameInstance) {
        this.gameInstance = gameInstance;
    }

    public static GameWindow getInstance() {
        if (instance == null) {
            instance = new GameWindow();
        }
        return instance;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameInstance != null && !isPaused) {
            for (GameObject gameObject : gameInstance.getGameObjects()) {
                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File(gameObject.getSprite()));
                    int width = image.getWidth();
                    int height = image.getHeight();
                    BufferedImage bi = new BufferedImage(width, height, image.getType());
                    Graphics2D g2 = bi.createGraphics();

                    AffineTransform transform = new AffineTransform();

                    if (gameObject.getFacingDirection() == Rotation.LEFT) {
                        transform.scale(-1, 1); // Mirror horizontally
                        transform.translate(-width, 0); // Adjust to original position
                    } else {
                        transform.scale(1, 1); // Normal rendering
                    }

                    // Apply the transform
                    g2.drawImage(image, transform, null);

                    // Draw on screen
                    g.drawImage(bi,
                            worldPositionToScreenPosition(gameObject.getPosition()).x() - image.getWidth(this) / 2,
                            worldPositionToScreenPosition(gameObject.getPosition()).y() - image.getHeight(this) / 2,
                            this);
                } catch (IOException e) {
                    System.err.println("Error loading sprite: " + gameObject.getSprite());
                }
            }
        }
    }

    public void updateScreen() {
        if (!isPaused) {
            RokueLikeGame.getInstance().movePlayer();
            updateTime();
            repaint();
        }
    }

    public void updateTime() {
        long timeElapsed = (System.currentTimeMillis() - startTime) / 1000;
        if (gameInstance.getAdventureTime() - timeElapsed <= 0) {
            timeLabel.setText("Game Over");
            gameInstance.gameOver();
        } else {
            timeLabel.setText("Time: " + formatTimeRemaining(gameInstance.getAdventureTime() - timeElapsed));
        }
    }

    public String formatTimeRemaining(long seconds) {
        if (seconds < 60) {
            return seconds + " seconds";
        } else if (seconds < 3600) {
            return seconds / 60 + " minutes " + (seconds % 60) + " seconds";
        } else if (seconds < 86400) {
            return seconds / 3600 + " hours " + ((seconds / 60) % 60) + " minutes " + (seconds % 60) + " seconds";
        } else {
            return "-";
        }
    }

    private void createPauseResumeButtons() {
        JButton pauseButton = new JButton("Pause");
        pauseButton.setBounds(10, 10, 100, 30);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = true;
                timeLabel.setText("Game Paused");
                gameTimer.stop(); // Stop the timer
            }
        });

        JButton resumeButton = new JButton("Resume");
        resumeButton.setBounds(120, 10, 100, 30);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = false;
                timeLabel.setText("Game Resumed");
                gameTimer.start(); // Restart the timer
            }
        });

        this.add(pauseButton);
        this.add(resumeButton);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isPaused) {
            RokueLikeGame.getInstance().keyPressTriggered(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!isPaused) {
            RokueLikeGame.getInstance().keyReleaseTriggered(e.getKeyCode());
        }
    }

    public ScreenPosition worldPositionToScreenPosition(WorldPosition worldPosition) {
        return new ScreenPosition((int) worldPosition.getX() + getWidth() / 2, (int) worldPosition.getY() + getHeight() / 2);
    }
}
