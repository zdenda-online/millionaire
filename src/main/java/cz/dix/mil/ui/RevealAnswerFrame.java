package cz.dix.mil.ui;

import cz.dix.mil.controller.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Frame that is shown when answer is selected and allows moderator to do some disguise talks.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class RevealAnswerFrame extends JFrame {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private final GameController controller;

    public RevealAnswerFrame(GameController controller) {
        super();
        this.controller = controller;
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));

        JButton continueButton = new JButton("Reveal answer!");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                controller.revealCorrectAnswer();
            }
        });
        add(continueButton);
        setLocation(0, 0);
    }
}
