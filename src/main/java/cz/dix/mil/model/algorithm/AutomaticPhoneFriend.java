package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.PhoneFriendResult;
import cz.dix.mil.model.runtime.QuestionDifficulty;

import java.util.List;

/**
 * Algorithm that is used for automatic phone friend help.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface AutomaticPhoneFriend {

    /**
     * Counts result of automatic phone friend.
     *
     * @param possibleAnswers possible answers between which friend chooses
     * @param difficulty      difficulty of given question
     * @return result of automatic phone friend help
     */
    PhoneFriendResult call(List<Answer> possibleAnswers, QuestionDifficulty difficulty);
}
