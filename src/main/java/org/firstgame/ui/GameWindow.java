package org.firstgame.ui;

import org.firstgame.RokueLikeGame;
import org.firstgame.entities.*;
import org.firstgame.properties.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class GameWindow extends JPanel implements KeyListener, MouseListener {
    private static GameWindow instance;
    private RokueLikeGame gameInstance;
    private final Timer gameTimer;
    private JLabel timeLabel;
    private JLabel runeLabel;
    private long startTime;
    private boolean runeFound;
    private boolean monstersGenerating;

    private GameWindow() {
        JFrame frame = new JFrame("Rokue Like Game");
        frame.add(this);
        setBackground(new Color(40, 10, 35));
        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        addMouseListener(this);

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

        runeLabel = new JLabel("");
        runeLabel.setBounds((getWidth() / 2) - 200, 40, 400, 30);
        runeLabel.setLayout(new FlowLayout());
        runeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        runeLabel.setForeground(Color.WHITE);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/org/firstgame/fonts/custom_font.ttf")).deriveFont(14f);
            runeLabel.setFont(customFont);
        } catch (FontFormatException | IOException ignored) {

        }

        this.add(timeLabel);
        this.add(runeLabel);
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
        if(gameInstance != null) {
            for(GameObject gameObject : gameInstance.getGameObjects()) {
                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File(gameObject.getSprite()));
                    int width = image.getWidth();
                    int height = image.getHeight();
                    BufferedImage bi = new BufferedImage(width, height, image.getType());
                    Graphics2D g2 = bi.createGraphics();

                    AffineTransform transform = new AffineTransform();

                    if(gameObject.getFacingDirection() == Rotation.LEFT){
                        transform.scale(-1, 1);
                        transform.translate(-width, 0);
                    } else {
                        transform.scale(1, 1);
                    }

                    g2.drawImage(image, transform, null);

                    if(gameObject.hasRune()){
                        bi = increaseBrightness(bi, 50);
                    }

                    g.drawImage(bi,
                            worldPositionToScreenPosition(gameObject.getPosition()).x() - image.getWidth(this) / 2,
                            worldPositionToScreenPosition(gameObject.getPosition()).y() - image.getHeight(this) / 2,
                            this);
                } catch (IOException e) {
                    // throw new RuntimeException(e);
                }
            }
        }
    }

    public void updateScreen() {
        RokueLikeGame.getInstance().movePlayer();
        generateMonsters();
        moveMonsters();
        checkForCollisions();
        checkRuneFound();
        updateTime();
        repaint();
    }

    public void checkForCollisions() {
        for (GameObject gameObject : RokueLikeGame.getInstance().getGameObjects()) {
            gameObject.checkForCollisions();
        }
    }

    public void generateMonsters() {
        if(((System.currentTimeMillis() - startTime) / 1000) % 8 == 7){
            if(!monstersGenerating){
                monstersGenerating = true;
                Random r = new Random();
                int i = r.nextInt(3);
                GameObject monster = null;
                switch (i) {
                    case 0: {
                        monster = new FighterMonster();
                        break;
                    }
                    case 1: {
                        monster = new ArcherMonster();
                        break;
                    }
                    case 2: {
                        monster = new WizardMonster();
                        break;
                    }
                }
                RokueLikeGame.getInstance().addGameObject(monster);
            }
        } else {
            monstersGenerating = false;
        }
    }

    public void moveMonsters() {
        for (GameObject gameObject : RokueLikeGame.getInstance().getGameObjects()) {
            if(gameObject instanceof FighterMonster){
                if(((System.currentTimeMillis() - startTime) / 1000) % 3 == 0){
                    Random r = new Random();
                    ((FighterMonster) gameObject).setRotation(new Rotation(r.nextInt(360)));
                } else {
                    ((FighterMonster) gameObject).move();
                }
            } else if (gameObject instanceof WizardMonster) {
                if(((System.currentTimeMillis() - startTime) / 1000) % 5 == 4){
                    List<GameObject> runeCarryingObjects = RokueLikeGame.getInstance()
                            .getGameObjects()
                            .stream()
                            .filter(it -> it.canHaveRuneInIt() && !it.hasRune()).toList();
                    List<GameObject> theRune = RokueLikeGame.getInstance()
                            .getGameObjects()
                            .stream()
                            .filter(it -> it.canHaveRuneInIt() && it.hasRune()).toList();
                    if(!theRune.isEmpty()){
                        theRune.get(0).setHasRune(false);
                        Random r = new Random();
                        int n = r.nextInt(runeCarryingObjects.size());
                        runeCarryingObjects.get(n).setHasRune(true);
                        gameObject.setSprite(Constants.WIZARD_CHARGED_SPRITE);
                    }
                } else if(((System.currentTimeMillis() - startTime) / 1000) % 5 == 0){
                    gameObject.setSprite(Constants.WIZARD_SPRITE);
                }
            }
        }
    }

    public void checkRuneFound() {
        if(runeFound){
            runeLabel.setText("Rune Found");
        }
    }

    public void updateTime() {
        long timeElapsed = (System.currentTimeMillis() - startTime) / 1000;
        if(gameInstance.getAdventureTime() - timeElapsed <= 0){
            timeLabel.setText("Game Over");
            gameInstance.gameOver();
        } else timeLabel.setText(gameInstance.getCurrentLevel() + " Hall / Time: " + formatTimeRemaining(gameInstance.getAdventureTime() - timeElapsed));
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
        return new ScreenPosition((int) ((worldPosition.getX() + Constants.WORLD_MARGIN_X) * 44), (int) ((worldPosition.getY() + Constants.WORLD_MARGIN_Y) * 44));
    }

    public void renderInventory(Graphics g) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GameObject gameObject : RokueLikeGame.getInstance().getGameObjects()) {
            if(Math.abs(RokueLikeGame.getInstance().getPlayer().getPosition().getX() - gameObject.getPosition().getX()) +
                    Math.abs(RokueLikeGame.getInstance().getPlayer().getPosition().getY() - gameObject.getPosition().getY()) <= 1){
                if(gameObject.hasRune()) {
                    runeFound = true;
                    gameObject.setHasRune(false);
                    List<GameObject> door = RokueLikeGame.getInstance().getGameObjects().stream().filter(it -> it instanceof Door).toList();
                    if(!door.isEmpty()){
                        Door d = (Door) door.get(0);
                        d.setOpened(true);
                    }
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void stopTimer() {
        gameTimer.stop();
    }

    public void winGame() {
        gameInstance.addAdventureTime();
        runeFound = false;
        runeLabel.setText("");
        repaint();
    }

    public BufferedImage increaseBrightness(BufferedImage image, int brightnessIncrease) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage brightenedImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                red = Math.min(255, red + brightnessIncrease);
                green = Math.min(255, green + brightnessIncrease);
                blue = Math.min(255, blue + brightnessIncrease);

                int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                brightenedImage.setRGB(x, y, newRgb);
            }
        }

        return brightenedImage;
    }
}
