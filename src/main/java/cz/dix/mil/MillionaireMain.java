package cz.dix.mil;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.controller.SoundsController;
import cz.dix.mil.model.GameValidationException;
import cz.dix.mil.model.ModelFactory;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.state.GameModel;
import cz.dix.mil.ui.GameView;

import javax.swing.*;
import java.io.File;

/**
 * Main class of the millionaire game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class MillionaireMain {

    public static void main(String[] args) throws InterruptedException {
        setupSystem();
        if (args == null || args.length != 1) {
            System.out.println("Expecting one argument with path to game XML file");
            System.exit(1);
        }
        File gameFile = new File(args[0]);
        if (!gameFile.exists()) {
            System.out.println("Given game file does exist in " + gameFile.getAbsoluteFile());
            System.exit(2);
        }
        Game game = ModelFactory.newGame(gameFile);
        try {
            game.validate();
        } catch (GameValidationException e) {
            System.out.println("Game file is not valid due to: " + e.getMessage());
            System.exit(3);
        }

        final GameModel model = new GameModel(game);
        final GameController controller = new GameController(model);
        final GameView view = new GameView(model, controller);
        controller.setView(view);

        controller.startGame();
    }

    private static void setupSystem() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
