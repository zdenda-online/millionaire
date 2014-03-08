package cz.dix.mil.view.settings;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.GameSettings;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.GameValidationException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Frame that is shown at the beginning for choosing settings and starting the game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameSettingsFrame extends JFrame {

    private static final int WIDTH = 230;
    private static final int HEIGHT = 135;
    private static final int ITEMS_MARGIN = 5;
    private static final String REAL_AUDIENCE_HINT = "By default, a result of audience hint (voting) is computed " +
            "automatically according to the difficulty of actual question.\n" +
            "However if you have a real audience around, you can select this option to" +
            " allow the insertion of audience votes manually.";

    private Game game;
    private JButton gameFileButton = new JButton(new ImageIcon(getClass().getResource("/imgs/folder.png")));
    private JTextField gameFileField = new JTextField("select game file -->");
    private JCheckBox realAudienceCheckbox = new JCheckBox();
    private JLabel realAudienceHintIcon = new JLabel(new ImageIcon(getClass().getResource("/imgs/info.png")));
    private JButton startButton = new JButton("Start game");


    public GameSettingsFrame() {
        super("Game Settings");
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource("/imgs/icon.png")).getImage());

        JPanel mainPanel = new JPanel(new GridLayout(3, 1, ITEMS_MARGIN, ITEMS_MARGIN));
        mainPanel.setBorder(new EmptyBorder(ITEMS_MARGIN, ITEMS_MARGIN, ITEMS_MARGIN, ITEMS_MARGIN));

        realAudienceHintIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(GameSettingsFrame.this, REAL_AUDIENCE_HINT, "", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        gameFileField.setEnabled(false);
        gameFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectGameFile();
            }
        });
        gameFileButton.setFocusable(false);

        startButton.setEnabled(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        JPanel gamePanel = new JPanel(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
        gamePanel.add(gameFileField, BorderLayout.CENTER);
        gamePanel.add(gameFileButton, BorderLayout.EAST);

        JPanel audiencePanel = new JPanel(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
        JPanel audienceInnerPanel = new JPanel(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
        audienceInnerPanel.add(new JLabel("Real Audience"), BorderLayout.CENTER);
        audienceInnerPanel.add(realAudienceCheckbox, BorderLayout.EAST);
        audiencePanel.add(audienceInnerPanel, BorderLayout.CENTER);
        audiencePanel.add(realAudienceHintIcon, BorderLayout.EAST);

        mainPanel.add(gamePanel);
        mainPanel.add(audiencePanel);
        mainPanel.add(startButton);
        add(mainPanel);
    }

    /**
     * Shows file chooser for selecting game file.
     */
    private void selectGameFile() {
        JFileChooser fileChooser = new JFileChooser(new File("../games"));
        fileChooser.setDialogTitle("Game File Selection");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML only", "xml"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            gameFileField.setText(selectedFile.getName());
            try {
                this.game = Game.load(selectedFile);
                JOptionPane.showMessageDialog(this, "Game file loaded successfully", "",
                        JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/imgs/approved.png")));
                startButton.setEnabled(true);
            } catch (GameValidationException e) {
                startButton.setEnabled(false);
                JOptionPane.showMessageDialog(this, "Game file load failed due to:\n" + e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Initializes controller and starts game with it.
     */
    private void startGame() {
        dispose();
        GameSettings settings = new GameSettings(game, realAudienceCheckbox.isSelected());
        GameController.newController(settings).startGame();
    }
}
