package cz.dix.mil.ui;

import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.state.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panel with actual question.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class QuestionsPanel extends JPanel implements Refreshable {

    private static final int QUESTION_MARGIN = 30;
    private final JLabel questionLabel = new JLabel();
    private final GameModel model;

    public QuestionsPanel(GameModel model) {
        super(new BorderLayout());
        this.model = model;
        init();
    }

    private void init() {
        questionLabel.setText("Here will be question");
        questionLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        questionLabel.setBorder(new EmptyBorder(QUESTION_MARGIN, QUESTION_MARGIN, QUESTION_MARGIN, QUESTION_MARGIN));
        add(questionLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        questionLabel.setText("<html>" + model.getActualQuestion().getText() + "</html>");
    }
}
