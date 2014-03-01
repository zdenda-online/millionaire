package cz.dix.mil.ui;

import cz.dix.mil.controller.GameFlowListener;
import cz.dix.mil.model.game.Question;
import cz.dix.mil.model.state.GameModel;

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
public class RewardsPanel extends JPanel implements GameFlowListener {

    private static final int MARGIN = 5;

    private JLabel[] rewards;
    private final GameModel model;
    private Color lastQuestionColor = Colors.REWARDS_DEFAULT_COLOR; // first question

    public RewardsPanel(GameModel model) {
        this.model = model;
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGameStart() {
    }

    /**
     * Changes selected reward according to the actual state of the game.
     */
    @Override
    public void onNewQuestion() {
        int actualQuestionIdx = model.getActualQuestionIdx();

        if (lastQuestionColor != null) {
            rewards[actualQuestionIdx - 1].setForeground(lastQuestionColor);
            rewards[actualQuestionIdx - 1].updateUI();
            rewards[actualQuestionIdx].revalidate();
            rewards[actualQuestionIdx].repaint();
        }
        lastQuestionColor = rewards[actualQuestionIdx].getForeground();

        rewards[actualQuestionIdx].setForeground(Colors.REWARDS_ACTUAL_COLOR);
        rewards[actualQuestionIdx].revalidate();
        rewards[actualQuestionIdx].repaint();

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
            JLabel label = rewardLabel(i, question.getReward());
            label.setFont(new Font("Dialog", Font.PLAIN, 20));
            if (i == 0) {
                label.setForeground(Colors.REWARDS_ACTUAL_COLOR);
            } else if (model.isCheckpoint(question)) {
                label.setForeground(Colors.REWARDS_CHECKPOINT_COLOR);
            } else {
                label.setForeground(Colors.REWARDS_DEFAULT_COLOR);
            }
            add(label);
            this.rewards[i--] = label;
        }

        setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
    }

    private JLabel rewardLabel(int index, String rewardText) {
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
