package cz.dix.mil.ui;

import cz.dix.mil.ui.skin.Gradient;
import cz.dix.mil.ui.skin.Skin;
import cz.dix.mil.ui.skin.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Button that is used for single answer of question.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AnswerButton extends JButton {

    private static final float ARC_WIDTH = 80.0f;
    private static final float ARC_HEIGHT = 50.0f;
    private static final int FOCUS_STROKE = 2;

    protected Shape shape;
    protected Shape border;
    protected Shape base;

    private Skin skin = SkinManager.getSkin();


    public enum ButtonState {
        DEFAULT, SELECTED_ANSWER, CORRECT_ANSWER
    }

    private ButtonState buttonState = ButtonState.DEFAULT;

    public AnswerButton(String text) {
        super(text);
        setRolloverEnabled(false);
        setFocusable(false);
        setContentAreaFilled(false);
        initShape();
        setFont(skin.defaultFont());
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

    protected void initShape() {
        if (!getBounds().equals(base)) {
            base = getBounds();
            shape = new RoundRectangle2D.Float(0, 0,
                    getWidth() - 1, getHeight() - 1,
                    ARC_WIDTH, ARC_HEIGHT);
            border = new RoundRectangle2D.Float(FOCUS_STROKE, FOCUS_STROKE,
                    getWidth() - 1 - FOCUS_STROKE * 2,
                    getHeight() - 1 - FOCUS_STROKE * 2,
                    ARC_WIDTH, ARC_HEIGHT);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        initShape();
        setForeground(getTextColor());

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(getGradient());
        g2.fill(shape);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setColor(getBackground());
        super.paintComponent(g2);
    }

    @Override
    protected void paintBorder(Graphics g) {
        initShape();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(skin.answerButtonBorderColor());
        g2.draw(shape);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    @Override
    public boolean contains(int x, int y) {
        initShape();
        return shape.contains(x, y);
    }

    private GradientPaint getGradient() {
        Gradient g;
        switch (buttonState) {
            case SELECTED_ANSWER:
                g = skin.answerButtonSelectedGradient();
                return constructGradient(g.color1, g.color2);
            case CORRECT_ANSWER:
                g = skin.answerButtonCorrectGradient();
                return constructGradient(g.color1, g.color2);
            case DEFAULT:
            default:
                g = skin.answerButtonDefaultGradient();
                return constructGradient(g.color1, g.color2);
        }
    }

    private Color getTextColor() {
        switch (buttonState) {
            case SELECTED_ANSWER:
                return skin.answerButtonSelectedText();
            case CORRECT_ANSWER:
                return skin.answerButtonCorrectText();
            case DEFAULT:
            default:
                return skin.answerButtonDefaultText();
        }
    }

    private GradientPaint constructGradient(Color color1, Color color2) {
        return new GradientPaint(0, 0, color1, 0, getHeight(), color2);
    }
}
