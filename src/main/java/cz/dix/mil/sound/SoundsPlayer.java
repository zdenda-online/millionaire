package cz.dix.mil.sound;

import cz.dix.mil.controller.ChainedAction;
import cz.dix.mil.model.state.GameModel;

/**
 * Component that is responsible for playing appropriate sounds for given events.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class SoundsPlayer {

    private final GameModel model;
    private final SoundsFactory soundsFactory = new SoundsFactory();
    private Sound playedSound;

    public SoundsPlayer(GameModel model) {
        this.model = model;
    }

    /**
     * Plays a starting sound (blocked), starts sound for easy questions and passes processing to chained action.
     *
     * @param chainedAction action to be fired after start sound is played
     */
    public void startGame(final ChainedAction chainedAction) {
        soundsFactory.start().playBlocked(new ChainedAction() {
            @Override
            public void toNextAction() {
                playSound(soundsFactory.easyQuestion());
                chainedAction.toNextAction();
            }
        });
    }

    /**
     * In case of harder than easy questions, plays awaiting sound (until moderator reveals answers).
     */
    public void selectAnswer() {
        switch (model.getActualQuestionDifficulty()) {
            case EASY:
                // do nothing
                break;
            case MID:
                stopActualSound();
                playSound(soundsFactory.answerWait());
                break;
            case HARD:
                stopActualSound();
                playSound(soundsFactory.answerWait());
                break;
            default:
                break;
        }
    }

    /**
     * Plays appropriate sounds (correct/incorrect answer or checkpoint)
     * and passes processing to chained action.
     *
     * @param chainedAction action to be fired after sounds is played
     */
    public void revealAnswer(final ChainedAction chainedAction) {
        switch (model.getActualQuestionDifficulty()) {
            case EASY:
                if (isAnswerCorrect()) {
                    if (!checkCheckpoint(chainedAction)) {
                        pauseActualSound();
                        soundsFactory.answerEasyCorrect().playBlocked(chainedAction);
                        continueActualSound();
                    }
                } else {
                    stopActualSound();
                    soundsFactory.answerIncorrect().playBlocked(chainedAction);
                }
                break;
            case MID:
                if (isAnswerCorrect()) {
                    if (!checkCheckpoint(chainedAction)) {
                        stopActualSound();
                        soundsFactory.answerWaitCorrect().playBlocked(chainedAction);
                    }
                } else {
                    stopActualSound();
                    soundsFactory.answerIncorrect().playBlocked(chainedAction);
                }
                break;
            case HARD:
                if (isAnswerCorrect()) {
                    if (!checkCheckpoint(chainedAction)) {
                        stopActualSound();
                        soundsFactory.answerWaitCorrect().playBlocked(chainedAction);
                    }
                } else {
                    stopActualSound();
                    soundsFactory.answerIncorrect().playBlocked(chainedAction);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Plays a sound for next question and passes processing to chained action.
     *
     * @param chainedAction action to be fired after sounds is played
     */
    public void nextQuestion(final ChainedAction chainedAction) {
        switch (model.getActualQuestionDifficulty()) {
            case EASY:
                chainedAction.toNextAction();
                break;
            case MID:
                stopActualSound();
                soundsFactory.question().playBlocked(new ChainedAction() {
                    @Override
                    public void toNextAction() {
                        playSound(soundsFactory.midQuestion());
                        chainedAction.toNextAction();
                    }
                });
                break;
            case HARD:
                stopActualSound();
                soundsFactory.question().playBlocked(new ChainedAction() {
                    @Override
                    public void toNextAction() {
                        playSound(soundsFactory.hardQuestion());
                        chainedAction.toNextAction();
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * Plays a sound for audience hint and passes processing to chained action.
     *
     * @param chainedAction action to be fired after sounds is played
     */
    public void askAudience(final ChainedAction chainedAction) {
        pauseActualSound();
        soundsFactory.askAudience().playBlocked(new ChainedAction() {
            @Override
            public void toNextAction() {
                useHint(chainedAction, soundsFactory.askAudienceEnd());
            }
        });
    }

    /**
     * Plays a sound for 50-50 hint and passes processing to chained action.
     *
     * @param chainedAction action to be fired after sounds is played
     */
    public void fiftyFifty(final ChainedAction chainedAction) {
        useHint(chainedAction, soundsFactory.fiftyFifty());
    }

    /**
     * Plays a sound for phone friend hint and passes processing to chained action.
     *
     * @param chainedAction action to be fired after sounds is played
     */
    public void phoneFriend(final ChainedAction chainedAction) {
        useHint(chainedAction, soundsFactory.phoneFriend());
    }

    /**
     * Checks whether the question is checkpoint and plays its sound if yes.
     *
     * @return true if actual question was checkpoint, otherwise false
     */
    private boolean checkCheckpoint(final ChainedAction chainedAction) {
        if (model.isCheckpoint(model.getActualQuestion())) {
            stopActualSound();
            soundsFactory.checkpoint().playBlocked(chainedAction);
            return true;
        } else {
            return false;
        }
    }

    private void useHint(final ChainedAction chainedAction, Sound hintSound) {
        pauseActualSound();
        hintSound.playBlocked(new ChainedAction() {
            @Override
            public void toNextAction() {
                continueActualSound();
                chainedAction.toNextAction();
            }
        });
    }

    private void pauseActualSound() {
        if (playedSound != null) {
            playedSound.pausePlaying();
        }
    }

    private void stopActualSound() {
        if (playedSound != null) {
            playedSound.stop();
        }
    }

    private void continueActualSound() {
        if (playedSound != null) {
            playedSound.continuePlaying();
        }
    }

    private void playSound(Sound sound) {
        playedSound = sound;
        playedSound.play();
    }

    private boolean isAnswerCorrect() {
        return model.getSelectedAnswer().isCorrect();
    }
}
