package cz.dix.mil.controller;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.state.GameModel;
import cz.dix.mil.model.state.Hint;
import cz.dix.mil.model.state.PlayersProgress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Main controller of the game that is responsible for passing events to the {@link GameModel}
 * and notifying appropriate listeners (typically UI components).
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameController {

    private final GameModel model;
    private final Collection<GameFlowListener> gameFlowListeners = new ArrayList<>();
    private final Collection<AnswerListener> answerListeners = new ArrayList<>();
    private final Collection<HintsListener> hintsListeners = new ArrayList<>();

    public GameController(GameModel model) {
        this.model = model;
    }

    /**
     * Registers new listeners for game flow.
     * Note that listeners' callbacks will be invoked in the same order as registered.
     *
     * @param listeners listeners to be registered
     * @return this instance
     */
    public GameController registerGameListeners(GameFlowListener... listeners) {
        Collections.addAll(gameFlowListeners, listeners);
        return this;
    }

    /**
     * Registers new listeners for hints.
     * Note that listeners' callbacks will be invoked in the same order as registered.
     *
     * @param listeners listeners to be registered
     * @return this instance
     */
    public GameController registerHintsListeners(HintsListener... listeners) {
        Collections.addAll(hintsListeners, listeners);
        return this;
    }

    /**
     * Registers new listeners for answers.
     * Note that listeners' callbacks will be invoked in the same order as registered.
     *
     * @param listeners listeners to be registered
     * @return this instance
     */
    public GameController registerAnswerListeners(AnswerListener... listeners) {
        Collections.addAll(answerListeners, listeners);
        return this;
    }

    /**
     * Moderator starts the new game.
     */
    public void startGame() {
        model.toNextQuestion();
        for (GameFlowListener listener : gameFlowListeners) {
            listener.onGameStart();
        }
    }

    /**
     * Player answers the question.
     *
     * @param answer answer selected by player
     */
    public void answerQuestion(Answer answer) {
        model.answerQuestion(answer);
        for (AnswerListener listener : answerListeners) {
            listener.onAnswerSelected(answer);
        }
    }

    /**
     * Moderator shows correct answer.
     */
    public void showCorrectAnswer() {
        for (final AnswerListener listener : answerListeners) {
            listener.onAnswersReveal();
        }
        if (PlayersProgress.IN_GAME.equals(model.getState())) {
            if (model.hasNextQuestion()) {
                model.toNextQuestion();
                for (GameFlowListener listener : gameFlowListeners) {
                    listener.onNewQuestion();
                }
            }
        }
    }

    /**
     * Player asks for audience hint.
     */
    public void useAudienceHint() {
        model.useHint(Hint.AUDIENCE);
        for (HintsListener listener : hintsListeners) {
            listener.onAskAudience();
        }
    }

    /**
     * Player asks for 50-50 hint.
     */
    public void useFiftyFiftyHint() {
        model.useHint(Hint.FIFTY_FIFTY);
        for (HintsListener listener : hintsListeners) {
            listener.onFiftyFifty();
        }
    }

    /**
     * Player asks for phone friend hint.
     */
    public void usePhoneFriendHint() {
        model.useHint(Hint.PHONE_FRIEND);
        for (HintsListener listener : hintsListeners) {
            listener.onPhoneFriend();
        }
    }
}
