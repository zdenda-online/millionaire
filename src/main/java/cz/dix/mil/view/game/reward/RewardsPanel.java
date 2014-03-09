package cz.dix.mil.view.game.reward;

import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.game.Question;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Panel with rewards and actual question that is asked.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class RewardsPanel extends JPanel implements Refreshable {

    private static final int MARGIN = 5;
    private Skin skin = SkinManager.getSkin();

    private JLabel[] rewards;
    private final GameModel model;

    public RewardsPanel(GameModel model) {
        this.model = model;
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        int actualQuestionIdx = model.getActualQuestionIdx();
        int previousQuestionIdx = actualQuestionIdx - 1;

        if (previousQuestionIdx >= 0) {
            rewards[previousQuestionIdx].setForeground(getDefaultColor(previousQuestionIdx));
        }
        rewards[actualQuestionIdx].setForeground(skin.rewardActualQuestionText());

        revalidate();
        repaint();
    }

    private void init() {
        List<Question> questions = new ArrayList<>(model.getAllQuestions());
        Collections.reverse(questions);

        setLayout(new GridLayout(questions.size(), 1));
        rewards = new JLabel[questions.size()];

        int i = questions.size() - 1;
        for (Question question : questions) {
            JLabel label = createRewardLabel(i, question.getReward());
            label.setFont(skin.defaultFont());
            if (i == 0) {
                label.setForeground(skin.rewardActualQuestionText());
            } else {
                label.setForeground(getDefaultColor(i));
            }
            add(label);
            this.rewards[i--] = label;
        }

        setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
    }

    private Color getDefaultColor(int questionIdx) {
        if (questionIdx != 0 && (questionIdx + 1) % 5 == 0) {
            return skin.rewardCheckpointText();
        } else {
            return skin.rewardDefaultText();
        }

    }

    private JLabel createRewardLabel(int index, String rewardText) {
        int number = index + 1;
        int spacesCount = 5 - Integer.toString(number).length();

        StringBuilder text = new StringBuilder();
        text.append(number);
        for (int i = 0; i < spacesCount; i++) {
            text.append(" ");
        }
        text.append(rewardText);
        return new JLabel(text.toString());
    }
}
