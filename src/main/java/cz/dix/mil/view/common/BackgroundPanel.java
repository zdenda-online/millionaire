package cz.dix.mil.view.common;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that has image as a background.
 * Every other panel that is added to this one will be translucent
 * (the background of this panel will be visible instead).
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class BackgroundPanel extends JPanel {

    private Image image;

    /**
     * Creates a new background panel.
     *
     * @param image image to be shown on the background
     */
    public BackgroundPanel(Image image) {
        this.image = image;
        setLayout(new BorderLayout());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(null), image.getHeight(null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        setPanelsNonOpaque(this);
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawImage(image, 0, 0, d.width, d.height, null);
    }

    /**
     * Sets given panel's opaque to be false.
     * If this panel contains any other panels, it sets recursively their opaque to false too.
     *
     * @param panel panel to be set non opaque
     */
    private void setPanelsNonOpaque(JPanel panel) {
        if (!this.equals(panel)) { // for everyone except this instance
            panel.setOpaque(false);
        }
        Component[] subComponents = panel.getComponents();
        if (subComponents != null) {
            for (Component c : subComponents) {
                if (c instanceof JPanel) {
                    setPanelsNonOpaque((JPanel) c);
                }
            }
        }
    }
}
