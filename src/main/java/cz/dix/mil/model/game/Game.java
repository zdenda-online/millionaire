package cz.dix.mil.model.game;

import cz.dix.mil.model.game.validation.GameValidation;
import cz.dix.mil.model.game.validation.ValidationResult;

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
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
@XmlRootElement(name = "game")
@XmlAccessorType(XmlAccessType.FIELD)
public class Game {

    private static final String DEFAULT_NAME = "Who Wants to Be a Millionaire?";

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
     * @param file           file with game contents
     * @param gameValidation validation that should be used for resolving whether game is valid
     * @return new game instance
     * @throws GameValidationException possible exception if file is corrupt
     */
    public static Game newInstance(File file, GameValidation gameValidation) throws GameValidationException {
        if (!file.exists()) {
            throw new GameValidationException("Selected file does not exist!");
        }
        Game game;
        try {
            JAXBContext ctx = JAXBContext.newInstance(Question.class, Game.class, Answer.class);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            game = (Game) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new GameValidationException(e.getMessage());
        }

        ValidationResult validationResult = gameValidation.validate(game);
        if (!validationResult.isValid()) {
            throw new GameValidationException(validationResult.getMessage());
        }
        return game;
    }

    /**
     * Gets a name of the game.
     *
     * @return name of the game
     */
    public String getName() {
        return (name == null) ? DEFAULT_NAME : name;
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
