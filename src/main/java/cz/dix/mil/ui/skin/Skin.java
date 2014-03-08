package cz.dix.mil.ui.skin;

import java.awt.*;

/**
 * Represents skin of GUI.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface Skin {

    Font defaultFont();

    Font smallerFont();

    Font largerFont();

    Color defaultTextColor();

    Color answerButtonBorderColor();

    Gradient answerButtonDefaultGradient();

    Color answerButtonDefaultText();

    Gradient answerButtonSelectedGradient();

    Color answerButtonSelectedText();

    Gradient answerButtonCorrectGradient();

    Color answerButtonCorrectText();


    Color audienceVotingText();

    Gradient audienceResultColumn();

    Color audienceResultTextColor();

    Color phoneFriendCountdownText();

    Color rewardDefaultText();

    Color rewardActualText();

    Color rewardCheckpointText();

    Color finalRewardText();

}
