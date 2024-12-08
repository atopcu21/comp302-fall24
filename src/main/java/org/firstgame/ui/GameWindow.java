package org.firstgame.ui;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.ScreenPosition;
import org.firstgame.properties.WorldPosition;
import org.firstgame.entities.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class GameWindow extends JPanel implements KeyListener {
    private static GameWindow instance;
    private RokueLikeGame gameInstance;
    private final Timer gameTimer;
    private JLabel timeLabel;
    private long startTime;

    private GameWindow() {
        JFrame frame = new JFrame("First Game");
        frame.add(this);
        setBackground(new Color(40, 10, 35));
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
    }

    public void setGameInstance(RokueLikeGame gameInstance) {
        this.gameInstance = gameInstance;
    }

    public static GameWindow getInstance() {
        if(instance == null) {
            instance = new GameWindow();
        }
        return instance;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if(gameInstance != null) {
            for(GameObject gameObject : gameInstance.getGameObjects()) {
                Image image = toolkit.getImage(gameObject.getSprite());
                g.drawImage(image, worldPositionToScreenPosition(gameObject.getPosition()).x() - image.getWidth(this) / 2, worldPositionToScreenPosition(gameObject.getPosition()).y() - image.getHeight(this) / 2, this);
            }
        }
    }

    public void updateScreen() {
        RokueLikeGame.getInstance().movePlayer();
        updateTime();
        repaint();
    }

    public void updateTime() {
        long timeElapsed = (System.currentTimeMillis() - startTime) / 1000;
        if(gameInstance.getAdventureTime() - timeElapsed <= 0){
            timeLabel.setText("Game Over");
            gameInstance.gameOver();
        }else timeLabel.setText("Time: " + formatTimeRemaining(gameInstance.getAdventureTime() - timeElapsed));
    }

    public String formatTimeRemaining(long seconds) {
        if(seconds < 60) {
            return seconds + " seconds";
        } else if(seconds < 3600) {
            return seconds / 60 + " minutes " + (seconds % 60) + " seconds";
        } else if(seconds < 86400) {
            return seconds / 3600 + " hours " + ((seconds / 60) % 60) + " minutes " + (seconds % 60) + " seconds";
        } else return "-";
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        RokueLikeGame.getInstance().keyPressTriggered(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        RokueLikeGame.getInstance().keyReleaseTriggered(e.getKeyCode());
    }

    public ScreenPosition worldPositionToScreenPosition(WorldPosition worldPosition) {
        return new ScreenPosition((int) worldPosition.getX() + getWidth() / 2, (int) worldPosition.getY() + getHeight() / 2);
    }
}
