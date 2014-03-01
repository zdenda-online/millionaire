package cz.dix.mil;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.GameFactory;
import cz.dix.mil.model.GameValidationException;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.state.GameModel;
import cz.dix.mil.sound.SoundsPlayer;
import cz.dix.mil.ui.MainFrame;
import cz.dix.mil.ui.WaitAnswerFrame;

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
        Game game = GameFactory.newGame(gameFile);
        try {
            game.validate();
        } catch (GameValidationException e) {
            System.out.println("Game file is not valid due to: " + e.getMessage());
            System.exit(3);
        }

        GameModel model = new GameModel(game);
        SoundsPlayer soundsPlayer = new SoundsPlayer(model);
        GameController controller = new GameController(model);
        MainFrame view = new MainFrame(model, controller);
        WaitAnswerFrame view2 = new WaitAnswerFrame(controller);

        controller.registerAnswerListeners(view2, view.getAnswersPanel(), soundsPlayer);
        controller.registerHintsListeners(soundsPlayer, view.getHintsPanel(), view.getAnswersPanel());
        controller.registerGameListeners(soundsPlayer, view.getQuestionsPanel(), view.getAnswersPanel(), view.getRewardsPanel());
        controller.startGame();

        view.setVisible(true);
    }

    private static void setupSystem() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
