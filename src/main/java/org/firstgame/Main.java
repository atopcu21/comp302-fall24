package org.firstgame;

import org.firstgame.ui.MainMenu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
