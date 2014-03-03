package cz.dix.mil.sound.jmf;

import cz.dix.mil.sound.Sound;
import cz.dix.mil.sound.SoundsFactory;

import java.io.InputStream;

/**
 * Factory for {@link JmfSound} instances.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class JmsSoundsFactory implements SoundsFactory {

    private InputStream stream(String fileName) {
        return getClass().getResourceAsStream("/sounds/" + fileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound start() {
        return new JmfSound(stream("start.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound question() {
        return new JmfSound(stream("question.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound easyQuestion() {
        return new JmfSound(stream("easy-questions.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound midQuestion() {
        return new JmfSound(stream("mid-question.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound hardQuestion() {
        return new JmfSound(stream("hard-question.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerWaitStart() {
        return new JmfSound(stream("answer-wait.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerWaitContinue() {
        return new JmfSound(stream("answer-wait-continue.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerEasyCorrect() {
        return new JmfSound(stream("answer-easy-correct.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerWaitCorrect() {
        return new JmfSound(stream("answer-wait-correct.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound answerIncorrect() {
        return new JmfSound(stream("answer-incorrect.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound checkpoint() {
        return new JmfSound(stream("checkpoint.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound askAudience() {
        return new JmfSound(stream("ask-audience.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound askAudienceEnd() {
        return new JmfSound(stream("ask-audience-end.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound fiftyFifty() {
        return new JmfSound(stream("fifty-fifty.wav"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sound phoneFriend() {
        return new JmfSound(stream("phone-help.wav"));
    }
}
