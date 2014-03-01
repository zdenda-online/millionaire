package cz.dix.mil.ui;

import cz.dix.mil.controller.AnswerListener;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.game.Answer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Frame that is shown when answer is selected and allows moderator to do some disguise talks.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class WaitAnswerFrame extends JFrame implements AnswerListener {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private final GameController controller;

    public WaitAnswerFrame(GameController controller) {
        super();
        this.controller = controller;
        init();
    }

    /**
     * Shows this frame.
     *
     * @param answer selected answer by player
     */
    @Override
    public void onAnswerSelected(Answer answer) {
        setVisible(true);
    }

    /**
     * Hides this frame.
     */
    @Override
    public void onAnswersReveal() {
        setVisible(false);
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));

        JButton continueButton = new JButton("Continue...");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.showCorrectAnswer();
            }
        });
        add(continueButton);
        setLocation(0, 0);
    }

}
