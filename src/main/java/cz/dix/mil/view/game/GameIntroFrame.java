package cz.dix.mil.view.game;

import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;

/**
 * Frame that is shown when game is starting (starting sound plays) as an introduction.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameIntroFrame extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 100;
    private Skin skin = SkinManager.getSkin();

    private final GameModel model;
    private final JLabel welcomeLabel = new JLabel();
    private final JLabel infoLabel = new JLabel();

    public GameIntroFrame(GameModel model) {
        super();
        this.model = model;
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imgs/icon.png")).getImage());
        setLayout(new GridLayout(2, 1));
        setLocationRelativeTo(null);
        setResizable(false);

        welcomeLabel.setText("Welcome to the game: " + model.getGameName());
        welcomeLabel.setForeground(skin.defaultTextColor());
        welcomeLabel.setFont(skin.normalFont());
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);

        infoLabel.setText("The game will begin after the theme tune...");
        infoLabel.setForeground(skin.defaultTextColor());
        infoLabel.setFont(skin.smallerFont());
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setVerticalAlignment(SwingConstants.CENTER);

        add(welcomeLabel);
        add(infoLabel);
    }
}
