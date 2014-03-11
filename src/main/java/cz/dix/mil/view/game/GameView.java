package cz.dix.mil.view.game;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.model.runtime.PhoneFriendResult;
import cz.dix.mil.view.game.hint.ManualAudienceResultDialog;

/**
 * Represents all components of the game view.
 * Primarily passes requests from controller to desired UI components.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameView {

    private final GameIntroFrame gameIntroFrame;
    private final GameFrame gameFrame;
    private final ManualAudienceResultDialog manualAudienceResultDialog;

    public GameView(GameModel model, GameController controller) {
        this.gameIntroFrame = new GameIntroFrame(model);
        this.gameFrame = new GameFrame(model, controller);
        this.manualAudienceResultDialog = new ManualAudienceResultDialog(gameFrame, model, controller);
    }

    /**
     * Shows introduction frame when starting sound plays.
     */
    public void showIntroFrame() {
        gameIntroFrame.setVisible(true);
    }

    /**
     * Disposes introduction frame when starting sound is ended.
     */
    public void disposeIntroFrame() {
        gameIntroFrame.dispose();
    }

    /**
     * Shows main frame of game.
     */
    public void showMainFrame() {
        gameFrame.setVisible(true);
    }

    /**
     * Hides question (hides all components except reward).
     */
    public void hideQuestion() {
        gameFrame.hideQuestion();
    }

    /**
     * Updates main frame of the game.
     */
    public void updateMainFrame() {
        gameFrame.refresh();
    }

    /**
     * Disables all actions in main frame of the game.
     */
    public void disableMainFrame() {
        gameFrame.disableActions();
    }

    /**
     * Reveals correct answer.
     */
    public void revealAnswer() {
        gameFrame.revealAnswer();
    }

    /**
     * Disables all actions and shows button for revealing an answer.
     */
    public void showRevealAnswerButton() {
        disableMainFrame();
        gameFrame.showRevealAnswerPanel();
    }

    /**
     * Reveals dialog for audience hint result.
     */
    public void showAudienceResultDialog() {
        manualAudienceResultDialog.setVisible(true);
    }

    /**
     * Disables all actions and shows label that audience is voting.
     */
    public void showAudienceVoting() {
        gameFrame.showAudienceVotingPanel();
    }

    /**
     * Shows audience voting result.
     */
    public void showAudienceVotingResult() {
        gameFrame.refresh();
        gameFrame.showAudienceVotingResultPanel();
    }

    /**
     * Reveals dialog with countdown of phone friend.
     */
    public void showPhoneFriendCountdown() {
        gameFrame.showPhoneFriendPanel(null);
    }

    /**
     * Reveals dialog with countdown of phone friend and shows conversation that leads to given phone friend result.
     */
    public void showPhoneFriendCountdown(PhoneFriendResult result) {
        gameFrame.showPhoneFriendPanel(result);
    }

    /**
     * Shows with final reward (game is over).
     */
    public void showFinalReward() {
        gameFrame.showFinalRewardPanel();
    }
}
