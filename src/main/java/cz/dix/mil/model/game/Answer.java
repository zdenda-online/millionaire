package cz.dix.mil.model.game;

import javax.xml.bind.annotation.*;

/**
 * One single answer to the question.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
@XmlRootElement(name = "answer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Answer {

    @XmlValue
    private String text;
    @XmlAttribute(name = "is-correct", required = false)
    private Boolean isCorrect;

    public Answer() {
        // for JAXB
    }

    /**
     * Creates a new answer.
     *
     * @param text      text of the answer
     * @param isCorrect flag whether answer is correct
     */
    public Answer(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect ? true : null; // incorrect answer attribute is not saved
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
        return (isCorrect == null) ? false : isCorrect;
    }
}
