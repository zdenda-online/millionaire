package cz.dix.mil.view.game.hint;

import cz.dix.mil.model.algorithm.RandomAlgorithm;
import cz.dix.mil.model.algorithm.ShuffleRandomAlgorithm;
import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.game.Question;
import cz.dix.mil.model.runtime.PhoneFriendResult;
import cz.dix.mil.model.runtime.QuestionDifficulty;

import java.util.List;

/**
 * Factory that creates messages for given automatic phone friend hint.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class PhoneFriendMessagesFactory {

    private static final RandomAlgorithm randomAlgorithm = new ShuffleRandomAlgorithm();

    /**
     * Be sure to have always 1 newline in every message (the countdown won't move in {@link PhoneFriendPanel}).
     * If line should be empty, use &nbsp; entity to display empty row
     */

    // first 20 seconds of call
    private static final String[] EASY_SCENARIO_BEGINNING_1 = {
            "Hi,%s\nPossible Answers are %s.", // 10s
            "That is not that hard.\nPossibilities %s.", // 5s
            "OK, I should know this...\n&nbsp;", // 5s
    };
    private static final String[] EASY_SCENARIO_BEGINNING_2 = {
            "Hello, %s\nThe answers are %s.", // 10s
            "It seems easy.\nSo you said %s.", // 5s
            "I think I've heard about it...\n&nbsp;", // 5s
    };
    private static final String[] MID_SCENARIO_BEGINNING_1 = {
            "Hi, %s\nPossible Answers are %s.", // 10s
            "It really is tricky.\n%s.", // 5s
            "Fine, give me a little while...\n&nbsp; ", // 5s
    };
    private static final String[] MID_SCENARIO_BEGINNING_2 = {
            "Hello, %s\nThe answers are %s.", // 10s
            "OK...\nSo %s.", // 5s
            "Alright, ummmm...\n&nbsp;", // 5s
    };
    private static final String[] HARD_SCENARIO_BEGINNING_1 = {
            "Hi, %s\nPossible Answers are %s.", // 10s
            "Ou, that is really hard.\n%s.", // 5s
            "I'm thinking...\n&nbsp;", // 5s
    };
    private static final String[] HARD_SCENARIO_BEGINNING_2 = {
            "Hello, %s\nAnswers are %s.", // 10s
            "It really is hard...\n%s...", // 5s
            "Let me think a while...\n&nbsp;", // 5s
    };

    // last 10 seconds of call
    private static final String[] ENDINGS_NO_ANSWER = {
            "I'm sorry I have no idea, I really don't know.\nI can't choose one answer...",
            "I'm afraid I won't help you with this.\nNo, not really, sorry...",
            "I can't remember it!\nI think I'm not able to be give you a correct answer...",
            "I wish to help you, but I really don't know.\nForgive me, too hard for me..."
    };
    private static final String[] ENDINGS_VERY_SURE_ANSWER = {
            "I'm sure, it is %s.\nYes, definitely %s...",
            "Yes, that must be %s.\n%s is the correct answer...",
            "It is %s.\nI feel confident, %s is correct...",
    };
    private static final String[] ENDINGS_ALMOST_SURE_ANSWER = {
            "I think I know this.\nAlthough I'm not 100% sure, I think it is %s...",
            "I'm not absolutely sure, but I think it is %s\nI'd go for this one...",
            "No 100% guarantee but I think that %s is correct.\nI'm almost certain...",
    };
    private static final String[] ENDINGS_NOT_SURE_ANSWER = {
            "I'm really not sure.\nI would go with %s but I'm really not sure...",
            "I have a feeling that it can be %s.\nBut that is only a feeling, I'm not sure...",
            "I have %s in my head. But I'm unsure of it.\nReally unsure...",
    };
    private static final String[] ENDINGS_JUST_GUESS_ANSWER = {
            "That is really tricky...\n%s seems best to me but it is only a guess...",
            "Now, I'm just guessing...\nI like %s but frankly, it is really a guess...",
            "Only guessing this one...\n%s looks nice, but as I said just a guess...",
    };

    /**
     * Gets messages for automatic phone friend.
     * Returned array has 4 elements and they should be used this way:
     * <ul>
     * <li>10s for first message</li>
     * <li>5s for second message</li>
     * <li>5s for third message</li>
     * <li>10s for fourth message</li>
     * </ul>
     *
     * @param result          result of automatic phone friend
     * @param question        question for phone friend
     * @param possibleAnswers possible answers to question
     * @param difficulty      difficulty of question
     * @return messages in described format
     */
    public static String[] getMessages(PhoneFriendResult result, Question question, List<Answer> possibleAnswers,
                                       QuestionDifficulty difficulty) {
        String[] out = new String[4];
        String[] beginning = pickBeginning(difficulty); // 3 Strings
        out[0] = String.format(beginning[0], question.getText(), generateAnswersText(possibleAnswers));
        out[1] = String.format(beginning[1], generateAnswersText(possibleAnswers));
        out[2] = beginning[2];
        out[3] = pickEnding(result);
        return out;
    }

    private static String[] pickBeginning(QuestionDifficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return Math.random() > 0.5 ? EASY_SCENARIO_BEGINNING_1 : EASY_SCENARIO_BEGINNING_2;
            case MID:
                return Math.random() > 0.5 ? MID_SCENARIO_BEGINNING_1 : MID_SCENARIO_BEGINNING_2;
            case HARD:
                return Math.random() > 0.5 ? HARD_SCENARIO_BEGINNING_1 : HARD_SCENARIO_BEGINNING_2;
            default:
                throw new IllegalArgumentException("Unknown difficulty!");
        }
    }

    private static String pickEnding(PhoneFriendResult result) {
        if (result.getSelectedAnswer() == null) {
            return randomAlgorithm.random(ENDINGS_NO_ANSWER);
        }
        String answer = result.getSelectedAnswer().getText();
        switch (result.getAssurance()) {
            case VERY_SURE:
                return String.format(randomAlgorithm.random(ENDINGS_VERY_SURE_ANSWER), answer, answer);
            case ALMOST_SURE:
                return String.format(randomAlgorithm.random(ENDINGS_ALMOST_SURE_ANSWER), answer);
            case NOT_SURE:
                return String.format(randomAlgorithm.random(ENDINGS_NOT_SURE_ANSWER), answer);
            case JUST_GUESS:
                return String.format(randomAlgorithm.random(ENDINGS_JUST_GUESS_ANSWER), answer);
            default:
                throw new IllegalArgumentException("Unknown assurance!");
        }
    }

    private static String generateAnswersText(List<Answer> answers) {
        int answersCount = answers.size();
        if (answersCount < 2) {
            throw new IllegalArgumentException("Less then two possible answers?!");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answersCount - 2; i++) {
            sb.append(answers.get(i).getText()).append(", ");
        }

        String prelastAnswer = answers.get(answersCount - 2).getText();
        String lastAnswer = answers.get(answersCount - 1).getText();
        sb.append(prelastAnswer).append(" or ").append(lastAnswer);
        return sb.toString();
    }
}
