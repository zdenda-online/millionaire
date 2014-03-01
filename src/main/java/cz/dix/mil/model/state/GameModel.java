package cz.dix.mil.model.state;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Represents whole model of the game.
 * It means it contains all static data (game questions and answers) but also runtime data like selected answer
 * of player or actual state of game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameModel {

    private final Game game;
    private int actualQuestionIdx = -1;
    private Collection<Hint> availableHints = new ArrayList<>(Arrays.asList(Hint.values()));

    private Answer selectedAnswer = null;
    private Answer removedAnswer1 = null;
    private Answer removedAnswer2 = null;
    private PlayersProgress playersProgress = PlayersProgress.BEFORE_GAME;

    public GameModel(Game game) {
        this.game = game;
    }

    /**
     * Updates model when player answers with given answer.
     *
     * @param answer answer selected by player
     */
    public void answerQuestion(Answer answer) {
        selectedAnswer = answer;
        if (selectedAnswer.isCorrect()) {
            if (!hasNextQuestion()) {
                playersProgress = PlayersProgress.AFTER_GAME; // you won
            }
        } else {
            playersProgress = PlayersProgress.AFTER_GAME; // you return checkpoint or get nothing
        }
    }

    /**
     * Gets actual question of the game.
     *
     * @return actual question
     */
    public Question getActualQuestion() {
        return game.getQuestion(actualQuestionIdx);
    }

    /**
     * Gets a flag whether there is still any question to be responded.
     *
     * @return true if any question is still available, otherwise false
     */
    public boolean hasNextQuestion() {
        return actualQuestionIdx < game.getQuestionsSize();
    }

    /**
     * Moves game to the next question while it clears previously stored data about answer.
     */
    public void toNextQuestion() {
        selectedAnswer = null;
        removedAnswer1 = null;
        removedAnswer2 = null;
        playersProgress = PlayersProgress.IN_GAME;
        if (!hasNextQuestion()) {
            throw new IllegalArgumentException("No more questions in the comp");
        }
        actualQuestionIdx++;
    }

    /**
     * Gets a difficulty of actual question.
     *
     * @return actual question difficulty
     */
    public QuestionDifficulty getActualQuestionDifficulty() {
        int questionIdx = getQuestionIdx(getActualQuestion());
        if (questionIdx < 5) {
            return QuestionDifficulty.EASY;
        } else if (questionIdx < 10) {
            return QuestionDifficulty.MID;
        } else {
            return QuestionDifficulty.HARD;
        }
    }

    /**
     * Gets a flag whether given question is checkpoint.
     *
     * @param question question to be checked
     * @return true if question is checkpoint, otherwise false
     */
    public boolean isCheckpoint(Question question) {
        return (getQuestionIdx(question) + 1) % 5 == 0;
    }

    /**
     * Gets a flag whether given hint is available (not used yet) for player.
     *
     * @param hint hint to be checked
     * @return true if hint is still available, otherwise false
     */
    public boolean isHintAvailable(Hint hint) {
        return availableHints.contains(hint);
    }

    /**
     * Sets specified hint as used and updates available answers {@link #getAvailableAnswers()}
     * if hint is 50-50.
     *
     * @param hint hint to be used
     */
    public void useHint(Hint hint) {
        availableHints.remove(hint);
        if (Hint.FIFTY_FIFTY.equals(hint)) {
            useFiftyFifty();
        }
    }

    /**
     * Gets all available answers of actual question.
     * Typically returns all answers but if 50-50 was used before, returns only remaining questions.
     *
     * @return available answers of actual question
     */
    public Collection<Answer> getAvailableAnswers() {
        Collection<Answer> availableAnswers = new ArrayList<>(getActualQuestion().getAnswers());
        availableAnswers.remove(removedAnswer1);
        availableAnswers.remove(removedAnswer2);
        return availableAnswers;
    }

    /**
     * Gets all questions of the game.
     *
     * @return all questions
     */
    public List<Question> getAllQuestions() {
        return game.getQuestions();
    }

    /**
     * Gets a progress of the player.
     *
     * @return progress of player
     */
    public PlayersProgress getState() {
        return playersProgress;
    }

    /**
     * Gets selected answer by player (if any).
     *
     * @return selected answer by player or null if no answer selected yet
     */
    public Answer getSelectedAnswer() {
        return selectedAnswer;
    }

    /**
     * Gets index of actual question.
     * Note that the first index is 0.
     *
     * @return index of actual question
     */
    public int getActualQuestionIdx() {
        return actualQuestionIdx;
    }

    private void useFiftyFifty() {
        while (true) {
            int idx = (int) (Math.random() * ((double) getActualQuestion().getAnswers().size()));
            Answer answer = getActualQuestion().getAnswers().get(idx);
            if (!answer.isCorrect()) {
                if (removedAnswer1 == null) {
                    removedAnswer1 = answer;
                } else if (!removedAnswer1.equals(answer)) {
                    removedAnswer2 = answer;
                    break;
                }
            }
        }
    }

    private int getQuestionIdx(Question question) {
        int idx = 0;
        for (Question q : game.getQuestions()) {
            if (q.equals(question)) {
                return idx;
            }
            idx++;
        }
        throw new IllegalArgumentException("Question is not covered by this model");
    }
}
