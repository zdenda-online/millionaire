package cz.dix.mil.view.common;

import cz.dix.mil.view.skin.Gradient;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Rounded button which contents is by default gradient and default text color.
 * However you are free to override these methods to customize the look:
 * <ul>
 * <li>{@link #getBackgroundPaint()}</li>
 * <li>{@link #getTextColor()}</li>
 * <li>{@link #createShape()}</li>
 * </ul>
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class RoundedButton extends JButton {

    private static final float ARC_WIDTH = 80.0f;
    private static final float ARC_HEIGHT = 50.0f;
    private Shape shape;
    private Shape base;

    private Skin skin = SkinManager.getSkin();

    public RoundedButton(String text) {
        super(text);
        setFocusable(false);
        setContentAreaFilled(false);
        initShape();
        setFont(skin.normalFont());
    }

    /**
     * Gets a paint of background for this button.
     *
     * @return background paint
     */
    protected Paint getBackgroundPaint() {
        return constructGradient(skin.defaultButtonGradient());
    }

    /**
     * Gets a paint of background when there is rollover over button.
     *
     * @return rollover paint
     */
    protected Paint getRolloverPaint() {
        return constructGradient(skin.defaultButtonRolloverGradient());
    }

    /**
     * Gets a color for text of this button.
     *
     * @return color of text
     */
    protected Color getTextColor() {
        return skin.answerButtonText();
    }

    /**
     * Gets a shape for this button.
     *
     * @return button shape
     */
    protected Shape createShape() {
        return new RoundRectangle2D.Float(0, 0,
                getWidth() - 1, getHeight() - 1,
                ARC_WIDTH, ARC_HEIGHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        initShape();
        setForeground(getTextColor());
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isRolloverEnabled() && model.isRollover() && !model.isArmed()) {
            g2.setPaint(getRolloverPaint());
        } else {
            g2.setPaint(getBackgroundPaint());
        }
        g2.fill(shape);
        super.paintComponent(g2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintBorder(Graphics g) {
        initShape();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(skin.answerButtonBorderColor());
        g2.draw(shape);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(int x, int y) {
        initShape();
        return shape.contains(x, y);
    }

    private void initShape() {
        if (!getBounds().equals(base)) {
            base = getBounds();
            shape = createShape();
        }
    }

    protected GradientPaint constructGradient(Gradient gradient) {
        return new GradientPaint(0, 0, gradient.color1, 0, getHeight(), gradient.color2);
    }
}
