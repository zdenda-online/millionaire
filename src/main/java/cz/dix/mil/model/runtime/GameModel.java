package cz.dix.mil.model.runtime;

import cz.dix.mil.model.algorithm.AutomaticAudienceAlgorithm;
import cz.dix.mil.model.algorithm.SimpleAutomaticAudienceAlgorithm;
import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.Question;

import java.util.*;

/**
 * Represents whole model of the game.
 * It means it contains all static data (game questions and answers) but also runtime data like selected answer
 * of player or actual state of game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameModel {

    private final Game game;
    private final AutomaticAudienceAlgorithm automaticAudienceAlgorithm = new SimpleAutomaticAudienceAlgorithm();
    private int actualQuestionIdx = -1;
    private Collection<Hint> availableHints = new ArrayList<>(Arrays.asList(Hint.values()));

    private Answer selectedAnswer = null;
    private Set<Answer> removedAnswers = new HashSet<>();
    private PlayersProgress playersProgress = PlayersProgress.BEFORE_GAME;
    private AudienceResult audienceResult;

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
                playersProgress = PlayersProgress.WON_GAME;
            }
        } else {
            playersProgress = PlayersProgress.AFTER_INCORRECT_ANSWER;
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
        return (actualQuestionIdx + 1) < game.getQuestionsCount();
    }

    /**
     * Moves game to the next question while it clears previously stored data about answer.
     */
    public void toNextQuestion() {
        clearQuestionData();
        if (!hasNextQuestion()) {
            throw new IllegalArgumentException("No more questions in the game");
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
        return ((getQuestionIdx(question) + 1) % 5 == 0) && (getQuestionIdx(question) != 0);
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
     * Uses 50-50 hint and updates available answers, see {@link #isAnswerAvailable(Answer)}}.
     */
    public void useFiftyFifty() {
        availableHints.remove(Hint.FIFTY_FIFTY);

        List<Answer> allAnswers = getActualQuestion().getAnswers();

        while (removedAnswers.size() < (allAnswers.size() / 2)) {
            int randomIdx = (int) (Math.random() * ((double) allAnswers.size()));
            Answer randomAnswer = getActualQuestion().getAnswers().get(randomIdx);
            if (!randomAnswer.isCorrect()) {
                if (!removedAnswers.contains(randomAnswer)) {
                    removedAnswers.add(randomAnswer);
                }
            }
        }
    }

    /**
     * Uses phone friend hint.
     */
    public void usePhoneFriend() {
        availableHints.remove(Hint.PHONE_FRIEND);
    }

    /**
     * Uses audience hint and updates values according to given values.
     * Use {@link #getAudienceResult()} to retrieve it.
     *
     * @param peopleForA people that voted for A answer
     * @param peopleForB people that voted for B answer
     * @param peopleForC people that voted for C answer
     * @param peopleForD people that voted for D answer
     */
    public void setAudienceResults(int peopleForA, int peopleForB, int peopleForC, int peopleForD) {
        availableHints.remove(Hint.AUDIENCE);
        audienceResult = new AudienceResult(peopleForA, peopleForB, peopleForC, peopleForD);
    }

    /**
     * Uses audience hint and generates values according to automatic algorithm results.
     * Use {@link #getAudienceResult()} to retrieve it.
     */
    public void generateAudienceResults() {
        availableHints.remove(Hint.AUDIENCE);
        int[] res = automaticAudienceAlgorithm.count(getPossibleAnswers(), getActualQuestionDifficulty());
        audienceResult = createAudienceResult(res);
    }

    /**
     * Gets player's final reward according if he/she gave up, answered incorrectly or won game.
     *
     * @return final reward of player
     */
    public String getFinalReward() {
        switch (playersProgress) {
            case GAVE_UP:
            case WON_GAME:
                return getActualQuestion().getReward();
            case AFTER_INCORRECT_ANSWER:
                Question checkpointQuestion = null;
                int idx = actualQuestionIdx - 1;
                while (idx >= 0 && checkpointQuestion == null) {
                    Question q = game.getQuestion(idx);
                    if (isCheckpoint(q)) {
                        checkpointQuestion = q;
                    }
                    idx--;
                }

                if (checkpointQuestion != null) {
                    return checkpointQuestion.getReward();
                } else {
                    return "Nothing";
                }
            default:
                System.out.println("Game is in incorrect state, please contact authors.");
                System.exit(27);
                return null; // never happens
        }
    }

    /**
     * Gets a flag whether audience results are available (hint was used and data entered).
     *
     * @return true if audience result is available, otherwise false
     */
    public boolean hasAudienceResult() {
        return audienceResult != null;
    }

    /**
     * Checks whether given answer is available (was not removed by 50-50 hint).
     *
     * @param answer answer to be checked
     * @return true if answer is available, otherwise false
     */
    public boolean isAnswerAvailable(Answer answer) {
        return !removedAnswers.contains(answer);
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
    public PlayersProgress getPlayerProgress() {
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

    /**
     * Gets result of audience hint.
     *
     * @return results of audience hint
     */
    public AudienceResult getAudienceResult() {
        return audienceResult;
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

    private void clearQuestionData() {
        selectedAnswer = null;
        removedAnswers.clear();
        playersProgress = PlayersProgress.IN_GAME;
        audienceResult = null;
    }

    private List<Answer> getPossibleAnswers() {
        List<Answer> out = new ArrayList<>();
        for (Answer answer : getActualQuestion().getAnswers()) {
            if (isAnswerAvailable(answer)) {
                out.add(answer);
            }
        }
        return out;
    }

    private AudienceResult createAudienceResult(int[] availAnswersCount) {
        List<Answer> allAnswers = getActualQuestion().getAnswers();
        int[] percents = new int[allAnswers.size()];
        int allIdx = 0;
        int availIdx = 0;
        for (Answer answer : allAnswers) {
            int p = isAnswerAvailable(answer) ? availAnswersCount[availIdx++] : 0;
            percents[allIdx++] = p;
        }
        return new AudienceResult(percents);
    }
}
