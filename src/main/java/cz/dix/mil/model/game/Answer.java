package cz.dix.mil.model.game;

import javax.xml.bind.annotation.*;

/**
 * One single answer to the question.
 *
 * @author Zdenek Obst
 */
@XmlRootElement(name = "answer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Answer {

    @XmlValue
    private String text;
    @XmlAttribute(name = "is-correct", required = false)
    private boolean isCorrect;

    public Answer() {
    }

    public Answer(String text, boolean isCorrect) {
        this.isCorrect = isCorrect;
        this.text = text;
    }

    /**
     * Gets a text of the answer.
     *
     * @return text of answer
     */
    public String getText() {
        return text;
    }

    /**
     * Gets a flag whether answer is correct.
     *
     * @return true if answer is correct, otherwise false
     */
    public boolean isCorrect() {
        return isCorrect;
    }
}
