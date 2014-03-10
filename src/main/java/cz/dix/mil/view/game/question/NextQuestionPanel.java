package cz.dix.mil.view.game.question;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.view.common.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple panel with single button for showing next question.
 * Note that you should {@link #reactivateButton()} before every use because during click,
 * buttons gets automatically hidden (not to allow click quickly twice).
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class NextQuestionPanel extends JPanel {

    private static final int BUTTON_WIDTH = 400;
    private static final int BUTTON_HEIGHT = 50;

    private final JButton button = new RoundedButton("Next Question");

    /**
     * Creates a new question panel.
     *
     * @param controller controller of the game
     */
    public NextQuestionPanel(final GameController controller) {
        super(new GridBagLayout());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setVisible(false);
                controller.showNextQuestion();
            }
        });
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        add(button);
        reactivateButton();
    }

    /**
     * Reactivates the button of this panel.
     */
    public void reactivateButton() {
        button.setVisible(true);
    }
}
