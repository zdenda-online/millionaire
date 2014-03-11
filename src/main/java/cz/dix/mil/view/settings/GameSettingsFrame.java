package cz.dix.mil.view.settings;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.GameSettings;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.validation.GameValidation;
import cz.dix.mil.model.game.validation.OriginalGameValidation;
import cz.dix.mil.view.common.GameFileChooser;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Frame that is shown at the beginning for choosing settings and starting the game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameSettingsFrame extends JFrame {

    private static final int WIDTH = 280;
    private static final int HEIGHT = 210;
    private static final int ITEMS_MARGIN = 10;

    // can be chosen as setting from combo-box in future maybe
    private static final GameValidation GAME_VALIDATION = new OriginalGameValidation();

    private static final String REAL_AUDIENCE_HINT = "By default, a result of audience hint (voting) is computed " +
            "automatically according to the difficulty of actual question.\n" +
            "However if you have a real audience around, you can select this option to" +
            " allow the insertion of audience votes manually.";
    private static final String REAL_PHONE_FRIEND_HINT = "By default, a result of phone friend hint is computed " +
            "automatically according to the difficulty of actual question.\n" +
            "However if player wants to call a real friend, you can select this option " +
            "and game will only show countdown of 30s.";
    private static final String SKIP_INTRO_HINT = "By default, the theme tune is played when the game starts and " +
            "the welcome window is shown during that time.\nYou can select this option to skip this introduction " +
            "which is played only for the effect.";

    private Game game;
    private JButton gameFileButton = new JButton(new ImageIcon(getClass().getResource("/imgs/folder.png")));
    private JTextField gameNameField = new JTextField("select game file -->");
    private CheckboxWithInfoPanel realAudiencePanel = new CheckboxWithInfoPanel("Real Audience", REAL_AUDIENCE_HINT);
    private CheckboxWithInfoPanel realPhoneFriendPanel = new CheckboxWithInfoPanel("Real Phone Friend", REAL_PHONE_FRIEND_HINT);
    private CheckboxWithInfoPanel skipIntroPanel = new CheckboxWithInfoPanel("Skip Intro", SKIP_INTRO_HINT);
    private JButton startButton = new JButton("Start game");


    public GameSettingsFrame() {
        super("Game Settings");
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imgs/icon.png")).getImage());
        setLocationRelativeTo(null);
        setResizable(false);

        gameNameField.setEnabled(false);
        gameFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectGameFile();
            }
        });
        gameFileButton.setFocusable(false);

        startButton.setEnabled(false);
        startButton.setForeground(SkinManager.getSkin().defaultTextColor());
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked!");
            }
        });

        JPanel gamePanel = new JPanel(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
        gamePanel.add(gameNameField, BorderLayout.CENTER);
        gamePanel.add(gameFileButton, BorderLayout.EAST);

        JPanel mainPanel = new JPanel(new GridLayout(5, 1, ITEMS_MARGIN, ITEMS_MARGIN));
        mainPanel.setBorder(new EmptyBorder(ITEMS_MARGIN, ITEMS_MARGIN, ITEMS_MARGIN, ITEMS_MARGIN));
        mainPanel.add(gamePanel);
        mainPanel.add(realAudiencePanel);
        mainPanel.add(realPhoneFriendPanel);
        mainPanel.add(skipIntroPanel);
        mainPanel.add(startButton);
        add(mainPanel);
    }

    /**
     * Shows file chooser for selecting game file.
     */
    private void selectGameFile() {
        this.game = new GameFileChooser(GAME_VALIDATION).importGame();
        startButton.setEnabled(game != null);
        if (game != null) {
            gameNameField.setText(game.getName());
        }
    }

    /**
     * Initializes controller and starts game with it.
     */
    private void startGame() {
        dispose();
        GameSettings settings = new GameSettings(game,
                realAudiencePanel.isCheckboxSelected(),
                realPhoneFriendPanel.isCheckboxSelected(),
                skipIntroPanel.isCheckboxSelected());
        GameController.newController(settings).startGame();
    }

    private class CheckboxWithInfoPanel extends JPanel {

        private final JCheckBox checkBox = new JCheckBox();

        private CheckboxWithInfoPanel(final String label, final String infoHint) {
            setLayout(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
            JPanel innerRightPanel = new JPanel(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
            innerRightPanel.add(new JLabel(label), BorderLayout.CENTER);
            innerRightPanel.add(checkBox, BorderLayout.EAST);
            add(innerRightPanel, BorderLayout.CENTER);


            JLabel hintIcon = new JLabel(new ImageIcon(getClass().getResource("/imgs/info.png")));
            hintIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(GameSettingsFrame.this, infoHint, label, JOptionPane.INFORMATION_MESSAGE);
                }
            });
            add(hintIcon, BorderLayout.EAST);
        }

        private boolean isCheckboxSelected() {
            return checkBox.isSelected();
        }
    }
}
