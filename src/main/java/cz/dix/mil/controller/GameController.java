package cz.dix.mil.controller;

import cz.dix.mil.model.GameSettings;
import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.game.GameView;

/**
 * Main controller of the game that is responsible for passing events to the {@link GameModel}
 * and notifying appropriate UI components.
 * </p>
 * It also controls {@link SoundsController} that is responsible for playing sounds.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 * @see SoundsController
 */
public class GameController {

    private final GameSettings settings;
    private final SoundsController soundsController;
    private final GameModel model;
    private final GameView view;

    /**
     * Private constructor, use {@link #newController(GameSettings)} instead.
     *
     * @param settings settings of the game
     */
    private GameController(GameSettings settings) {
        this.settings = settings;
        this.model = new GameModel(settings.getGame());
        this.soundsController = new SoundsController(model);
        this.view = new GameView(model, this);
    }

    /**
     * Creates a new controller for the game with given settings.
     *
     * @param settings settings of the game
     */
    public static GameController newController(GameSettings settings) {
        return new GameController(settings);
    }

    /**
     * Starts the whole game by playing introduction sound and showing main frame.
     */
    public void startGame() {
        ChainedAction gameStart = new ChainedAction() {
            @Override
            public void execute() {
                view.disposeIntroFrame();
                soundsController.startGame();
                model.toNextQuestion();
                view.updateMainFrame();
                view.showMainFrame();
            }
        };

        if (settings.skipIntro()) {
            gameStart.execute();
        } else {
            view.showIntroFrame();
            soundsController.playIntro(gameStart);
        }
    }

    /**
     * Player selects an answer to the question.
     *
     * @param answer answer selected by player
     */
    public void selectAnswer(Answer answer) {
        view.showRevealAnswerButton();
        model.answerQuestion(answer);
        soundsController.selectAnswer();
    }

    /**
     * Moderator shows the correct answer of actual question.
     */
    public void showCorrectAnswer() {
        view.revealAnswer();
        soundsController.revealAnswer(new ChainedAction() {
            @Override
            public void execute() {
                view.hideQuestion();
                switch (model.getPlayerProgress()) {
                    case IN_GAME:
                        break;
                    case GAVE_UP:
                    case AFTER_INCORRECT_ANSWER:
                    case WON_GAME:
                    default:
                        view.showFinalReward();
                        break;
                }
            }
        });
    }

    /**
     * Moderator moves to next question.
     */
    public void showNextQuestion() {
        if (model.hasNextQuestion()) {
            model.toNextQuestion();
            soundsController.nextQuestion(new ChainedAction() {
                @Override
                public void execute() {
                    view.updateMainFrame();
                }
            });
        }
    }

    /**
     * Player asks for audience hint.
     */
    public void useAudienceHint() {
        view.showAudienceVoting();
        soundsController.askAudience(new ChainedAction() {
            @Override
            public void execute() {
                if (settings.isRealAudience()) {
                    view.showAudienceResultDialog();
                } else {
                    model.generateAudienceResults();
                    view.showAudienceVotingResult();
                }
            }
        });
    }

    /**
     * Sets data for audience hint results.
     *
     * @param counts counts for each answer
     */
    public void setManualAudienceHintResults(int[] counts) {
        model.setAudienceResults(counts);
        view.updateMainFrame();
        view.showAudienceVotingResult();
    }

    /**
     * Player asks for 50-50 hint.
     */
    public void useFiftyFiftyHint() {
        model.useFiftyFifty();
        soundsController.fiftyFifty(new ChainedAction() {
            @Override
            public void execute() {
                view.updateMainFrame();
            }
        });
    }

    /**
     * Player asks for phone friend hint.
     */
    public void usePhoneFriendHint() {
        model.removePhoneFriendHint();
        view.disableMainFrame();
        if (settings.isRealPhoneFriend()) {
            view.showPhoneFriendCountdown();
        } else {
            view.showPhoneFriendCountdown(model.generatePhoneFriendResult());
        }
        soundsController.phoneFriend(new ChainedAction() {
            @Override
            public void execute() {
                view.updateMainFrame();
            }
        });
    }
}
