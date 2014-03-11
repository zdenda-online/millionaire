package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.AudienceVotingResult;
import cz.dix.mil.model.runtime.QuestionDifficulty;

import java.util.List;

/**
 * Algorithm that is used for counting automatic audience results.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface AutomaticAudience {

    /**
     * Counts results of audience voting.
     *
     * @param allAnswers      all answers of the question
     * @param possibleAnswers possible answers for voting (50-50 may have been used before the voting)
     * @param difficulty      difficulty of given question
     * @return result of voting which contains percents for every answer (answers not possible have 0)
     */
    AudienceVotingResult vote(List<Answer> allAnswers, List<Answer> possibleAnswers, QuestionDifficulty difficulty);
}
