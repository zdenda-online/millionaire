package cz.dix.mil.view.game;

import cz.dix.mil.controller.ChainedAction;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.game.hint.ManualAudienceResultDialog;
import cz.dix.mil.view.game.hint.AudienceVotingDialog;
import cz.dix.mil.view.game.hint.PhoneFriendDialog;
import cz.dix.mil.view.game.question.RevealAnswerDialog;
import cz.dix.mil.view.game.reward.FinalRewardDialog;

/**
 * Represents all components of the game view.
 * Primarily passes requests from controller to desired UI components.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameView {

    private final GameMainFrame gameMainFrame;
    private final RevealAnswerDialog revealAnswerDialog;
    private final AudienceVotingDialog audienceVotingDialog;
    private final ManualAudienceResultDialog manualAudienceResultDialog;
    private final PhoneFriendDialog phoneFriendDialog;
    private final FinalRewardDialog finalRewardDialog;

    public GameView(GameModel model, GameController controller) {
        this.gameMainFrame = new GameMainFrame(model, controller);
        this.revealAnswerDialog = new RevealAnswerDialog(gameMainFrame, controller);
        this.audienceVotingDialog = new AudienceVotingDialog(gameMainFrame);
        this.manualAudienceResultDialog = new ManualAudienceResultDialog(gameMainFrame, model, controller);
        this.phoneFriendDialog = new PhoneFriendDialog(gameMainFrame);
        this.finalRewardDialog = new FinalRewardDialog(gameMainFrame, model);
    }

    /**
     * Shows main frame of game.
     */
    public void showMainFrame() {
        gameMainFrame.setVisible(true);
    }

    /**
     * Updates main frame of the game.
     */
    public void updateMainFrame() {
        gameMainFrame.refresh();
    }

    /**
     * Disables all actions in main frame of the game.
     */
    public void disableMainFrame() {
        gameMainFrame.disableActions();
    }

    /**
     * Reveals correct answer in view and passes processing to specified chained action.
     *
     * @param chainedAction action to be fired after answer is revealed
     */
    public void revealAnswer(ChainedAction chainedAction) {
        gameMainFrame.revealAnswer(chainedAction);
    }

    /**
     * Reveals dialog for answer reveal (moderator's action).
     */
    public void showRevealAnswerDialog() {
        revealAnswerDialog.setVisible(true);
    }

    /**
     * Reveals dialog for audience hint result.
     */
    public void showAudienceResultDialog() {
        manualAudienceResultDialog.setVisible(true);
    }

    /**
     * Reveals dialog when audience is voting.
     */
    public void showAudienceVotingDialog() {
        audienceVotingDialog.setVisible(true);
    }

    /**
     * Destroys dialog when audience voting is finished.
     */
    public void hideAudienceVotingDialog() {
        audienceVotingDialog.dispose();
    }

    /**
     * Reveals dialog with countdown of phone friend.
     */
    public void showPhoneFriendDialog() {
        phoneFriendDialog.startCountdown();
    }

    /**
     * Reveals dialog with final reward (game is over).
     */
    public void showFinalRewardDialog() {
        finalRewardDialog.setVisible(true);
    }
}
