package cz.dix.mil.view.game;

import cz.dix.mil.controller.ChainedAction;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.game.hint.AudienceResultPanel;
import cz.dix.mil.view.game.hint.HintsPanel;
import cz.dix.mil.view.game.question.AnswersPanel;
import cz.dix.mil.view.game.question.QuestionsPanel;
import cz.dix.mil.view.game.reward.RewardsPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Main frame with whole game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameMainFrame extends JFrame implements Refreshable {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 550;

    private final HintsPanel hintsPanel;
    private final AudienceResultPanel audienceResultPanel;
    private final QuestionsPanel questionsPanel;
    private final AnswersPanel answersPanel;
    private final RewardsPanel rewardsPanel;

    public GameMainFrame(GameModel model, GameController controller) {
        super("Game: " + model.getGameName());
        this.hintsPanel = new HintsPanel(model, controller);
        this.audienceResultPanel = new AudienceResultPanel(model);
        this.questionsPanel = new QuestionsPanel(model);
        this.answersPanel = new AnswersPanel(model, controller);
        this.rewardsPanel = new RewardsPanel(model);
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imgs/icon.png")).getImage());
        setLayout(new BorderLayout());

        JPanel background = new BackgroundPanel(new ImageIcon(getClass().getResource("/imgs/background.png")).getImage());

        JPanel leftPanel = new JPanel(new BorderLayout());

        JPanel leftInnerPanel = new JPanel(new BorderLayout());
        leftInnerPanel.setOpaque(false);
        leftInnerPanel.add(audienceResultPanel, BorderLayout.CENTER);
        leftInnerPanel.add(questionsPanel, BorderLayout.SOUTH);

        leftPanel.add(hintsPanel, BorderLayout.NORTH);
        leftPanel.add(leftInnerPanel, BorderLayout.CENTER);
        leftPanel.add(answersPanel, BorderLayout.SOUTH);

        background.add(leftPanel, BorderLayout.CENTER);
        background.add(rewardsPanel, BorderLayout.EAST);
        add(background);

        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Disables all actions in main frame (removes all listeners).
     */
    public void disableActions() {
        answersPanel.disableAnswers();
        hintsPanel.disableHints();
    }

    /**
     * Reveals an answer in all required sub components and passes processing to specified chained action.
     *
     * @param chainedAction action to be fired after answer is revealed
     */
    public void revealAnswer(ChainedAction chainedAction) {
        answersPanel.revealAnswer(chainedAction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        hintsPanel.refresh();
        audienceResultPanel.refresh();
        questionsPanel.refresh();
        answersPanel.refresh();
        rewardsPanel.refresh();
    }
}
