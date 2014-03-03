package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Question;
import cz.dix.mil.model.state.AudienceResult;
import cz.dix.mil.model.state.QuestionDifficulty;

/**
 * Used for automatic audience results
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface AutomaticAudienceAlgorithm {

    /**
     * Counts results of automatic audience voting.
     *
     * @param question question for which to generated
     * @param difficulty difficulty of question
     * @return result of voting
     */
    AudienceResult count(Question question, QuestionDifficulty difficulty);
}
