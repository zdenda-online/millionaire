package cz.dix.mil.sound;

import java.io.InputStream;

/**
 * Factory for {@link Sound} instances.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class SoundsFactory {

    private InputStream stream(String fileName) {
        return getClass().getResourceAsStream("/sounds/" + fileName);
    }

    public Sound start() {
        return new Sound(stream("start.wav"), false, 9);
    }

    public Sound question() {
        return new Sound(stream("question.wav"), false, 3);
    }

    public Sound easyQuestion() {
        return new Sound(stream("easy-questions.wav"), true, 167);
    }

    public Sound midQuestion() {
        return new Sound(stream("mid-question.wav"), true, 128);
    }

    public Sound hardQuestion() {
        return new Sound(stream("hard-question.wav"), true, 160);
    }

    public Sound answerWait() {
        return new Sound(stream("answer-wait.wav"), true, 19);
    }

    public Sound answerEasyCorrect() {
        return new Sound(stream("answer-easy-correct.wav"), false, 1);
    }

    public Sound answerWaitCorrect() {
        return new Sound(stream("answer-wait-correct.wav"), false, 3);
    }

    public Sound answerIncorrect() {
        return new Sound(stream("answer-incorrect.wav"), false, 3);
    }

    public Sound checkpoint() {
        return new Sound(stream("checkpoint.wav"), false, 5);
    }

    public Sound askAudience() {
        return new Sound(stream("ask-audience.wav"), false, 10);
    }

    public Sound askAudienceEnd() {
        return new Sound(stream("ask-audience-end.wav"), false, 1);
    }

    public Sound fiftyFifty() {
        return new Sound(stream("fifty-fifty.wav"), false, 1);
    }

    public Sound phoneFriend() {
        return new Sound(stream("phone-help.wav"), false, 31);
    }
}
