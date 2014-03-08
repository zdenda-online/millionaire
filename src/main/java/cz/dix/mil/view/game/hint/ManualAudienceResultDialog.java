package cz.dix.mil.view.game.hint;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.model.runtime.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for submitting results of audience voting manually.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class ManualAudienceResultDialog extends JDialog {

    private static final int WIDTH = 120;
    private static final int HEIGHT_DEFAULT = 70;
    private static final int DIALOG_MARGIN = 10;
    private static final int ITEMS_MARGIN = 5;

    private final GameModel model;
    private final GameController controller;

    public ManualAudienceResultDialog(JFrame owner, GameModel model, GameController controller) {
        super(owner);
        this.model = model;
        this.controller = controller;
    }

    private void init() {
        int questionsCount = model.getActualQuestion().getAnswers().size();
        setSize(WIDTH, questionsCount * 30 + HEIGHT_DEFAULT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imgs/icon.png")).getImage());
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));

        JPanel answersPanel = new JPanel(new GridLayout(questionsCount, 2, ITEMS_MARGIN, ITEMS_MARGIN));
        final JSpinner[] spinners = new JSpinner[questionsCount];
        int charNo = 65;
        for (int i = 0; i < questionsCount; i++) {
            char letter = (char) (charNo + i);
            answersPanel.add(new JLabel(String.valueOf(letter)));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
            answersPanel.add(spinner);
            spinners[i] = spinner;
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                controller.setAudienceHintResults(getSpinnerValues(spinners));
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(ITEMS_MARGIN, ITEMS_MARGIN));
        mainPanel.setBorder(new EmptyBorder(DIALOG_MARGIN, DIALOG_MARGIN, DIALOG_MARGIN, DIALOG_MARGIN));
        mainPanel.add(answersPanel, BorderLayout.CENTER);
        mainPanel.add(submitButton, BorderLayout.SOUTH);
        add(mainPanel);
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
