package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class HighTimeStrategy implements WizardStrategy, Serializable {

    private long lastRuneChangeTime = 0;

    @Override
    public void runStrategy(WizardMonster wizard) {
        long now = System.currentTimeMillis();

        if(lastRuneChangeTime == 0){
            lastRuneChangeTime = wizard.getCreationTime();
        }

        if (now - lastRuneChangeTime >= 3000) {
            changeRuneLocation(wizard);
            lastRuneChangeTime = now;
        } else if (now - lastRuneChangeTime >= 1000){
            wizard.setSprite(Constants.WIZARD_SPRITE);
        }
    }

    private void changeRuneLocation(WizardMonster wizard) {
        List<GameObject> runeCarriers = RokueLikeGame.getInstance().getGameObjects().stream()
                .filter(GameObject::canHaveRuneInIt).toList();
        List<GameObject> theRune = runeCarriers.stream()
                .filter(GameObject::hasRune)
                .toList();

        if(!theRune.isEmpty()) {
            theRune.get(0).setHasRune(false);
            List<GameObject> others = runeCarriers.stream()
                    .filter(rc -> rc != theRune.get(0))
                    .toList();
            if(!others.isEmpty()) {
                Random r = new Random();
                int n = r.nextInt(others.size());
                others.get(n).setHasRune(true);
            }
            wizard.setSprite(Constants.WIZARD_CHARGED_SPRITE);
            System.out.println("Wizard changed the rune location");
        }
    }
}