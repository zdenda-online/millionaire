package cz.dix.mil;

import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.GameFactory;
import cz.dix.mil.view.creator.CreatorFrame;
import cz.dix.mil.view.skin.DefaultSkin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;

/**
 * Main class for starting game creator GUI.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class CreatorMain {

    public static void main(String[] args) throws InterruptedException {
        setupSystem();
        Game gameTemplate = GameFactory.newGameTemplate();
        new CreatorFrame(gameTemplate).setVisible(true);
    }

    private static void setupSystem() {
        SkinManager.setSkin(new DefaultSkin());
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
