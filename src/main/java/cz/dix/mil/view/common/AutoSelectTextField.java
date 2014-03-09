package cz.dix.mil.view.common;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Simple decorator of {@link JTextField} that automatically selects whole text
 * when text field is clicked for the first time.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AutoSelectTextField extends JTextField {

    private boolean alreadyClicked = false;

    public AutoSelectTextField() {
        super();
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (!alreadyClicked) {
            if (e.getID() == MouseEvent.MOUSE_CLICKED) {
                alreadyClicked = true;
                selectAll();
            }
        }
        super.processMouseEvent(e);
    }
}
