package cz.dix.mil.model;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.game.Game;
import cz.dix.mil.model.game.Question;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Factory that is able to create objects of the model.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class ModelFactory {

    /**
     * Creates a new game from the game file.
     *
     * @param file game file
     * @return game instance loaded from file
     */
    public static Game newGame(File file) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Question.class, Game.class, Answer.class);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            return (Game) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Unable to read game file due to: " + e.getMessage());
        }
    }
}
