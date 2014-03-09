package cz.dix.mil.model.game.validation;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.Question;

import java.util.List;

/**
 * Abstract validation that provides basic checking of numbers of questions, answers and correct answers.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 * @see OriginalGameValidation
 */
public abstract class AbstractGameValidation implements GameValidation {

    /**
     * Gets a count of questions that are allowed to be in game (precisely).
     *
     * @return count of allowed questions
     */
    protected abstract int getQuestionsCount();

    /**
     * Gets a count of allowed answers every question can have.
     *
     * @return count of allowed answers per question
     */
    protected abstract int getAnswersCount();

    /**
     * Gets a count of correct answers for every question.
     *
     * @return count of correct answers for every question
     */
    protected abstract int getCorrectAnswersCount();

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate(Game game) {
        int allowedQuestionsCount = getQuestionsCount();
        int allowedAnswersCount = getAnswersCount();
        int allowedCorrectAnswersCount = getCorrectAnswersCount();

        List<Question> questions = game.getQuestions();
        if (questions == null || questions.size() != allowedQuestionsCount) {
            return new ValidationResult("Game must contain exactly " + allowedQuestionsCount + " questions!");
        }

        int questionNr = 1;
        for (Question question : questions) {
            List<Answer> answers = question.getAnswers();
            if (answers == null || answers.size() != allowedAnswersCount) {
                return new ValidationResult(questionNr + ". question must have exactly " + allowedAnswersCount + " answers!");
            }

            int correctAnswers = 0;
            for (Answer answer : answers) {
                if (answer.isCorrect()) {
                    correctAnswers++;
                }
            }

            if (correctAnswers != allowedCorrectAnswersCount) {
                return new ValidationResult(questionNr + ". question must have exactly " + allowedCorrectAnswersCount + " correct answer!");
            }
            questionNr++;
        }

        return new ValidationResult(); // all OK
    }

}
