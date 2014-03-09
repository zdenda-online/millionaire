package cz.dix.mil.view.common;

import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.GameCreationException;
import cz.dix.mil.model.game.GameFactory;
import cz.dix.mil.model.game.validation.GameValidation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * File chooser that is used for loading game file
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameImportFileChooser extends JFileChooser {

    private static final File DEFAULT_DIR = new File("../games");
    private final GameValidation gameValidation;

    public GameImportFileChooser(GameValidation gameValidation) {
        super(DEFAULT_DIR);
        this.gameValidation = gameValidation;
        setDialogTitle("Game File Selection");
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setMultiSelectionEnabled(false);
        setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
    }

    /**
     * Imports the game from XML file provided by user.
     *
     * @return game instance or null if load failed (or closed)
     */
    public Game importGame() {
        if (showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Game importedGame = GameFactory.newGame(getSelectedFile(), gameValidation);
                JOptionPane.showMessageDialog(this, "Game file loaded successfully", "",
                        JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/imgs/approved.png")));
                return importedGame;
            } catch (GameCreationException e) {
                JOptionPane.showMessageDialog(this, "Game file load failed due to:\n" + e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }

    /**
     * Exports the game from given game instance to file provided by user.
     *
     * @param game game to be saved
     */
    public void exportGame(Game game) {
        if (showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                GameFactory.exportToXml(game, gameValidation, getSelectedFile());
                JOptionPane.showMessageDialog(this, "Game exported successfully", "",
                        JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/imgs/approved.png")));
            } catch (GameCreationException e) {
                JOptionPane.showMessageDialog(this, "Game export failed due to:\n" + e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
