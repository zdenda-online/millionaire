package cz.dix.mil.view.game;

import cz.dix.mil.controller.ChainedAction;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.common.BackgroundPanel;
import cz.dix.mil.view.game.hint.AudienceResultPanel;
import cz.dix.mil.view.game.hint.HintsPanel;
import cz.dix.mil.view.game.question.AnswersPanel;
import cz.dix.mil.view.game.question.QuestionPanel;
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
    private final QuestionPanel questionPanel;
    private final AnswersPanel answersPanel;
    private final RewardsPanel rewardsPanel;

    public GameMainFrame(GameModel model, GameController controller) {
        super(model.getGameName());
        this.hintsPanel = new HintsPanel(model, controller);
        this.audienceResultPanel = new AudienceResultPanel(model);
        this.questionPanel = new QuestionPanel(model);
        this.answersPanel = new AnswersPanel(model, controller);
        this.rewardsPanel = new RewardsPanel(model);
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imgs/icon.png")).getImage());
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel leftInnerPanel = new JPanel(new BorderLayout());
        leftInnerPanel.add(audienceResultPanel, BorderLayout.CENTER);
        leftInnerPanel.add(questionPanel, BorderLayout.SOUTH);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(hintsPanel, BorderLayout.NORTH);
        leftPanel.add(leftInnerPanel, BorderLayout.CENTER);
        leftPanel.add(answersPanel, BorderLayout.SOUTH);

        JPanel backgroundPanel = new BackgroundPanel(new ImageIcon(getClass().getResource("/imgs/background.png")).getImage());
        backgroundPanel.add(leftPanel, BorderLayout.CENTER);
        backgroundPanel.add(rewardsPanel, BorderLayout.EAST);
        add(backgroundPanel);
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
        questionPanel.refresh();
        answersPanel.refresh();
        rewardsPanel.refresh();
    }
}
