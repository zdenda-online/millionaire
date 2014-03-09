package cz.dix.mil.model.game;

import cz.dix.mil.model.game.validation.GameValidation;
import cz.dix.mil.model.game.validation.ValidationResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory that provides static methods for creation new games.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameFactory {

    private static JAXBContext jaxbContext;


    static {
        try {
            jaxbContext = JAXBContext.newInstance(Question.class, Game.class, Answer.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Unable to create JAXB Context", e);
        }
    }

    /**
     * Creates a new instance from XML game file.
     *
     * @param file           file with game contents
     * @param gameValidation validation that should be used for resolving whether game is valid
     * @return new game instance
     * @throws GameCreationException possible exception if file is corrupt
     */
    public static Game newGame(File file, GameValidation gameValidation) throws GameCreationException {
        if (!file.exists()) {
            throw new GameCreationException("Selected file does not exist!");
        }
        Game game;
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            game = (Game) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new GameCreationException(e.getMessage());
        }

        validate(game, gameValidation);
        return game;
    }

    /**
     * Creates a new default template game with 15 questions, 4 answers per question where first one will be correct.
     *
     * @return template game
     */
    public static Game newGameTemplate() {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 15; i++) {

            String questionText = (i + 1) + ". question text";
            String questionReward = templateReward(i);
            String answersDescription = "It is A) because of this and that...";

            List<Answer> answers = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                char letter = (char) (65 + j);
                boolean isCorrect = (j == 0);
                String answerText = (isCorrect ? "Correct" : "Incorrect") + " answer (" + letter + ")";
                answers.add(new Answer(answerText, isCorrect));
            }

            questions.add(new Question(questionText, questionReward, answers, answersDescription));
        }

        return new Game(questions);
    }

    /**
     * Exports given game to XML file if game instance is valid.
     *
     * @param game           game to be exported
     * @param gameValidation validation that should be used for resolving whether game is valid
     * @param file           target file
     * @throws GameCreationException possible exception game is not valid
     */
    public static void exportToXml(Game game, GameValidation gameValidation, File file) {
        validate(game, gameValidation);
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(game, file);
        } catch (JAXBException e) {
            throw new GameCreationException(e.getMessage());
        }

    }

    private static void validate(Game game, GameValidation gameValidation) {
        if (game == null) {
            throw new GameCreationException("Game is null!");
        }
        if (gameValidation == null) {
            throw new GameCreationException("Game validation algorithm is null!");
        }
        ValidationResult validationResult = gameValidation.validate(game);
        if (!validationResult.isValid()) {
            throw new GameCreationException(validationResult.getMessage());
        }
    }

    private static String templateReward(int questionIdx) {
        switch (questionIdx) {
            case 0:
                return "$100";
            case 1:
                return "$200";
            case 2:
                return "$300";
            case 3:
                return "$500";
            case 4:
                return "$1,000";
            case 5:
                return "$2,000";
            case 6:
                return "$4,000";
            case 7:
                return "$8,000";
            case 8:
                return "$16,000";
            case 9:
                return "$32,000";
            case 10:
                return "$64,000";
            case 11:
                return "$125,000";
            case 12:
                return "$250,000";
            case 13:
                return "$500,000";
            case 14:
                return "$1 MILLION";
            default:
                return "Unreachable reward";
        }
    }
}
