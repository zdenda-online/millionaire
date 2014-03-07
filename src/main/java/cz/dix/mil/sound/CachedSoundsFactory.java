package cz.dix.mil.sound;

/**
 * Simple decorator that caches already created sounds and returns them instead of creating new ones.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class CachedSoundsFactory implements SoundsFactory {

    private final SoundsFactory decorated;

    // cached sounds
    private Sound start;
    private Sound easyQuestion;
    private Sound question;
    private Sound midQuestion;
    private Sound hardQuestion;
    private Sound answerWaitStart;
    private Sound answerWaitContinue;
    private Sound answerWaitCorrect;
    private Sound answerEasyCorrect;
    private Sound answerIncorrect;
    private Sound checkpoint;
    private Sound audience;
    private Sound audienceEnd;
    private Sound fiftyFifty;
    private Sound phoneFriend;

    public CachedSoundsFactory(SoundsFactory decorated) {
        this.decorated = decorated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound start() {
        if (start == null) {
            start = decorated.start();
        }
        return start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound easyQuestion() {
        if (easyQuestion == null) {
            easyQuestion = decorated.easyQuestion();
        }
        return easyQuestion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound question() {
        if (question == null) {
            question = decorated.question();
        }
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound midQuestion() {
        if (midQuestion == null) {
            midQuestion = decorated.midQuestion();
        }
        return midQuestion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound hardQuestion() {
        if (hardQuestion == null) {
            hardQuestion = decorated.hardQuestion();
        }
        return hardQuestion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerWaitStart() {
        if (answerWaitStart == null) {
            answerWaitStart = decorated.answerWaitStart();
        }
        return answerWaitStart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerWaitContinue() {
        if (answerWaitContinue == null) {
            answerWaitContinue = decorated.answerWaitContinue();
        }
        return answerWaitContinue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerEasyCorrect() {
        if (answerEasyCorrect == null) {
            answerEasyCorrect = decorated.answerEasyCorrect();
        }
        return answerEasyCorrect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerWaitCorrect() {
        if (answerWaitCorrect == null) {
            answerWaitCorrect = decorated.answerWaitCorrect();
        }
        return answerWaitCorrect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerIncorrect() {
        if (answerIncorrect == null) {
            answerIncorrect = decorated.answerIncorrect();
        }
        return answerIncorrect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound checkpoint() {
        if (checkpoint == null) {
            checkpoint = decorated.checkpoint();
        }
        return checkpoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound audience() {
        if (audience == null) {
            audience = decorated.audience();
        }
        return audience;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound audienceEnd() {
        if (audienceEnd == null) {
            audienceEnd = decorated.audienceEnd();
        }
        return audienceEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound fiftyFifty() {
        if (fiftyFifty == null) {
            fiftyFifty = decorated.fiftyFifty();
        }
        return fiftyFifty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound phoneFriend() {
        if (phoneFriend == null) {
            phoneFriend = decorated.phoneFriend();
        }
        return phoneFriend;
    }
}
