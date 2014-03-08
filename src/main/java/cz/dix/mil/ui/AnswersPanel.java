package cz.dix.mil.ui;

import cz.dix.mil.controller.ChainedAction;
import cz.dix.mil.controller.GameController;
import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.ui.skin.Skin;
import cz.dix.mil.ui.skin.SkinManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Panel with possible answers to the question.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AnswersPanel extends JPanel implements Refreshable {

    private static final int MARGIN = 5;
    private static final int BUTTONS_WIDTH = 200;
    private static final int BUTTONS_HEIGHT = 40;
    private final GameModel model;
    private final GameController controller;
    private Skin skin = SkinManager.getSkin();

    private AnswerButton correctAnswerButton;
    private AnswerButton[] answerButtons;

    public AnswersPanel(GameModel model, GameController controller) {
        super(new GridLayout(2, 2, MARGIN, MARGIN));
        this.model = model;
        this.controller = controller;
        setBackground(new Color(0, 0, 0, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        removeAll();
        int i = 0;
        List<Answer> allAnswers = model.getActualQuestion().getAnswers();
        answerButtons = new AnswerButton[allAnswers.size()];
        for (final Answer answer : allAnswers) {
            JPanel oneAnswerPanel = new JPanel(new BorderLayout());
            oneAnswerPanel.setBackground(new Color(0, 0, 0, 0));
            if (model.isAnswerAvailable(answer)) {
                final AnswerButton answerButton = new AnswerButton(answer.getText());
                if (answer.isCorrect()) {
                    correctAnswerButton = answerButton;
                }
                answerButtons[i] = answerButton;
                answerButton.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
                answerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        answerButton.setButtonState(AnswerButton.ButtonState.SELECTED_ANSWER);
                        controller.answerQuestion(answer);
                    }
                });
                char letter = (char) (65 + i);
                JLabel letterLabel = new JLabel(letter + ") ");
                letterLabel.setFont(skin.defaultFont());
                letterLabel.setOpaque(false);
                letterLabel.setForeground(skin.defaultTextColor());
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
        final AnswerButton appliedButton = correctAnswerButton;
        final AnswerButton.ButtonState originalType = appliedButton.getButtonState();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    appliedButton.setButtonState(AnswerButton.ButtonState.CORRECT_ANSWER);
                    appliedButton.repaint();
                    Thread.sleep(250);
                    appliedButton.setButtonState(originalType);
                    appliedButton.repaint();
                    Thread.sleep(250);
                    appliedButton.setButtonState(AnswerButton.ButtonState.CORRECT_ANSWER);
                    appliedButton.repaint();
                    Thread.sleep(250);
                    appliedButton.setButtonState(originalType);
                    appliedButton.repaint();
                    Thread.sleep(250);
                    appliedButton.setButtonState(AnswerButton.ButtonState.CORRECT_ANSWER);
                    appliedButton.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        chainedAction.toNextAction();
    }
}
