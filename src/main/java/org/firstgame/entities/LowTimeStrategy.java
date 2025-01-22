package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.WorldPosition;

import java.io.Serializable;
import java.util.Random;

public class LowTimeStrategy implements WizardStrategy, Serializable {

    private boolean hasTeleportedPlayer = false;

    @Override
    public void runStrategy(WizardMonster wizard) {
        long now = System.currentTimeMillis();
        if (now - wizard.getCreationTime() >= 1000 && !hasTeleportedPlayer) {
            hasTeleportedPlayer = true;
            teleportPlayer(wizard);
        } else if(now - wizard.getCreationTime() >= 2000) {
            RokueLikeGame.getInstance().removeGameObject(wizard);
        }
    }

    private void teleportPlayer(WizardMonster wizard) {
        Random random = new Random();
        WorldPosition randomPosition;

        int trialCount = 0;
        while (true) {
            double x = 1 + random.nextInt(10);
            double y = 1 + random.nextInt(10);
            randomPosition = new WorldPosition(x, y);
            if (wizard.canTeleportTo(randomPosition) || trialCount >= 64) {
                break;
            } else {
                trialCount++;
            }
        }

        RokueLikeGame.getInstance().getPlayer().setPosition(randomPosition);
        System.out.println("Wizard teleported the player to: " + randomPosition);
    }
}
