package cz.dix.mil.view.game.question;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.view.common.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple panel with single button for showing correct answer.
 * Note that you should {@link #reactivateButton()} before every use because during click,
 * buttons gets automatically disabled (not to allow click quickly twice)
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class ShowCorrectAnswerPanel extends JPanel {

    private static final int BUTTON_WIDTH = 400;
    private static final int BUTTON_HEIGHT = 50;

    private final GameController controller;
    private final JButton button;

    /**
     * Creates a reveal answer panel.
     *
     * @param controller controller of the game
     */
    public ShowCorrectAnswerPanel(final GameController controller) {
        super(new GridBagLayout());
        this.controller = controller;
        this.button = new RoundedButton("Reveal Answer");
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        add(button);
        reactivateButton();
    }

    /**
     * Reactivates the button of this panel.
     */
    public void reactivateButton() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.removeActionListener(this);
                controller.showCorrectAnswer();
            }
        });
    }
}
