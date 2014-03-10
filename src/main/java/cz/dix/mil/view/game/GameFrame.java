package cz.dix.mil.view.game;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.common.BackgroundPanel;
import cz.dix.mil.view.game.hint.AudienceVotingPanel;
import cz.dix.mil.view.game.hint.AudienceVotingResultPanel;
import cz.dix.mil.view.game.hint.HintsPanel;
import cz.dix.mil.view.game.hint.PhoneFriendPanel;
import cz.dix.mil.view.game.question.AnswersPanel;
import cz.dix.mil.view.game.question.NextQuestionPanel;
import cz.dix.mil.view.game.question.QuestionPanel;
import cz.dix.mil.view.game.question.ShowCorrectAnswerPanel;
import cz.dix.mil.view.game.reward.FinalRewardPanel;
import cz.dix.mil.view.game.reward.RewardsPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Frame with whole game. Contains all game sub-components and is responsible for controlling them.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameFrame extends JFrame implements Refreshable {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 550;

    private final HintsPanel hintsPanel;
    private final AudienceVotingResultPanel audienceVotingResultPanel;
    private final QuestionPanel questionPanel;
    private final AnswersPanel answersPanel;
    private final RewardsPanel rewardsPanel;

    private final JPanel middlePanel;
    private final NextQuestionPanel nextQuestionPanel;
    private final ShowCorrectAnswerPanel showCorrectAnswerPanel;
    private final FinalRewardPanel finalRewardPanel;
    private final AudienceVotingPanel audienceVotingPanel;
    private final PhoneFriendPanel phoneFriendPanel;

    public GameFrame(GameModel model, GameController controller) {
        super(model.getGameName());

        this.hintsPanel = new HintsPanel(model, controller);
        this.audienceVotingResultPanel = new AudienceVotingResultPanel(model);
        this.questionPanel = new QuestionPanel(model);
        this.answersPanel = new AnswersPanel(model, controller);
        this.rewardsPanel = new RewardsPanel(model);

        this.middlePanel = new JPanel(new BorderLayout());
        this.showCorrectAnswerPanel = new ShowCorrectAnswerPanel(controller);
        this.nextQuestionPanel = new NextQuestionPanel(controller);
        this.finalRewardPanel = new FinalRewardPanel(model);
        this.audienceVotingPanel = new AudienceVotingPanel();
        this.phoneFriendPanel = new PhoneFriendPanel();

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
        leftInnerPanel.add(middlePanel, BorderLayout.CENTER);
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
     * Show button for revealing answer.
     */
    public void showRevealAnswerPanel() {
        showCorrectAnswerPanel.reactivateButton();
        setMiddlePanel(showCorrectAnswerPanel);
    }

    /**
     * Disables all actions and shows label with final reward.
     */
    public void showFinalRewardPanel() {
        disableActions();
        finalRewardPanel.refresh();
        setMiddlePanel(finalRewardPanel);
    }

    /**
     * Reveals correct answer in all required sub components.
     */
    public void revealAnswer() {
        setMiddlePanel(null);
        answersPanel.revealAnswer();
    }

    /**
     * Hides question data (typically between questions).
     */
    public void hideQuestion() {
        showQuestion(false);
        nextQuestionPanel.reactivateButton();
        setMiddlePanel(nextQuestionPanel);
    }

    /**
     * Shows label that audience is voting.
     */
    public void showAudienceVotingPanel() {
        disableActions();
        setMiddlePanel(audienceVotingPanel);
        audienceVotingPanel.startVoting();
    }

    /**
     * Shows panel with results of voting.
     */
    public void showAudienceVotingResultPanel() {
        audienceVotingResultPanel.fetchResults();
        setMiddlePanel(audienceVotingResultPanel);
    }

    /**
     * Shows panel with timer that corresponds to remaining time of phone call.
     */
    public void showPhoneFriendPanel() {
        disableActions();
        setMiddlePanel(phoneFriendPanel);
        phoneFriendPanel.startCountdown();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        setMiddlePanel(null);

        hintsPanel.refresh();
        questionPanel.refresh();
        answersPanel.refresh();
        rewardsPanel.refresh();

        showQuestion(true);
    }

    private void setMiddlePanel(JPanel contents) {
        middlePanel.removeAll();
        if (contents != null) {
            middlePanel.add(contents, BorderLayout.CENTER);
        }
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    private void showQuestion(boolean isShown) {
        questionPanel.setVisible(isShown);
        answersPanel.setVisible(isShown);
    }
}
