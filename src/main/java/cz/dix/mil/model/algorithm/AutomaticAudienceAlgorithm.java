package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.QuestionDifficulty;

import java.util.List;

/**
 * Used for automatic audience results
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface AutomaticAudienceAlgorithm {

    /**
     * Counts results of automatic audience voting.
     *
     * @param possibleAnswers possible answers for voting
     * @param difficulty      difficulty of question
     * @return result of voting (counts for each vote corresponding to given answers)
     */
    int[] count(List<Answer> possibleAnswers, QuestionDifficulty difficulty);
}
