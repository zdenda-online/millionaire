package cz.dix.mil;

import cz.dix.mil.view.skin.DefaultSkin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;

/**
 * Base main class from which should extend all main classes while it setups the system
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class BaseMain {

    static {
        SkinManager.setSkin(new DefaultSkin());
        UIManager.put("OptionPane.font", SkinManager.getSkin().smallerFont());
        UIManager.put("OptionPane.messageFont", SkinManager.getSkin().smallerFont());
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
