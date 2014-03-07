package cz.dix.mil.model.game;

import cz.dix.mil.model.GameValidationException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Game is a simple aggregator of questions.
 * It is static representation of the game itself but it does not contain any runtime data
 *
 * @author Zdenek Obst
 */
@XmlRootElement(name = "game")
@XmlAccessorType(XmlAccessType.FIELD)
public class Game {

    @XmlElementRef
    private List<Question> questions;

    public Game() {
        // for JAXB
    }

    public Game(List<Question> questions) {
        this.questions = questions;
    }

    public void validate() {
        if (questions == null || questions.size() == 0) {
            throw new GameValidationException("Game is must contain at least one question!");
        }
        for (Question question : questions) {
            List<Answer> answers = question.getAnswers();
            if (answers == null || answers.size() != 4) { // remove check for 4 answers?, simple audience won't work!
                throw new GameValidationException("Every question must contain four answers!");
            }

            boolean hasCorrectAnswer = false;
            for (Answer answer : answers) {
                if (hasCorrectAnswer && answer.isCorrect()) {
                    throw new GameValidationException("Every question can have only one correct answer!");
                }
                if (!hasCorrectAnswer) {
                    hasCorrectAnswer = answer.isCorrect();
                }
            }
            if (!hasCorrectAnswer) {
                throw new GameValidationException("Every question must have one correct answer!");
            }
        }
    }

    /**
     * Gets a count of all questions.
     *
     * @return count of questions
     */
    public int getQuestionsCount() {
        return questions.size();
    }

    /**
     * Gets a question under given index.
     *
     * @param questionIdx index of question
     * @return question of game
     */
    public Question getQuestion(int questionIdx) {
        return questions.get(questionIdx);
    }

    /**
     * Gets all questions of the game.
     *
     * @return all questions
     */
    public List<Question> getQuestions() {
        return questions;
    }
}
