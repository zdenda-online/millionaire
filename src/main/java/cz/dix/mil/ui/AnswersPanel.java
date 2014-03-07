package cz.dix.mil.ui;

import cz.dix.mil.controller.ChainedAction;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.controller.Refreshable;
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
public class AnswersPanel extends JPanel implements Refreshable {

    private static final int MARGIN = 5;
    private static final int BUTTONS_WIDTH = 200;
    private static final int BUTTONS_HEIGHT = 40;
    private final GameModel model;
    private final GameController controller;

    private JButton correctAnswerButton;
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
    public void refresh() {
        removeAll();
        int i = 0;
        answerButtons = new JButton[4];
        for (final Answer answer : model.getActualQuestion().getAnswers()) {
            JPanel oneAnswerPanel = new JPanel(new BorderLayout());
            if (model.isAnswerAvailable(answer)) {
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
                        answerButton.setBackground(Colors.SELECTED_ANSWER_BACKGROUND);
                        answerButton.setForeground(Colors.SELECTED_ANSWER_TEXT);
                        controller.answerQuestion(answer);
                    }
                });
                char letter = (char) (65 + i);
                JLabel letterLabel = new JLabel(letter + ") ");
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

    /**
     * Disables all answer buttons.
     */
    public void disableAnswers() {
        for (JButton button : answerButtons) {
            if (button != null) {
                for (ActionListener listener : button.getActionListeners()) {
                    button.removeActionListener(listener);
                }
            }
        }
    }

    /**
     * Disables all buttons and changes color of button that corresponds to yjr correct answer.
     */
    public void revealAnswer(ChainedAction chainedAction) {
        final JButton appliedButton = correctAnswerButton;
        final Color originalBackground = correctAnswerButton.getBackground();
        final Color originalForeground = correctAnswerButton.getForeground();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    appliedButton.setBackground(Colors.CORRECT_ANSWER_BACKGROUND);
                    appliedButton.setForeground(Colors.CORRECT_ANSWER_TEXT);
                    Thread.sleep(250);
                    appliedButton.setBackground(originalBackground);
                    appliedButton.setForeground(originalForeground);
                    Thread.sleep(250);
                    appliedButton.setBackground(Colors.CORRECT_ANSWER_BACKGROUND);
                    appliedButton.setForeground(Colors.CORRECT_ANSWER_TEXT);
                    Thread.sleep(250);
                    appliedButton.setBackground(originalBackground);
                    appliedButton.setForeground(originalForeground);
                    Thread.sleep(250);
                    appliedButton.setBackground(Colors.CORRECT_ANSWER_BACKGROUND);
                    appliedButton.setForeground(Colors.CORRECT_ANSWER_TEXT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        chainedAction.toNextAction();
    }
}
