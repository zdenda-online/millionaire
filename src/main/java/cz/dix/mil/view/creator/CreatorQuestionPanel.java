package cz.dix.mil.view.creator;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.game.Question;
import cz.dix.mil.view.common.AutoSelectTextField;
import cz.dix.mil.view.common.MaxLengthDocument;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel for creating single question.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class CreatorQuestionPanel extends JPanel {

    private static final int MAX_REWARD_CHARACTERS = 20;
    private static final int MAX_ANSWER_CHARACTERS = 30;

    private Skin skin = SkinManager.getSkin();
    private final int answersCount;
    private final int itemsMargin;
    private final AutoSelectTextField questionTextField = new AutoSelectTextField();
    private final AutoSelectTextField questionRewardField = new AutoSelectTextField();
    private final AnswerPanel[] answerPanels;

    public CreatorQuestionPanel(int answersCount, int itemsMargin) {
        super();
        this.answersCount = answersCount;
        this.itemsMargin = itemsMargin;
        this.answerPanels = new AnswerPanel[answersCount];
        init();
    }

    private void init() {
        setBorder(BorderFactory.createTitledBorder("Question"));

        JPanel questionPanel = new JPanel(new BorderLayout(itemsMargin, itemsMargin));
        questionPanel.add(setSkin(new JLabel("Text:")), BorderLayout.WEST);
        questionPanel.add(setSkin(questionTextField), BorderLayout.CENTER);

        JPanel rewardPanel = new JPanel(new BorderLayout(itemsMargin, itemsMargin));
        rewardPanel.add(setSkin(new JLabel("Reward:")), BorderLayout.WEST);
        questionRewardField.setDocument(new MaxLengthDocument(MAX_REWARD_CHARACTERS));
        rewardPanel.add(setSkin(questionRewardField), BorderLayout.CENTER);

        JPanel questionRewardPanel = new JPanel(new GridLayout(2, 1, itemsMargin, itemsMargin));
        questionRewardPanel.add(questionPanel);
        questionRewardPanel.add(rewardPanel);

        JPanel answersPanel = new JPanel(new GridLayout(answersCount / 2, 2, itemsMargin, itemsMargin));
        ButtonGroup buttonGroup = new ButtonGroup(); // only one can selected
        for (int i = 0; i < answerPanels.length; i++) {
            answerPanels[i] = new AnswerPanel(buttonGroup);
            answersPanel.add(answerPanels[i]);
        }

        setLayout(new GridLayout(2, 1, itemsMargin, itemsMargin));
        add(questionRewardPanel);
        add(answersPanel);

    }

    /**
     * Sets the question according to which panel with form will be repainted.
     *
     * @param question question to be set
     */
    public void setQuestion(Question question) {
        questionTextField.setText(question.getText());
        questionRewardField.setText(question.getReward());
        int i = 0;
        for (Answer answer : question.getAnswers()) {
            answerPanels[i++].setAnswer(answer);
        }
    }

    /**
     * Gets a question generated from contents of the form.
     *
     * @return question according to contents of form
     */
    public Question getQuestion() {
        List<Answer> answers = new ArrayList<>();
        for (AnswerPanel answerPanel : answerPanels) {
            answers.add(answerPanel.getAnswer());
        }

        // no description is saved now (not used actually in game) - that is last null
        return new Question(questionTextField.getText(), questionRewardField.getText(), answers, null);
    }

    /**
     * Clears clicked flags on all fields.
     *
     * @see AutoSelectTextField
     */
    public void clearClickedFlags() {
        questionTextField.clearClickedFlag();
        questionRewardField.clearClickedFlag();
        for (AnswerPanel answerPanel : answerPanels) {
            answerPanel.clearClickedFlag();
        }
    }

    /**
     * Panel with answer radio button and text field.
     */
    private class AnswerPanel extends JPanel {

        private final JRadioButton isCorrectButton = new JRadioButton();
        private final AutoSelectTextField answerField = new AutoSelectTextField();

        private AnswerPanel(ButtonGroup buttonGroup) {
            buttonGroup.add(isCorrectButton);
            setLayout(new BorderLayout(itemsMargin, itemsMargin));
            answerField.setDocument(new MaxLengthDocument(MAX_ANSWER_CHARACTERS));
            add(isCorrectButton, BorderLayout.WEST);
            add(setSkin(answerField), BorderLayout.CENTER);
        }

        private void setAnswer(Answer answer) {
            isCorrectButton.setSelected(answer.isCorrect());
            answerField.setText(answer.getText());
        }

        private Answer getAnswer() {
            return new Answer(answerField.getText(), isCorrectButton.isSelected());
        }

        private void clearClickedFlag() {
            answerField.clearClickedFlag();
        }
    }

    private JComponent setSkin(JComponent component) {
        if (component instanceof JButton) {
            component.setForeground(skin.formsButtonsText());
        } else {
            component.setForeground(skin.formsComponentsText());
        }
        component.setFont(skin.formsFont());
        return component;
    }
}
