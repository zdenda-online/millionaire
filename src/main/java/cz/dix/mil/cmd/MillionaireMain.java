package cz.dix.mil.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.GameValidationException;
import cz.dix.mil.model.ModelFactory;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.ui.GameView;

import javax.swing.*;

/**
 * Main class of the millionaire game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class MillionaireMain {

    private static final boolean IS_WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws InterruptedException {
        CmdOptions options = new CmdOptions();
        JCommander cmd = null;
        try {
            cmd = new JCommander(options, args);
            cmd.setProgramName("millionaire" + (IS_WIN ? ".bat" : ".sh"));
        } catch (ParameterException e) {
            System.out.println(e.getMessage() + "\nFor more information use -h for help");
            System.exit(1);
        }

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

        setupSystem();
        GameModel model = new GameModel(game);
        GameController controller = new GameController(model, options);
        GameView view = new GameView(model, controller);
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
