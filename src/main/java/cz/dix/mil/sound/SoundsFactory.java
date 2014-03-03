package cz.dix.mil.sound;

/**
 * Factory for sounds of the game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface SoundsFactory {

    /**
     * Creates a new sound for initial entering.
     *
     * @return sound for entering
     */
    Sound start();


    /**
     * Creates a new background sound for easy questions.
     *
     * @return sound for easy questions
     */
    Sound easyQuestion();

    /**
     * Creates a new initial sound when harder than easy question.
     *
     * @return initial question sound
     */
    Sound question();

    /**
     * Creates a new background sound for mid questions.
     *
     * @return sound for mid questions
     */
    Sound midQuestion();

    /**
     * Creates a new background sound for hard questions.
     *
     * @return sound for hard questions
     */
    Sound hardQuestion();

    /**
     * Creates a new background sound for the time when answer is selected but still awaits moderator approval.
     * It is used at the beginning and should not be repeated
     *
     * @return sound for waiting on answer (start)
     */
    Sound answerWaitStart();

    /**
     * Creates a new background sound for the time when answer is selected but still awaits moderator approval.
     * It is used after {@link #answerWaitStart()} ends up if needed.
     *
     * @return sound for waiting on answer
     */
    Sound answerWaitContinue();


    /**
     * Creates a new sound when easy question was answered correctly.
     *
     * @return sound for easy question answered correctly
     */
    Sound answerEasyCorrect();

    /**
     * Creates a new sound when question that was waiting moderators approval was answered correctly.
     *
     * @return sound for awaited question answered correctly
     */
    Sound answerWaitCorrect();

    /**
     * Creates a new sound when questions was answered incorrectly.
     *
     * @return sound for question answered incorrectly
     */
    Sound answerIncorrect();

    /**
     * Creates a new sound when player reaches checkpoint.
     *
     * @return sound for checkpoint
     */
    Sound checkpoint();

    /**
     * Crates a new sound when hint for asking audience is on.
     *
     * @return sound for audience hint
     */
    Sound askAudience();

    /**
     * Crates a new sound when audience voting is over
     *
     * @return sound for audience voting is over
     */
    Sound askAudienceEnd();

    /**
     * Crates a new sound when 50-50 hint is on.
     *
     * @return sound for 50-50 hint
     */
    Sound fiftyFifty();

    /**
     * Crates a new sound when phone friend hint is on.
     *
     * @return sound for 50-50 hint
     */
    Sound phoneFriend();
}
