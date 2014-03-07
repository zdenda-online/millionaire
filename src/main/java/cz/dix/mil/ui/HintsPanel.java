package cz.dix.mil.ui;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.model.runtime.Hint;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel with hints of the player.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class HintsPanel extends JPanel implements Refreshable {

    private static final int MARGIN = 5;
    private final GameModel model;
    private final GameController controller;

    private JButton[] hintButtons;

    public HintsPanel(GameModel model, GameController controller) {
        super(new FlowLayout());
        this.model = model;
        this.controller = controller;
        refresh();
    }

    /**
     * Disables all buttons with hints
     */
    public void disableHints() {
        for (JButton button : hintButtons) {
            if (button != null) {
                for (ActionListener listener : button.getActionListeners()) {
                    button.removeActionListener(listener);
                }
            }
        }
    }

    @Override
    public void refresh() {
        removeAll();

        hintButtons = new JButton[3];
        if (model.isHintAvailable(Hint.AUDIENCE)) {
            ImageIcon image = new ImageIcon(getClass().getResource("/imgs/audience.jpg"));
            hintButtons[0] = addHintButton(image, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.useAudienceHint();
                }
            });

        }

        if (model.isHintAvailable(Hint.FIFTY_FIFTY)) {
            ImageIcon image = new ImageIcon(getClass().getResource("/imgs/5050.jpg"));
            hintButtons[1] = addHintButton(image, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.useFiftyFiftyHint();
                }
            });

        }

        if (model.isHintAvailable(Hint.PHONE_FRIEND)) {
            ImageIcon image = new ImageIcon(getClass().getResource("/imgs/phone.jpg"));
            hintButtons[2] = addHintButton(image, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.usePhoneFriendHint();
                }
            });
        }

        revalidate();
        repaint();
    }

    private JButton addHintButton(ImageIcon image, ActionListener actionListener) {
        JButton audienceHelpButton = new JButton(image);
        audienceHelpButton.setFocusable(false);
        audienceHelpButton.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
        audienceHelpButton.addActionListener(actionListener);
        add(audienceHelpButton, BorderLayout.NORTH);
        return audienceHelpButton;
    }
}
