package cz.dix.mil;

import cz.dix.mil.view.settings.GameSettingsFrame;
import cz.dix.mil.view.skin.DefaultSkin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;

/**
 * Main class for staring game GUI.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameMain {

    public static void main(String[] args) throws InterruptedException {
        setupSystem();
        new GameSettingsFrame().setVisible(true);
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
