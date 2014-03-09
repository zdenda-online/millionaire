package cz.dix.mil.view.game;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that has image as a background.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class BackgroundPanel extends JPanel {

    private Image image;

    /*
     *  Set image as the background with the specified style
     */
    public BackgroundPanel(Image image) {
        this.image = image;
        setLayout(new BorderLayout());
    }

    @Override
    public Dimension getPreferredSize() {
        if (image == null)
            return super.getPreferredSize();
        else
            return new Dimension(image.getWidth(null), image.getHeight(null));
    }

    @Override
    public void add(Component component, Object constraints) {
        if (component instanceof JPanel) {
            setPanelNotOpaque((JPanel) component);
        }
        super.add(component, constraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
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
    private void setPanelNotOpaque(JPanel panel) {
        panel.setOpaque(false);
        Component[] subComponents = panel.getComponents();
        if (subComponents != null) {
            for (Component c : subComponents) {
                if (c instanceof JPanel) {
                    setPanelNotOpaque((JPanel) c);
                }
            }
        }
    }
}
