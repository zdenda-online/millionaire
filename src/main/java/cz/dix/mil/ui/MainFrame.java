package cz.dix.mil.ui;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.state.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Main frame with whole game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class MainFrame extends JFrame {

    private final HintsPanel hintsPanel;
    private final QuestionsPanel questionsPanel;
    private final AnswersPanel answersPanel;
    private final RewardsPanel rewardsPanel;

    public MainFrame(GameModel model, GameController controller) {
        super();
        this.hintsPanel = new HintsPanel(model, controller);
        this.questionsPanel = new QuestionsPanel(model);
        this.answersPanel = new AnswersPanel(model, controller);
        this.rewardsPanel = new RewardsPanel(model);
        init();
    }

    private void init() {
        setSize(1200, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());

        leftPanel.add(hintsPanel, BorderLayout.NORTH);
        leftPanel.add(questionsPanel, BorderLayout.CENTER);
        leftPanel.add(answersPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.CENTER);
        add(rewardsPanel, BorderLayout.EAST);
        setLocationRelativeTo(null);
    }

    public HintsPanel getHintsPanel() {
        return hintsPanel;
    }

    public QuestionsPanel getQuestionsPanel() {
        return questionsPanel;
    }

    public AnswersPanel getAnswersPanel() {
        return answersPanel;
    }

    public RewardsPanel getRewardsPanel() {
        return rewardsPanel;
    }
}
