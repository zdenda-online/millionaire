package cz.dix.mil.sound;

import cz.dix.mil.controller.AnswerListener;
import cz.dix.mil.controller.GameFlowListener;
import cz.dix.mil.controller.HintsListener;
import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.state.GameModel;

/**
 * Component that is responsible for playing appropriate sounds for given events.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class SoundsPlayer implements GameFlowListener, AnswerListener, HintsListener {

    private final GameModel model;
    private final SoundsFactory soundsFactory = new SoundsFactory();
    private Sound playedSound;

    public SoundsPlayer(GameModel model) {
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGameStart() {
        playSound(soundsFactory.easyQuestion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAnswerSelected(Answer answer) {
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
     * {@inheritDoc}
     */
    @Override
    public void onAnswersReveal() {
        switch (model.getActualQuestionDifficulty()) {
            case EASY:
                if (isAnswerCorrect()) {
                    if (!checkCheckpoint()) {
                        pauseActualSound();
                        soundsFactory.answerEasyCorrect().playBlocked();
                        continueActualSound();
                    }
                } else {
                    stopActualSound();
                    soundsFactory.answerIncorrect().playBlocked();
                }
                break;
            case MID:
                if (isAnswerCorrect()) {
                    if (!checkCheckpoint()) {
                        stopActualSound();
                        soundsFactory.answerWaitCorrect().playBlocked();
                    }
                } else {
                    stopActualSound();
                    soundsFactory.answerIncorrect().playBlocked();
                }
                break;
            case HARD:
                if (isAnswerCorrect()) {
                    if (!checkCheckpoint()) {
                        stopActualSound();
                        soundsFactory.answerWaitCorrect().playBlocked();
                    }
                } else {
                    stopActualSound();
                    soundsFactory.answerIncorrect().playBlocked();
                }
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNewQuestion() {
        switch (model.getActualQuestionDifficulty()) {
            case EASY:
                // do nothing
                break;
            case MID:
                stopActualSound();
                soundsFactory.question().playBlocked();
                playSound(soundsFactory.midQuestion());
                break;
            case HARD:
                stopActualSound();
                soundsFactory.question().playBlocked();
                playSound(soundsFactory.hardQuestion());
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAskAudience() {
        pauseActualSound();
        soundsFactory.askAudience().playBlocked();
        soundsFactory.askAudienceEnd().playBlocked();
        continueActualSound();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFiftyFifty() {
        pauseActualSound();
        soundsFactory.fiftyFifty().playBlocked();
        continueActualSound();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPhoneFriend() {
        pauseActualSound();
        soundsFactory.phoneFriend().playBlocked();
        continueActualSound();
    }

    /**
     * Checks whether the question is checkpoint and plays its sound if yes.
     *
     * @return true if actual question was checkpoint, otherwise false
     */
    private boolean checkCheckpoint() {
        if (model.isCheckpoint(model.getActualQuestion())) {
            stopActualSound();
            soundsFactory.checkpoint().playBlocked();
            return true;
        } else {
            return false;
        }
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
