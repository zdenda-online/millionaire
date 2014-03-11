package cz.dix.mil;

import cz.dix.mil.view.settings.GameSettingsFrame;

/**
 * Main class for staring game GUI.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameMain extends BaseMain {

    public static void main(String[] args) throws InterruptedException {
        new GameSettingsFrame().setVisible(true);
    }
}
