package cz.dix.mil.ui;

import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.ui.skin.Skin;
import cz.dix.mil.ui.skin.SkinManager;

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
    private Skin skin = SkinManager.getSkin();

    private final JLabel questionLabel = new JLabel();
    private final GameModel model;

    public QuestionsPanel(GameModel model) {
        super(new BorderLayout());
        this.model = model;
        init();
    }

    private void init() {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        questionLabel.setFont(skin.defaultFont());
        questionLabel.setForeground(skin.questionTextColor());
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
