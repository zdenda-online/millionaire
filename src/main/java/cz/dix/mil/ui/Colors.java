package cz.dix.mil.ui;

import java.awt.*;

/**
 * Static holder of colors.
 * May be changed in the future to interface (and provide methods instead of static fields) to create skins.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class Colors {

    public static final Color SELECTED_ANSWER_BACKGROUND = new Color(255, 150, 0);
    public static final Color SELECTED_ANSWER_TEXT = new Color(0, 0, 0);

    public static final Color CORRECT_ANSWER_BACKGROUND = new Color(150, 240, 0);
    public static final Color CORRECT_ANSWER_TEXT = new Color(0, 0, 0);

    public static final Color REWARDS_DEFAULT_COLOR = new Color(255, 255, 255);
    public static final Color REWARDS_ACTUAL_COLOR = new Color(255, 150, 0);
    public static final Color REWARDS_CHECKPOINT_COLOR = new Color(255, 240, 150);

    public static final Color AUDIENCE_RESULT_GRADIENT1 = new Color(230, 0, 230);
    public static final Color AUDIENCE_RESULT_GRADIENT2 = new Color(0, 0, 255);
}
