package cz.dix.mil.ui.skin;

import java.awt.*;

/**
 * Default skin of the GUI that is very similar to original TV show.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class DefaultSkin implements Skin {

    /**
     * {@inheritDoc}
     */
    @Override
    public Font defaultFont() {
        return new Font(Font.DIALOG, Font.PLAIN, 20);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Font smallerFont() {
        return new Font(Font.DIALOG, Font.BOLD, 15);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Font largerFont() {
        return new Font(Font.DIALOG, Font.BOLD, 26);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color defaultTextColor() {
        return new Color(255, 220, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color answerButtonBorderColor() {
        return new Color(255, 220, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient answerButtonDefaultGradient() {
        return new Gradient(new Color(70, 0, 150), new Color(0, 0, 100));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color answerButtonDefaultText() {
        return new Color(255, 220, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient answerButtonCorrectGradient() {
        return new Gradient(new Color(0, 240, 0), new Color(50, 150, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color answerButtonCorrectText() {
        return new Color(255, 255, 255);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color audienceVotingText() {
        return defaultTextColor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient answerButtonSelectedGradient() {
        return new Gradient(new Color(255, 200, 0), new Color(240, 120, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color answerButtonSelectedText() {
        return new Color(255, 255, 255);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient audienceResultColumn() {
        return new Gradient(new Color(230, 0, 230), new Color(0, 0, 255));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color audienceResultTextColor() {
        return new Color(255, 255, 255);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color phoneFriendCountdownText() {
        return defaultTextColor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color rewardDefaultText() {
        return new Color(255, 255, 255);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color rewardActualQuestionText() {
        return new Color(255, 150, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color rewardCheckpointText() {
        return new Color(255, 230, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color finalRewardText() {
        return defaultTextColor();
    }
}
