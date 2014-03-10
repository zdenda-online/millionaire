package cz.dix.mil.view.creator;

import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.Question;
import cz.dix.mil.model.game.validation.GameValidation;
import cz.dix.mil.model.game.validation.OriginalGameValidation;
import cz.dix.mil.view.common.AutoSelectTextField;
import cz.dix.mil.view.common.GameFileChooser;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Frame for creating game XML file.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class CreatorGameFrame extends JFrame {

    private static final int WIDTH = 650;
    private static final int HEIGHT = 290;
    private static final int FRAME_MARGIN = 10;
    private static final int ITEMS_MARGIN = 10;
    private static final GameValidation GAME_VALIDATION = new OriginalGameValidation(); // allow change in future?
    private final Skin skin = SkinManager.getSkin();

    private final JTextField gameNameField = new AutoSelectTextField();
    private final JButton importGameButton = new JButton("Import Game");
    private final JButton exportGameButton = new JButton("Export Game");
    private final CreatorQuestionPanel questionPanel;
    private final JButton[] questionButtons;
    private final Map<Integer, Question> questionsMap = new HashMap<>();

    private int selectedQuestionIdx = 0;

    public CreatorGameFrame(Game initialGame) {
        super("Game Creator");
        int answersCount = initialGame.getQuestion(0).getAnswers().size();
        this.questionPanel = new CreatorQuestionPanel(answersCount, ITEMS_MARGIN);
        this.questionButtons = new JButton[initialGame.getQuestionsCount()];
        init();
        setGame(initialGame);
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imgs/icon.png")).getImage());
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
        mainPanel.setBorder(new EmptyBorder(FRAME_MARGIN, FRAME_MARGIN, FRAME_MARGIN, FRAME_MARGIN));
        mainPanel.add(initGameNamePanel(), BorderLayout.NORTH);
        mainPanel.add(initQuestionsPanel(), BorderLayout.CENTER);
        mainPanel.add(initImportExportButtons(), BorderLayout.SOUTH);
        add(mainPanel);
    }

    /**
     * Sets actual game according to which form gets updated.
     *
     * @param game game to be set
     */
    public void setGame(Game game) {
        initMap(game.getQuestions());
        gameNameField.setText(game.getName());
        questionPanel.setQuestion(questionsMap.get(selectedQuestionIdx));

    }

    /**
     * Initializes panel with game name.
     *
     * @return panel with game name
     */
    private JPanel initGameNamePanel() {
        JPanel gameNamePanel = new JPanel(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
        gameNamePanel.add(new JLabel("Game Name:"), BorderLayout.WEST);
        gameNamePanel.add(gameNameField, BorderLayout.CENTER);
        return gameNamePanel;
    }

    /**
     * Initializes panel for questions (buttons and one question panel).
     *
     * @return panel with question
     */
    private JPanel initQuestionsPanel() {
        for (int i = 0; i < questionButtons.length; i++) {
            final int questionIdx = i;
            JButton questionButton = new JButton(String.valueOf(i + 1));
            if (i == 0) {
                questionButton.setForeground(skin.rewardActualQuestionText());
            } else {
                questionButton.setForeground(getQuestionColor(i));
            }
            questionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    changeQuestion(questionIdx);
                }
            });
            questionButtons[i] = questionButton;
        }

        JPanel questionButtonsPanel = new JPanel(new GridLayout(1, questionsMap.size(), ITEMS_MARGIN, ITEMS_MARGIN));
        for (JButton questionButton : questionButtons) {
            questionButtonsPanel.add(questionButton);
        }
        JPanel questionsPanel = new JPanel(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
        questionsPanel.add(questionButtonsPanel, BorderLayout.NORTH);
        questionsPanel.add(questionPanel, BorderLayout.CENTER);
        return questionsPanel;
    }

    /**
     * Initializes panel with import/export buttons.
     *
     * @return panel with buttons
     */
    private JPanel initImportExportButtons() {
        importGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importGame();
            }
        });
        exportGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportGame();
            }
        });
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, ITEMS_MARGIN, ITEMS_MARGIN));
        buttonsPanel.add(importGameButton);
        buttonsPanel.add(exportGameButton);
        return buttonsPanel;
    }

    /**
     * Loads new game from the given file.
     */
    private void importGame() {
        Game game = new GameFileChooser(GAME_VALIDATION).importGame();
        if (game != null) {
            setGame(game);
        }
    }

    /**
     * Stores game to the file.
     */
    private void exportGame() {
        questionsMap.put(selectedQuestionIdx, questionPanel.getQuestion()); // save currently selected question

        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < questionsMap.size(); i++) {
            questions.add(questionsMap.get(i));
        }
        Game game = new Game(gameNameField.getText(), questions);

        new GameFileChooser(GAME_VALIDATION).exportGame(game);
    }

    private void initMap(List<Question> questions) {
        int idx = 0;
        for (Question q : questions) {
            questionsMap.put(idx++, q);
        }
    }

    /**
     * Changes selected question (by button) and updates form according to it.
     *
     * @param questionIdx new selected question index
     */
    private void changeQuestion(int questionIdx) {
        if (selectedQuestionIdx == questionIdx) {
            return; // no need to save anything
        }
        questionsMap.put(selectedQuestionIdx, questionPanel.getQuestion()); // save question
        questionButtons[selectedQuestionIdx].setForeground(getQuestionColor(selectedQuestionIdx));

        selectedQuestionIdx = questionIdx;
        questionPanel.setQuestion(questionsMap.get(questionIdx));
        questionButtons[selectedQuestionIdx].setForeground(skin.rewardActualQuestionText());
    }

    private Color getQuestionColor(int idx) {
        if ((idx + 1) % 5 == 0) {
            return skin.rewardCheckpointText();
        } else {
            return skin.rewardDefaultText();
        }
    }

}
