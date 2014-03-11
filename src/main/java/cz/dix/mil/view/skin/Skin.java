package cz.dix.mil.view.skin;

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
    Font normalFont();

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
     * Gets an ultra large font of GUI.
     *
     * @return ultra large font
     */
    Font ultraLargeFont();

    /**
     * Gets a default text color of GUI.
     *
     * @return default text color
     */
    Color defaultTextColor();

    /**
     * Gets a default background of buttons.
     *
     * @return default background of button
     */
    Gradient defaultButtonGradient();

    /**
     * Gets a color of question text.
     *
     * @return color of question text
     */
    Color questionTextColor();

    /**
     * Gets a color of answer letter.
     *
     * @return color of answer letter
     */
    Color answerLetterColor();

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
    Gradient answerButtonGradient();

    /**
     * Gets a color of answer button default text.
     *
     * @return answer button default text
     */
    Color answerButtonText();

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
     * Gets a color of phone friend countdown text.
     *
     * @return phone friend countdown text
     */
    Color phoneFriendCountdownText();

    /**
     * Gets a color of phone friend conversation text (automatic phone friend).
     *
     * @return phone friend conversation text
     */
    Color phoneFriendConversationText();

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
