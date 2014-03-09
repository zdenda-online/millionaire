package cz.dix.mil.model.game;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Single question within the game.
 * Its difficulty is determined by the order within parent's {@link Game} instance.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
@XmlRootElement(name = "question")
@XmlAccessorType(XmlAccessType.FIELD)
public class Question {

    @XmlElement(name = "text", required = true)
    private String text;
    @XmlElement(name = "reward", required = true)
    private String reward;
    @XmlElementWrapper(name = "answers", required = true)
    @XmlElementRef
    private List<Answer> answers;
    @XmlElement(name = "answers-description", required = false)
    private String answersDescription;

    public Question() {
        // for JAXB
    }

    /**
     * Creates a new question.
     *
     * @param text               text of the question
     * @param reward             reward for the question
     * @param answers            answers of question
     * @param answersDescription description of answers
     */
    public Question(String text, String reward, List<Answer> answers, String answersDescription) {
        this.text = text;
        this.reward = reward;
        this.answers = answers;
        this.answersDescription = answersDescription;
    }

    /**
     * Gets a text of the question.
     *
     * @return text of question
     */
    public String getText() {
        return text;
    }

    /**
     * Gets all answers of the question.
     *
     * @return all answers of question
     */
    public List<Answer> getAnswers() {
        return answers;
    }

    /**
     * Gets a name of reward of the question.
     *
     * @return name of reward
     */
    public String getReward() {
        return reward;
    }

    /**
     * Gets a description to answers (may be used by moderator?).
     *
     * @return description to answers
     */
    public String getAnswersDescription() {
        return answersDescription;
    }
}
