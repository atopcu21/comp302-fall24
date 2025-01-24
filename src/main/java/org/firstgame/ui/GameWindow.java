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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;


public class GameWindow extends JPanel implements KeyListener, MouseListener, Serializable {
    private Image backgroundImage1;
    private Image backgroundImage2;
    private static GameWindow instance;
    private RokueLikeGame gameInstance;
    private final Timer gameTimer;
    private final Timer gameSaver;
    private JLabel timeLabel;
    private JLabel runeLabel;
    private long startTime;
    private long lastUpdatedTime;
    private boolean runeFound;
    private boolean monstersGenerating;
    private JPanel inventoryPanel;

    private JPanel healthPanel;
    private BufferedImage healthImage;
    private JLabel luringGemLabel;
    private JLabel cloakLabel;
    private JLabel revealLabel;

    private Timer timer;

    private boolean showFirstImage = true;

    private boolean enchantmentsGenerating = false;

    private JPanel yellowBoxnw;
    private JPanel yellowBoxne;
    private JPanel yellowBoxsw;
    private JPanel yellowBoxse;



    
    //highlight rune here
    
    private boolean highlightRune;

    private GameWindow() {

        backgroundImage1 = new ImageIcon("src/main/java/org/firstgame/assets/taslak01.png").getImage();
        backgroundImage2 = new ImageIcon("src/main/java/org/firstgame/assets/taslak02.png").getImage();

        luringGemLabel = createItemLabel("src/main/java/org/firstgame/assets/luringGem.png", RokueLikeGame.getInstance().getPlayer().isLuringGem());
        cloakLabel = createItemLabel("src/main/java/org/firstgame/assets/cloak.png", RokueLikeGame.getInstance().getPlayer().hasCloak());
        revealLabel = createItemLabel("src/main/java/org/firstgame/assets/reveal.png", RokueLikeGame.getInstance().getPlayer().isReveal());


        inventoryPanel = new JPanel();
        inventoryPanel.setOpaque(false);
        inventoryPanel.setPreferredSize(new Dimension(320, 420));
        inventoryPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20)); // Top, left, bottom, right margins
        
        luringGemLabel.setBounds(10, 100, 32, 32); // Adjust the size and position as needed
        cloakLabel.setBounds(50, 100, 32, 32); // Adjust the size and position as needed
        revealLabel.setBounds(90, 100, 32, 32); // Adjust the size and position as needed

        inventoryPanel.add(luringGemLabel);
        inventoryPanel.add(cloakLabel);
        inventoryPanel.add(revealLabel);
        inventoryPanel.setBounds(900, 310, 200, 100); // Set the position and size (x, y, width, height)
        healthPanel = new JPanel();
        healthPanel.setOpaque(false); // Make the health panel fully transparent// Ensure no background color
        healthPanel.setBounds(890, 280, 200, 100); // Set the position and size (x, y, width, height)
        
        add(healthPanel);
        add(inventoryPanel);
        
        try {
            healthImage = ImageIO.read(new File("src/main/java/org/firstgame/assets/heart.png")); // Load the health image
        } catch (IOException e) {
            e.printStackTrace();
        }
    



        highlightRune = false;

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
        
        //(160, 106, 514, 445)
        yellowBoxnw = new JPanel();
        yellowBoxnw.setBackground(new Color(255, 255, 255, 70)); // Yellow with 50% transparency
        yellowBoxnw.setBounds(160, 106, 257, 222); // Adjust the position and size as needed
        yellowBoxnw.setOpaque(true); // Set opaque to true
        yellowBoxnw.setVisible(false);
        this.add(yellowBoxnw);
        
        yellowBoxne = new JPanel();
        yellowBoxne.setBackground(new Color(255, 255, 255, 70)); // Red with 50% transparency
        yellowBoxne.setBounds(417, 106, 254, 222); // Adjust the position and size as needed
        yellowBoxne.setOpaque(true); // Set opaque to true
        yellowBoxne.setVisible(false);
        this.add(yellowBoxne);
        
        yellowBoxsw = new JPanel();
        yellowBoxsw.setBackground(new Color(255, 255, 255, 70)); // 
        yellowBoxsw.setBounds(160, 328, 257, 222); // Adjust the position and size as needed
        yellowBoxsw.setOpaque(true); // Set opaque to true
        yellowBoxsw.setVisible(false);
        this.add(yellowBoxsw);
        
        yellowBoxse = new JPanel();
        yellowBoxse.setBackground(new Color(255, 255, 255, 70)); // Green with 50% transparency
        yellowBoxse.setBounds(417, 328, 254, 222); // Adjust the position and size as needed
        yellowBoxse.setOpaque(true); // Set opaque to true
        yellowBoxse.setVisible(false);
        this.add(yellowBoxse);

        addMouseListener(this);

        gameTimer = new Timer(16, e -> updateScreen());
        gameTimer.start();
        startTime = System.currentTimeMillis();

        lastUpdatedTime = startTime;

        gameSaver = new Timer(5000, e -> saveGameStateRegularly());
        gameSaver.start();

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
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFirstImage = !showFirstImage;
                repaint();
            }
        });
        timer.start();
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

    public static void resetInstance() {
        instance = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showFirstImage) {
            g.drawImage(backgroundImage1, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.drawImage(backgroundImage2, 0, 0, getWidth(), getHeight(), this);
        }
        if (gameInstance != null) {
            for (GameObject gameObject : gameInstance.getGameObjects()) {
                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File(gameObject.getSprite()));
                    int width = image.getWidth();
                    int height = image.getHeight();
                    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = bi.createGraphics();

                    // Enable transparency
                    g2.setComposite(AlphaComposite.SrcOver);

                    AffineTransform transform = new AffineTransform();

                    if (gameObject instanceof Arrow) {
                        transform.translate(width / 2.0, height / 2.0);

                        if (gameObject.getFacingDirection() != null) {
                            transform.rotate(Math.toRadians(gameObject.getFacingDirection().getAngle()));
                        }

                        transform.translate(-width / 2.0, -height / 2.0);
                    }

                    if (gameObject.getFacingDirection() == Rotation.LEFT) {
                        transform.scale(-1, 1);
                        transform.translate(-width, 0);
                    } else if(gameObject.getFacingDirection() == null) {
                        transform.scale(1, 1);
                    }
    
                    g2.drawImage(image, transform, null);
    
                    if (gameObject.hasRune() && highlightRune) {
                        // print gameObject.getPosition to the console
                        double x = gameObject.getPosition().getX();
                        double y = gameObject.getPosition().getY();
                        System.out.println(gameObject.getPosition().getX() + " " + gameObject.getPosition().getY());
                        String runePosition = "";

                        if(x<6 && y<6){
                            runePosition = "NW";
                        }
                        else if(x>6 && y<6){
                            runePosition = "NE";
                        }
                        else if(x<6 && y>6){
                            runePosition = "SW";
                        }
                        else if(x>6 && y>6){
                            runePosition = "SE";
                        }
                        updateRunePosition(runePosition);

                        //bi = increaseBrightness(bi, 50);
                        updateEnchantmentIcons(); 
                    } else if (!highlightRune) {
                        updateRunePosition("");
                    }
    
                    g.drawImage(bi,
                            worldPositionToScreenPosition(gameObject.getPosition()).x() - image.getWidth(this) / 2,
                            worldPositionToScreenPosition(gameObject.getPosition()).y() - image.getHeight(this) / 2,
                            this);
                    g2.dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateRunePosition(String runePosition) {
        switch (runePosition) {
            case "NW":
                yellowBoxnw.setVisible(true);
                yellowBoxne.setVisible(false);
                yellowBoxsw.setVisible(false);
                yellowBoxse.setVisible(false);
                break;
            case "NE":
                yellowBoxnw.setVisible(false);
                yellowBoxne.setVisible(true);
                yellowBoxsw.setVisible(false);
                yellowBoxse.setVisible(false);
                break;
            case "SW":
                yellowBoxnw.setVisible(false);
                yellowBoxne.setVisible(false);
                yellowBoxsw.setVisible(true);
                yellowBoxse.setVisible(false);
                break;
            case "SE":
                yellowBoxnw.setVisible(false);
                yellowBoxne.setVisible(false);
                yellowBoxsw.setVisible(false);
                yellowBoxse.setVisible(true);
                break;
            default:
                yellowBoxnw.setVisible(false);
                yellowBoxne.setVisible(false);
                yellowBoxsw.setVisible(false);
                yellowBoxse.setVisible(false);
                break;
        }
    }

    public void updateScreen() {
        RokueLikeGame.getInstance().movePlayers();
        generateMonsters();
        generateEnchantments();
        moveMonsters();
        checkForCollisions();
        checkRuneFound();
        updateTime();
        updateEnchantmentIcons();
        repaint();
        updateHealth(RokueLikeGame.getInstance().getPlayer().getLives());
        if(RokueLikeGame.getInstance().isMultiplayer()) {
            if(RokueLikeGame.getInstance().getPlayer2() != null
                    && RokueLikeGame.getInstance().getPlayer2().getLives() <= 0) {
                RokueLikeGame.getInstance().gameOver();
            }
        }
        if(RokueLikeGame.getInstance().getPlayer().getLives() <= 0){
            RokueLikeGame.getInstance().gameOver();
        }
    }

    public void checkForCollisions() {
        for (GameObject gameObject : RokueLikeGame.getInstance().getGameObjects()) {
            gameObject.checkForCollisions();
        }
    }


    

    public void generateEnchantments() {
        // Spawns an enchantment every 12 seconds when (time % 12 == 11), similar to monster logic
        if (((System.currentTimeMillis() - startTime) / 1000) % 12 == 11) {
            if (!enchantmentsGenerating) {
                enchantmentsGenerating = true;
                Random r = new Random();
                int i = r.nextInt(5);
                Enchantment enchantment = null;
                switch (i) {
                    case 0: {
                        enchantment = new Enchantment("src/main/java/org/firstgame/assets/reveal.png");
                        break;
                    }
                    case 1: {
                        enchantment = new Enchantment("src/main/java/org/firstgame/assets/cloak.png");
                        break;
                    }
                    case 2: {
                        enchantment = new Enchantment("src/main/java/org/firstgame/assets/luringGem.png");
                        break;
                    }
                    case 3: {
                        enchantment = new Enchantment("src/main/java/org/firstgame/assets/reveal.png");
                        break;
                    }
                    case 4: {
                        enchantment = new Enchantment("src/main/java/org/firstgame/assets/time.png");
                        break;
                    }

                    // first aid kit testing
                    // case 0: {
                    //     enchantment = new Enchantment("src/main/java/org/firstgame/assets/firstAidKit.png");
                    //     break;
                    // }
                    // case 1: {
                    //     enchantment = new Enchantment("src/main/java/org/firstgame/assets/firstAidKit.png");
                    //     break;
                    // }
                    // case 2: {
                    //     enchantment = new Enchantment("src/main/java/org/firstgame/assets/firstAidKit.png");
                    //     break;
                    // }
                    // case 3: {
                    //     enchantment = new Enchantment("src/main/java/org/firstgame/assets/firstAidKit.png");
                    //     break;
                    // }
                    // case 4: {
                    //     enchantment = new Enchantment("src/main/java/org/firstgame/assets/firstAidKit.png");
                    //     break;
                    // }

                }
                RokueLikeGame.getInstance().addGameObject(enchantment);
                scheduleRemoveAllEnchantments();
            }
        } else {
            enchantmentsGenerating = false;
        }
    }
    private void scheduleRemoveAllEnchantments() {
        Timer timer = new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RokueLikeGame.getInstance().removeAllEnchantments();
            }
        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
    }

    private void scheduleRemoveHighlight() {
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightRune = false;
            }
        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
    }

    

    public void generateMonsters() {
        if(((System.currentTimeMillis() - startTime) / 1000) % 8 == 7){
            if(!monstersGenerating){
                monstersGenerating = true;
                Random r = new Random();
                boolean wizardExists = !RokueLikeGame.getInstance().getGameObjects().stream().filter(gameObject -> gameObject instanceof WizardMonster).toList().isEmpty();
                int randomRange;
                if (wizardExists) randomRange =  2; else randomRange = 3;
                int i = r.nextInt(randomRange);
                GameObject monster = null;
                switch (i) {
                    case 0: {
                        monster = new ArcherMonster();
                        break;
                    }
                    case 1: {
                        monster = new FighterMonster();
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
        List<GameObject> sceneObjects = new ArrayList<>(RokueLikeGame.getInstance().getGameObjects());
        for (GameObject gameObject : sceneObjects) {
            if(gameObject instanceof FighterMonster){
                if(((FighterMonster) gameObject).isLured()) {
                    ((FighterMonster) gameObject).move();
                } else {
                    if(((System.currentTimeMillis() - startTime) / 1000) % 3 == 0){
                        Random r = new Random();
                        ((FighterMonster) gameObject).setRotation(new Rotation(r.nextInt(360)));
                    } else {
                        ((FighterMonster) gameObject).move();
                    }
                }
            } else if (gameObject instanceof WizardMonster) {
                ((WizardMonster) gameObject).updateBehavior();
            } else if (gameObject instanceof ArcherMonster) {
                if(((System.currentTimeMillis() - ((ArcherMonster) gameObject).getTimeCreated()) / 1000) % 2 == 1){
                    ((ArcherMonster) gameObject).fireArrow(RokueLikeGame.getInstance().getPlayer());
                }
            } else if (gameObject instanceof Arrow) {
                ((Arrow) gameObject).move();
                if(((Arrow) gameObject).isOutOfRange){
                    RokueLikeGame.getInstance().getGameObjects().remove(gameObject);
                }
            } else if (gameObject instanceof Enchantment) {
                if(((Enchantment) gameObject).getOwner() != null){
                    ((Enchantment) gameObject).move();
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
        gameInstance.setAdventureTime(gameInstance.getAdventureTime() - (System.currentTimeMillis() - lastUpdatedTime));
        lastUpdatedTime = System.currentTimeMillis();
        if(gameInstance.getAdventureTime() <= 0){
            timeLabel.setText("Game Over");
            gameInstance.gameOver();
        } else{
            timeLabel.setText(gameInstance.getCurrentLevel() + " Hall / Time: " + formatTimeRemaining(gameInstance.getAdventureTime() / 1000));
        }

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
                        playSound("src/main/java/org/firstgame/assets/doorSound.wav");


                        // try {
                        //     AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        //     Clip clip = AudioSystem.getClip();
                        //     clip.open(audioStream);
                        //     clip.start();
                        // } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        //     ex.printStackTrace();
                        // }
                    }
                }
            }
        }
    }

    private void playSound(String filePath) {
        new Thread(() -> {
            try {
                File file = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        }).start();
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

    public void changeRuneHighlight() {
        if (!highlightRune) {
            highlightRune = true;
            scheduleRemoveHighlight();
        }
    }

    public void setBackgroundOfGW(String file1, String file2) {
        backgroundImage1 = new ImageIcon(file1).getImage();
        backgroundImage2 = new ImageIcon(file2).getImage();
        repaint();
    }
    public void updateHealth(int lives) {
        healthPanel.removeAll(); // Clear the existing health images
        for (int i = 0; i < lives; i++) {
            JLabel healthLabel = new JLabel(new ImageIcon(healthImage));
            healthPanel.add(healthLabel);
        }
        healthPanel.revalidate();
        healthPanel.repaint();
    }

    private JLabel createItemLabel(String imagePath, boolean isActive) {
        JLabel label = new JLabel();
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            if (!isActive) {
                image = convertToGrayscale(image);
            }
            label.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return label;
    }

    private BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return grayImage;
    }

    public void updateEnchantmentIcons() {
        Player player = RokueLikeGame.getInstance().getPlayer();
        if(player.isLuringGem()){
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("src/main/java/org/firstgame/assets/luringGem.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            luringGemLabel.setIcon(new ImageIcon(image));
        }
        else {
            try {
                luringGemLabel.setIcon(new ImageIcon(convertToGrayscale(ImageIO.read(new File("src/main/java/org/firstgame/assets/luringGem.png")))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(player.hasCloak()){
            try {
                BufferedImage image = ImageIO.read(new File("src/main/java/org/firstgame/assets/cloak.png"));
                cloakLabel.setIcon(new ImageIcon(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                cloakLabel.setIcon(new ImageIcon(convertToGrayscale(ImageIO.read(new File("src/main/java/org/firstgame/assets/cloak.png")))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
////////////////////////////////////////////////
        if(player.isReveal()){
            try {
                BufferedImage image = ImageIO.read(new File("src/main/java/org/firstgame/assets/reveal.png"));
                revealLabel.setIcon(new ImageIcon(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                revealLabel.setIcon(new ImageIcon(convertToGrayscale(ImageIO.read(new File("src/main/java/org/firstgame/assets/reveal.png")))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveGameStateRegularly(){
        RokueLikeGame.getInstance().saveGameState();
    }
}
