package cz.dix.mil;

import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.GameFactory;
import cz.dix.mil.view.creator.CreatorFrame;

/**
 * Main class for starting game creator GUI.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class CreatorMain extends BaseMain {

    public static void main(String[] args) throws InterruptedException {
        Game gameTemplate = GameFactory.newGameTemplate();
        new CreatorFrame(gameTemplate).setVisible(true);
    }
}
