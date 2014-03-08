package cz.dix.mil.ui.skin;

import java.awt.*;

/**
 * Represents skin of GUI.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface Skin {

    /**
     * Gets a default font of GUI.
     *
     * @return default font
     */
    Font defaultFont();

    /**
     * Gets a smaller font of GUI.
     *
     * @return smaller font
     */
    Font smallerFont();

    /**
     * Gets a larger font of GUI.
     *
     * @return larger font
     */
    Font largerFont();

    /**
     * Gets a default text color of GUI.
     *
     * @return default text color
     */
    Color defaultTextColor();

    /**
     * Gets a color of answer button border.
     *
     * @return color of answer button border
     */
    Color answerButtonBorderColor();

    /**
     * Gets a background of default answer button.
     *
     * @return background of default answer button
     */
    Gradient answerButtonDefaultGradient();

    /**
     * Gets a color of answer button default text.
     *
     * @return answer button default text
     */
    Color answerButtonDefaultText();

    /**
     * Gets a background of selected answer button.
     *
     * @return background of selected answer button
     */
    Gradient answerButtonSelectedGradient();

    /**
     * Gets a color of answer button selected text.
     *
     * @return answer button selected text
     */
    Color answerButtonSelectedText();

    /**
     * Gets a background of correct answer button.
     *
     * @return background of correct answer button
     */
    Gradient answerButtonCorrectGradient();

    /**
     * Gets a color of answer button correct text.
     *
     * @return answer button correct text
     */
    Color answerButtonCorrectText();


    /**
     * Gets a color of audience voting text.
     *
     * @return color of audience voting text
     */
    Color audienceVotingText();

    /**
     * Gets a background of audience voting columns (representing percents).
     *
     * @return background of audience voting columns
     */
    Gradient audienceResultColumn();

    /**
     * Gets a color of audience voting percents text.
     *
     * @return color of audience voting percents text
     */
    Color audienceResultTextColor();

    /**
     * Gets a color of phone friend countdown text
     *
     * @return phone friend countdown text
     */
    Color phoneFriendCountdownText();

    /**
     * Gets a color of default reward (in rewards panel).
     *
     * @return color of default reward
     */
    Color rewardDefaultText();

    /**
     * Gets a color of actual question (in rewards panel).
     *
     * @return color of actual question
     */
    Color rewardActualQuestionText();

    /**
     * Gets a color of checkpoint (in rewards panel).
     *
     * @return color of checkpoint
     */
    Color rewardCheckpointText();

    /**
     * Gets a color of final reward text.
     *
     * @return color of final reward text
     */
    Color finalRewardText();
}
