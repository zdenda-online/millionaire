package cz.dix.mil.cmd;

import com.beust.jcommander.JCommander;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.GameValidationException;
import cz.dix.mil.model.ModelFactory;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.state.GameModel;
import cz.dix.mil.ui.GameView;

import javax.swing.*;

/**
 * Main class of the millionaire game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class MillionaireMain {

    public static void main(String[] args) throws InterruptedException {
        setupSystem();
        final CmdOptions options = new CmdOptions();
        JCommander cmd = new JCommander(options, args);

        if (options.isHelp()) {
            cmd.usage();
            System.exit(0);
        }

        Game game = ModelFactory.newGame(options.getGameFile());
        try {
            game.validate();
        } catch (GameValidationException e) {
            System.out.println("Game file is not valid due to: " + e.getMessage());
            System.exit(3);
        }

        final GameModel model = new GameModel(game);
        final GameController controller = new GameController(model, options);
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
