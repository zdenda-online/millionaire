package cz.dix.mil.ui;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.runtime.GameModel;

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
    private final GameModel model;
    private final GameController controller;

    public AudienceResultDialog(JFrame owner, GameModel model, GameController controller) {
        super(owner);
        this.model = model;
        this.controller = controller;
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));

        int questionsCount = model.getActualQuestion().getAnswers().size();
        JPanel answersPanel = new JPanel(new FlowLayout());
        final JSpinner[] spinners = new JSpinner[questionsCount];
        int charNo = 65;
        for (int i = 0; i < questionsCount; i++) {
            char letter = (char) (charNo + i);
            answersPanel.add(new JLabel(String.valueOf(letter)));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
            answersPanel.add(spinner);
            spinners[i] = spinner;
        }

        JButton submitButton = new JButton("Submit results");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                controller.setAudienceHintResults(getSpinnerValues(spinners));
            }
        });

        setLayout(new BorderLayout());
        add(answersPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void setVisible(boolean visible) {
        init();
        super.setVisible(visible);
    }

    private int[] getSpinnerValues(JSpinner[] spinners) {
        int[] out = new int[spinners.length];
        for (int i = 0; i < spinners.length; i++) {
            out[i] = (int) spinners[i].getValue();
        }
        return out;
    }
}
