package cz.dix.mil.model.game;

import javax.xml.bind.annotation.*;
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
     * Creates a new game.
     *
     * @param questions questions of the game
     */
    public Game(List<Question> questions) {
        this.name = DEFAULT_NAME;
        this.questions = questions;
    }

    /**
     * Creates a new game.
     *
     * @param name      name of the game
     * @param questions questions of the game
     */
    public Game(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
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
     * Gets a vote of all questions.
     *
     * @return vote of questions
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
