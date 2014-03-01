package cz.dix.mil.model.game;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Single question within the game.
 * Its difficulty is determined by the order within parent's {@link Game} instance.
 *
 * @author Zdenek Obst
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
    }

    public Question(String text, String reward, String answersDescription, Answer... answers) {
        boolean hasCorrect = false;
        for (Answer answer : answers) {
            if (answer.isCorrect() && hasCorrect) {
                throw new IllegalArgumentException("Can have only one correct answer");
            }
            if (answer.isCorrect()) {
                hasCorrect = true;
            }
        }
        if (!hasCorrect) {
            throw new IllegalArgumentException("At least one answer must be correct");
        }

        this.text = text;
        this.reward = reward;
        this.answersDescription = answersDescription;
        this.answers = new ArrayList<Answer>();
        Collections.addAll(this.answers, answers);
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getReward() {
        return reward;
    }

    public String getAnswersDescription() {
        return answersDescription;
    }
}
