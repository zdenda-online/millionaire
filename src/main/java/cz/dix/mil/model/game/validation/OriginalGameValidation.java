package cz.dix.mil.model.game.validation;

/**
 * Validation that accepts games only in original TV show format.
 * That means 15 questions, 4 answers and only 1 correct.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class OriginalGameValidation extends AbstractGameValidation {

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getQuestionsCount() {
        return 15;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getAnswersCount() {
        return 4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getCorrectAnswersCount() {
        return 1;
    }
}
