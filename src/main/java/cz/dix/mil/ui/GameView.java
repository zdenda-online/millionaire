package cz.dix.mil.ui;

import cz.dix.mil.controller.ChainedAction;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.state.GameModel;

/**
 * Represents all components of the view.
 * Primarily passes requests from controller to desired UI components.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameView {

    private final MainFrame mainFrame;
    private final RevealAnswerDialog revealAnswerDialog;
    private final AudienceVotingDialog audienceVotingDialog;
    private final AudienceResultDialog audienceResultDialog;
    private final PhoneFriendDialog phoneFriendDialog;

    public GameView(GameModel model, GameController controller) {
        this.mainFrame = new MainFrame(model, controller);
        this.revealAnswerDialog = new RevealAnswerDialog(mainFrame, controller);
        this.audienceVotingDialog = new AudienceVotingDialog(mainFrame);
        this.audienceResultDialog = new AudienceResultDialog(mainFrame, controller);
        this.phoneFriendDialog = new PhoneFriendDialog(mainFrame);
    }

    /**
     * Shows main frame of game.
     */
    public void showMainFrame() {
        mainFrame.setVisible(true);
    }

    /**
     * Updates main frame of the game.
     */
    public void updateMainFrame() {
        mainFrame.refresh();
    }

    /**
     * Disables all actions in main frame of the game.
     */
    public void disableMainFrame() {
        mainFrame.disableActions();
    }

    /**
     * Reveals correct answer in view and passes processing to specified chained action.
     *
     * @param chainedAction action to be fired after answer is revealed
     */
    public void revealAnswer(ChainedAction chainedAction) {
        mainFrame.revealAnswer(chainedAction);
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
        audienceResultDialog.setVisible(true);
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
}
