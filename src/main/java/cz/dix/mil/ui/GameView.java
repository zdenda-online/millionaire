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
    private final RevealAnswerFrame revealAnswerFrame;
    private final AudienceResultFrame audienceResultFrame;

    public GameView(GameModel model, GameController controller) {
        this.mainFrame = new MainFrame(model, controller);
        this.revealAnswerFrame = new RevealAnswerFrame(controller);
        this.audienceResultFrame = new AudienceResultFrame(controller);
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
     * Reveals frame for answer reveal (moderator's action).
     */
    public void showRevealAnswerFrame() {
        revealAnswerFrame.setVisible(true);
    }

    /**
     * Reveals frame for audience hint result.
     */
    public void showAudienceResultFrame() {
        audienceResultFrame.setVisible(true);
    }

    /**
     * Reveals correct answer in view and passes processing to specified chained action.
     *
     * @param chainedAction action to be fired after answer is revealed
     */
    public void revealAnswer(ChainedAction chainedAction) {
        mainFrame.revealAnswer(chainedAction);
    }
}
