package cz.dix.mil.model.game;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
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

    @XmlElement(name = "name")
    private String name;
    @XmlElementRef
    private List<Question> questions;

    public Game() {
        // for JAXB
    }

    /**
     * Creates a new instance from XML game file.
     *
     * @param file file with game contents
     * @return new game instance
     * @throws GameValidationException possible exception if file is corrupt
     */
    public static Game load(File file) throws GameValidationException {
        if (!file.exists()) {
            throw new GameValidationException("Selected file does not exist!");
        }
        Game game = null;
        try {
            JAXBContext ctx = JAXBContext.newInstance(Question.class, Game.class, Answer.class);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            game = (Game) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new GameValidationException(e.getMessage());
        }
        game.validate();
        return game;
    }

    private void validate() throws GameValidationException {
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
     * Gets a name of the game.
     *
     * @return name of the game
     */
    public String getName() {
        return (name == null) ? "Default" : name;
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
