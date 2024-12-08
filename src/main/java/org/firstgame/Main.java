package org.firstgame;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static RokueLikeGame gameInstance;
    public static void main(String[] args) {
        gameInstance = RokueLikeGame.getInstance();
    }
}