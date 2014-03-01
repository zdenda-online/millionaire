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

    public boolean isCorrect() {
        return isCorrect;
    }

    public String getText() {
        return text;
    }
}
