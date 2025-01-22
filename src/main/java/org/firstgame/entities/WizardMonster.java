package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;
import org.firstgame.properties.WorldPosition;

import java.io.Serializable;
import java.util.Random;

public class WizardMonster extends Monster implements Serializable {
    private WizardStrategy strategy;
    private final long creationTime;

    public WizardMonster() {
        super();
        setSprite(Constants.WIZARD_SPRITE);
        this.creationTime = System.currentTimeMillis();
        Random rand = new Random();
        int x = rand.nextInt(10) + 1;
        int y = rand.nextInt(10) + 1;
        setPosition(x, y);
    }
    public void updateBehavior() {
        double fraction = getFractionOfTimeRemaining();
        WizardStrategy newStrategy = pickStrategy(fraction);
        if (strategy == null || !strategy.getClass().equals(newStrategy.getClass())) {
            strategy = newStrategy;
            setSprite(Constants.WIZARD_SPRITE);
        }

        strategy.runStrategy(this);
    }

    private WizardStrategy pickStrategy(double fraction) {
        if (fraction < 0.3) {
            return new LowTimeStrategy();
        } else if (fraction > 0.7) {
            return new HighTimeStrategy();
        } else {
            return new MediumTimeStrategy();
        }
    }

    private double getFractionOfTimeRemaining() {
        long currentTime = RokueLikeGame.getInstance().getAdventureTime();
        long initialTime = RokueLikeGame.getInstance().getTotalTime();
        if (initialTime <= 0) {
            return 0.0;
        }
        return (double) currentTime / (double) initialTime;
    }

    public boolean canTeleportTo(WorldPosition pos) {
        double x = pos.getX(), y = pos.getY();
        return !(x < 0.5) && !(x > 10.5) && !(y < 0.5) && !(y > 10.5);
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        super.onCollusion(otherObject);
    }

    public long getCreationTime() {
        return creationTime;
    }
}




