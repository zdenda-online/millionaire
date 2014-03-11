package cz.dix.mil.view.skin;

import java.awt.*;

/**
 * Default skin of the GUI that is very similar to original TV show.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class DefaultSkin implements Skin {

    // Colors used for text
    private static final Color YELLOW = new Color(255, 220, 0);
    private static final Color ORANGE = new Color(255, 150, 0);

    // Colors used for buttons
    private static final Color DARK_BLUE = new Color(20, 20, 150);
    private static final Color DARK_PURPLE = new Color(70, 0, 100);
    private static final Color GREEN = new Color(0, 240, 0);
    private static final Color DARK_GREEN = new Color(40, 115, 0);
    private static final Color LIGHT_ORANGE = new Color(255, 174, 0);
    private static final Color DARK_ORANGE = new Color(240, 100, 0);

    // Colors used for audience result
    private static final Color LIGHT_PURPLE = new Color(190, 0, 190);
    private static final Color BLUE = new Color(40, 0, 230);

    /**
     * {@inheritDoc}
     */
    @Override
    public Font formsFont() {
        return new Font(Font.SANS_SERIF, Font.PLAIN, 17);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color formsComponentsText() {
        return Color.WHITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color formsButtonsText() {
        return YELLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Font normalFont() {
        return new Font(Font.SANS_SERIF, Font.PLAIN, 20);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Font smallerFont() {
        return new Font(Font.SANS_SERIF, Font.BOLD, 15);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Font largerFont() {
        return new Font(Font.SANS_SERIF, Font.BOLD, 26);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Font ultraLargeFont() {
        return new Font(Font.SANS_SERIF, Font.BOLD, 36);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color defaultTextColor() {
        return YELLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient defaultButtonGradient() {
        return new Gradient(DARK_BLUE, DARK_PURPLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color questionTextColor() {
        return YELLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color answerLetterColor() {
        return ORANGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color answerButtonBorderColor() {
        return YELLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient answerButtonGradient() {
        return new Gradient(DARK_BLUE, DARK_PURPLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color answerButtonText() {
        return YELLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient answerButtonCorrectGradient() {
        return new Gradient(GREEN, DARK_GREEN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color answerButtonCorrectText() {
        return Color.WHITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color audienceVotingText() {
        return ORANGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient answerButtonSelectedGradient() {
        return new Gradient(LIGHT_ORANGE, DARK_ORANGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color answerButtonSelectedText() {
        return Color.WHITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient audienceResultColumn() {
        return new Gradient(LIGHT_PURPLE, BLUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color audienceResultTextColor() {
        return Color.WHITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color phoneFriendCountdownText() {
        return ORANGE;
    }

    @Override
    public Color phoneFriendConversationText() {
        return Color.WHITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color rewardDefaultText() {
        return Color.WHITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color rewardActualQuestionText() {
        return ORANGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color rewardCheckpointText() {
        return YELLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color finalRewardText() {
        return ORANGE;
    }
}
