package cz.dix.mil.ui;

import cz.dix.mil.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Dialog for submitting results of audience voting (hint).
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AudienceResultDialog extends JDialog {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 120;
    private final GameController controller;

    public AudienceResultDialog(JFrame owner, GameController controller) {
        super(owner);
        this.controller = controller;
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));

        JPanel answersPanel = new JPanel(new FlowLayout());
        answersPanel.add(new JLabel("A"));
        final JSpinner aSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        answersPanel.add(aSpinner);

        answersPanel.add(new JLabel("B"));
        final JSpinner bSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        answersPanel.add(bSpinner);

        answersPanel.add(new JLabel("C"));
        final JSpinner cSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        answersPanel.add(cSpinner);

        answersPanel.add(new JLabel("D"));
        final JSpinner dSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        answersPanel.add(dSpinner);

        JButton submitButton = new JButton("Submit results");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                controller.setAudienceHintResults(
                        (int) aSpinner.getValue(),
                        (int) bSpinner.getValue(),
                        (int) cSpinner.getValue(),
                        (int) dSpinner.getValue());
            }
        });

        setLayout(new BorderLayout());
        add(answersPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
