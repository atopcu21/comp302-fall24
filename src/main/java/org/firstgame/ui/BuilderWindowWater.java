package org.firstgame.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.firstgame.RokueLikeGame;
import org.firstgame.entities.GameObject;
import static org.firstgame.properties.Constants.BUILD_BOX_DOUBLE_SPRITE;
import static org.firstgame.properties.Constants.BUILD_BOX_SMALL_SPRITE;
import static org.firstgame.properties.Constants.BUILD_CHEST_SPRITE;
import static org.firstgame.properties.Constants.BUILD_COLUMN_SPRITE;
import static org.firstgame.properties.Constants.NEXT_BUTTON_SPRITE;
import static org.firstgame.properties.Constants.WALL_DOWN_SPRITE;
import static org.firstgame.properties.Constants.WALL_LEFT_SPRITE;
import static org.firstgame.properties.Constants.WALL_RIGHT_SPRITE;
import static org.firstgame.properties.Constants.WALL_UP_SPRITE;
import static org.firstgame.properties.Constants.WORLD_MARGIN_X;
import static org.firstgame.properties.Constants.WORLD_MARGIN_Y;
import org.firstgame.properties.ScreenPosition;
import org.firstgame.properties.WorldPosition;

public class BuilderWindowWater extends JPanel implements MouseListener, MouseMotionListener {
    private static BuilderWindowWater instance;
    private GameObject currentItem;
    private List<GameObject> placedObjects = new ArrayList<>();
    private JLabel levelLabel;

    private BuilderWindowWater() {
        JFrame frame = new JFrame("Game Builder");
        frame.add(this);
        setBackground(new Color(40, 10, 35));
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        levelLabel = new JLabel("Building Water Hall");
        levelLabel.setBounds((getWidth() / 2) - 200, 10, 400, 30);
        levelLabel.setLayout(new FlowLayout());
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setForeground(Color.WHITE);

        JButton randomlyFillButton = new JButton("Random");
        randomlyFillButton.setBounds(1050, 600, 200, 60);
        randomlyFillButton.setFocusPainted(false);
        randomlyFillButton.setBackground(Color.DARK_GRAY);
        randomlyFillButton.setForeground(new Color(30, 5, 30));
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/org/firstgame/fonts/custom_font.ttf")).deriveFont(28f);
            randomlyFillButton.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            randomlyFillButton.setFont(new Font("Arial", Font.BOLD, 28));
        }
        randomlyFillButton.addActionListener(e -> randomlyFill());
        this.add(randomlyFillButton);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/org/firstgame/fonts/custom_font.ttf")).deriveFont(14f);
            levelLabel.setFont(customFont);
        } catch (FontFormatException | IOException ignored) {

        }
        this.add(levelLabel);
        addMouseListener(this);
        addMouseMotionListener(this);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static BuilderWindowWater getInstance() {
        if(instance == null) {
            instance = new BuilderWindowWater();
        }
        return instance;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage chest = null;
        BufferedImage column = null;
        BufferedImage smallBox = null;
        BufferedImage doubleBox = null;
        BufferedImage wallUp = null;
        BufferedImage wallDown = null;
        BufferedImage wallLeft = null;
        BufferedImage wallRight = null;
        BufferedImage nextButton = null;
        BufferedImage skull = null;

        // Load new images
        BufferedImage chest_t1 = null;
        BufferedImage chest_t2 = null;

        try {
            chest = ImageIO.read(new File(BUILD_CHEST_SPRITE));
            wallUp = ImageIO.read(new File(WALL_UP_SPRITE));
            wallDown = ImageIO.read(new File(WALL_DOWN_SPRITE));
            wallLeft = ImageIO.read(new File(WALL_LEFT_SPRITE));
            wallRight = ImageIO.read(new File(WALL_RIGHT_SPRITE));
            column = ImageIO.read(new File(BUILD_COLUMN_SPRITE));
            doubleBox = ImageIO.read(new File(BUILD_BOX_DOUBLE_SPRITE));
            smallBox = ImageIO.read(new File(BUILD_BOX_SMALL_SPRITE));
            nextButton = ImageIO.read(new File(NEXT_BUTTON_SPRITE));



            chest_t1 = ImageIO.read(new File("src/main/java/org/firstgame/assets/chest-t1.png"));
            chest_t2 = ImageIO.read(new File("src/main/java/org/firstgame/assets/chest-t2.png"));
            skull = ImageIO.read(new File("src/main/java/org/firstgame/assets/skull.png"));


        } catch (Exception e){
            // ignored
        }
        g.drawImage(chest, 900, 125, null);
        g.drawImage(column, 947, 200, null);
        g.drawImage(doubleBox, 947, 275, null);
        g.drawImage(smallBox, 947, 350, null);
        g.drawImage(chest_t1, 947, 425, null);
        g.drawImage(chest_t2, 947, 475, null);
        g.drawImage(skull, 947, 525, null);

        for (int i = 0; i < 12; i++){
            g.drawImage(wallUp, (i * 44) + 250, 100, null);
            g.drawImage(wallDown, (i * 44) + 250, 628, null);
            g.drawImage(wallLeft, 250, (i * 44) + 100, null);
            g.drawImage(wallRight, 769, (i * 44) + 100, null);
        }
        for (GameObject item : placedObjects) {
            try {
                BufferedImage bi = ImageIO.read(new File(item.getSprite()));
                g.drawImage(bi, worldPositionToScreenPosition(item.getPosition()).x() - bi.getWidth() / 2, worldPositionToScreenPosition(item.getPosition()).y() - bi.getHeight() / 2, null);
            } catch (IOException e) {
                // ignored
            }
        }


        if (placedObjects.size() >= 13) {
            g.drawImage(nextButton, 925, 582, null);
        }


        if(currentItem != null) {
            try {
                BufferedImage bi = ImageIO.read(new File(currentItem.getSprite()));
                g.drawImage(bi, worldPositionToScreenPosition(currentItem.getPosition()).x() - bi.getWidth() / 2, worldPositionToScreenPosition(currentItem.getPosition()).y() - bi.getHeight() / 2, null);
            } catch (IOException e) {
                // ignored
            }
        }

    }

    public ScreenPosition worldPositionToScreenPosition(WorldPosition worldPosition) {
        return new ScreenPosition((int) ((worldPosition.getX() + 5) * 44), (int) ((worldPosition.getY() + 3) * 44));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    private boolean isInsideWalls(Point point) {
        return point.x > 250 && point.x < 769 && point.y > 100 && point.y < 628;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point clickPoint = e.getPoint();
        if(clickPoint.x >= 925 && clickPoint.x <= 925 + 70 && clickPoint.y >= 582 && clickPoint.y <= 582 + 36 && placedObjects.size() >= 6) {
            goToNextLevelBuilder();
        }
        if (clickPoint.x >= 947 && clickPoint.x <= 947 + 44) {
            if (clickPoint.y >= 200 && clickPoint.y <= 200 + 44) {
                currentItem = new GameObject(screenPositionToWorldPosition(new ScreenPosition(e.getX() - 22, e.getY() - 22)), BUILD_COLUMN_SPRITE);
            } else if (clickPoint.y >= 275 && clickPoint.y <= 275 + 44) {
                currentItem = new GameObject(screenPositionToWorldPosition(new ScreenPosition(e.getX() - 22, e.getY() - 22)), BUILD_BOX_DOUBLE_SPRITE);
            } else if (clickPoint.y >= 350 && clickPoint.y <= 350 + 44) {
                currentItem = new GameObject(screenPositionToWorldPosition(new ScreenPosition(e.getX() - 22, e.getY() - 22)), BUILD_BOX_SMALL_SPRITE);
            } else if (clickPoint.y >= 425 && clickPoint.y <= 425 + 44) {
                currentItem = new GameObject(screenPositionToWorldPosition(new ScreenPosition(e.getX() - 22, e.getY() - 22)), "src/main/java/org/firstgame/assets/chest-t1.png");
            } else if (clickPoint.y >= 475 && clickPoint.y <= 475 + 44) {
                currentItem = new GameObject(screenPositionToWorldPosition(new ScreenPosition(e.getX() - 22, e.getY() - 22)), "src/main/java/org/firstgame/assets/chest-t2.png");
            } else if (clickPoint.y >= 525 && clickPoint.y <= 525 + 44) {
                currentItem = new GameObject(screenPositionToWorldPosition(new ScreenPosition(e.getX() - 22, e.getY() - 22)), "src/main/java/org/firstgame/assets/skull.png");
            }
        } else {
            if (currentItem != null && isInsideWalls(clickPoint)) {
                currentItem.setPosition(screenPositionToWorldPosition(new ScreenPosition(clickPoint.x - 22, clickPoint.y - 22)));
                placedObjects.add(currentItem);
                currentItem = null;
            }
        }
        repaint();
    }

    public void goToNextLevelBuilder() {
        for (GameObject item : placedObjects) {
            item.setPosition(item.getPosition().getX() - 1.2, item.getPosition().getY() - 0.5);
        }
        Random r = new Random();
        placedObjects.get(r.nextInt(placedObjects.size())).setHasRune(true);
        RokueLikeGame.getInstance().addGameObjects(placedObjects, "Water");
        BuilderWindowFire.getInstance();
    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public WorldPosition screenPositionToWorldPosition(ScreenPosition screenPosition) {
        return new WorldPosition(((double) screenPosition.x() / 44) - WORLD_MARGIN_X, ((double) screenPosition.y() / 44) - WORLD_MARGIN_Y);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(currentItem != null) {
            currentItem.setPosition(screenPositionToWorldPosition(new ScreenPosition(e.getX() - 22, e.getY() - 22)));
            repaint();
        }
    }

    private void randomlyFill() {
        String[] options = {BUILD_COLUMN_SPRITE, BUILD_BOX_DOUBLE_SPRITE, BUILD_BOX_SMALL_SPRITE, "src/main/java/org/firstgame/assets/chest-t1.png", "src/main/java/org/firstgame/assets/chest-t2.png", "src/main/java/org/firstgame/assets/skull.png"};
        Random random = new Random();
        placedObjects.clear(); // Clear existing objects if needed
    
        List<Point> usedPositions = new ArrayList<>(); // Track used positions

        for (int i = 0; i < 13; i++) {
            String selectedSprite = options[random.nextInt(options.length)];
    
            int randomX, randomY;
            Point position;
            do {
                randomX = random.nextInt(9) + 2; // Random X within the grid (2 to 10)
                randomY = random.nextInt(9) + 2; // Random Y within the grid (2 to 10)
                position = new Point(randomX, randomY); // Represent position as a Point
            } while (usedPositions.contains(position)); // Ensure the position is unique
    
            usedPositions.add(position); // Mark position as used
    
            GameObject gameObject = new GameObject(new WorldPosition(randomX, randomY), selectedSprite);
            placedObjects.add(gameObject);
        }
        repaint();
    }

    public static void quickPlay() {
        BuilderWindowWater instance = getInstance();
        instance.randomlyFill();
        instance.goToNextLevelBuilder();
    }
}
