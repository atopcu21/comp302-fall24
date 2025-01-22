package org.firstgame.entities;

import java.io.Serializable;

public interface WizardStrategy extends Serializable {
    void runStrategy(WizardMonster wizard);
}