package cz.dix.mil.ui;

import cz.dix.mil.controller.AnswerListener;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.controller.GameFlowListener;
import cz.dix.mil.controller.HintsListener;
import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.state.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel with possible 4 answers of the question.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AnswersPanel extends JPanel implements GameFlowListener, AnswerListener, HintsListener {

    private static final int MARGIN = 5;
    private static final int BUTTONS_WIDTH = 200;
    private static final int BUTTONS_HEIGHT = 40;
    private final String[] LETTERS = {"A", "B", "C", "D"};
    private final GameModel model;
    private final GameController controller;

    private JButton correctAnswerButton;
    private JButton selectedAnswerButton;
    private JButton[] answerButtons;

    public AnswersPanel(GameModel model, GameController controller) {
        super(new GridLayout(2, 2, MARGIN, MARGIN));
        this.model = model;
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGameStart() {
        recreatePanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNewQuestion() {
        recreatePanel();
    }

    /**
     * Changes color of button that corresponds to the selected answer.
     */
    @Override
    public void onAnswerSelected(Answer answer) {
        selectedAnswerButton.setBackground(Colors.SELECTED_ANSWER_BACKGROUND);
        selectedAnswerButton.setForeground(Colors.SELECTED_ANSWER_TEXT);
    }

    /**
     * Disables all buttons and changes color of button that corresponds to yjr correct answer.
     */
    @Override
    public void onAnswersReveal() {
        disableButtons();
        correctAnswerButton.setBackground(Colors.CORRECT_ANSWER_BACKGROUND);
        correctAnswerButton.setForeground(Colors.CORRECT_ANSWER_TEXT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAskAudience() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFiftyFifty() {
        recreatePanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPhoneFriend() {
    }

    private void recreatePanel() {
        removeAll();
        int i = 0;
        answerButtons = new JButton[4];
        for (final Answer answer : model.getActualQuestion().getAnswers()) {
            JPanel oneAnswerPanel = new JPanel(new BorderLayout());
            if (model.getAvailableAnswers().contains(answer)) {
                final JButton answerButton = new JButton(answer.getText());
                if (answer.isCorrect()) {
                    correctAnswerButton = answerButton;
                }
                answerButtons[i] = answerButton;
                answerButton.setFont(new Font("Dialog", Font.PLAIN, 20));
                answerButton.setFocusable(false);
                answerButton.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
                answerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedAnswerButton = answerButton;
                        controller.answerQuestion(answer);
                    }
                });
                JLabel letterLabel = new JLabel(LETTERS[i] + ") ");
                letterLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
                oneAnswerPanel.add(letterLabel, BorderLayout.WEST);
                oneAnswerPanel.add(answerButton, BorderLayout.CENTER);
            } else {
                oneAnswerPanel.add(new JLabel()); // removed by fifty-fifty
                answerButtons[i] = null;
            }
            i++;
            add(oneAnswerPanel);
        }

        setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
        revalidate();
        repaint();
    }

    private void disableButtons() {
        for (JButton button : answerButtons) {
            if (button != null) {
                for (ActionListener listener : button.getActionListeners()) {
                    button.removeActionListener(listener);
                }
            }
        }
    }

}
