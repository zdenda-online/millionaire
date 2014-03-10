package cz.dix.mil.view.game.question;

import cz.dix.mil.view.common.RoundedButton;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import java.awt.*;

/**
 * Button that is used for single answer of question and has its state which drives its look.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AnswerButton extends RoundedButton {

    private Skin skin = SkinManager.getSkin();

    public enum ButtonState {
        DEFAULT, SELECTED_ANSWER, CORRECT_ANSWER
    }

    private ButtonState buttonState = ButtonState.DEFAULT;

    public AnswerButton(String text) {
        super(text);
    }

    /**
     * Sets actual button state which drives how will be button painted.
     *
     * @param buttonState state to set
     */
    public void setButtonState(ButtonState buttonState) {
        this.buttonState = buttonState;
    }

    /**
     * Gets actual button state.
     *
     * @return state of button
     */
    public ButtonState getButtonState() {
        return buttonState;
    }

    @Override
    protected Paint getBackgroundPaint() {
        switch (buttonState) {
            case SELECTED_ANSWER:
                return constructGradient(skin.answerButtonSelectedGradient());
            case CORRECT_ANSWER:
                return constructGradient(skin.answerButtonCorrectGradient());
            case DEFAULT:
                return constructGradient(skin.answerButtonGradient());
            default:
                return super.getBackgroundPaint();
        }
    }

    @Override
    protected Color getTextColor() {
        switch (buttonState) {
            case SELECTED_ANSWER:
                return skin.answerButtonSelectedText();
            case CORRECT_ANSWER:
                return skin.answerButtonCorrectText();
            case DEFAULT:
                return skin.answerButtonText();
            default:
                return super.getTextColor();
        }
    }

}
