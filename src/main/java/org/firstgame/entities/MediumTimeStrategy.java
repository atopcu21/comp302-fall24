package org.firstgame.entities;

import org.firstgame.RokueLikeGame;

import java.io.Serializable;

public class MediumTimeStrategy implements WizardStrategy, Serializable {

    private boolean vanishScheduled = false;
    private long vanishStart = 0;

    @Override
    public void runStrategy(WizardMonster wizard) {
        if (!vanishScheduled) {
            vanishScheduled = true;
            vanishStart = System.currentTimeMillis();
        }
        long elapsed = System.currentTimeMillis() - vanishStart;
        if (elapsed > 2000) {
            RokueLikeGame.getInstance().removeGameObject(wizard);
        }
    }
}