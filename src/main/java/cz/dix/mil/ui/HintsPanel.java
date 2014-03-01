package cz.dix.mil.ui;

import cz.dix.mil.controller.GameController;
import cz.dix.mil.controller.HintsListener;
import cz.dix.mil.model.state.GameModel;
import cz.dix.mil.model.state.Hint;

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
public class HintsPanel extends JPanel implements HintsListener {

    private static final int MARGIN = 5;
    private final GameModel model;
    private final GameController controller;

    public HintsPanel(GameModel model, GameController controller) {
        super(new FlowLayout());
        this.model = model;
        this.controller = controller;
        recreatePanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAskAudience() {
        recreatePanel();
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
        recreatePanel();
    }

    private void recreatePanel() {
        removeAll();

        if (model.isHintAvailable(Hint.AUDIENCE)) {
            final JButton phoneHelpButton = new JButton(new ImageIcon(getClass().getResource("/imgs/audience.jpg")));
            phoneHelpButton.setFocusable(false);
            phoneHelpButton.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
            phoneHelpButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.useAudienceHint();
                }
            });
            add(phoneHelpButton, BorderLayout.NORTH);

        }

        if (model.isHintAvailable(Hint.FIFTY_FIFTY)) {
            JButton fiftyFiftyButton = new JButton(new ImageIcon(getClass().getResource("/imgs/5050.jpg")));
            fiftyFiftyButton.setFocusable(false);
            fiftyFiftyButton.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
            fiftyFiftyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.useFiftyFiftyHint();
                }
            });
            add(fiftyFiftyButton, BorderLayout.NORTH);

        }

        if (model.isHintAvailable(Hint.PHONE_FRIEND)) {
            final JButton audienceHelpButton = new JButton(new ImageIcon(getClass().getResource("/imgs/phone.jpg")));
            audienceHelpButton.setFocusable(false);
            audienceHelpButton.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
            audienceHelpButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.usePhoneFriendHint();
                }
            });
            add(audienceHelpButton, BorderLayout.NORTH);

        }

        revalidate();
        repaint();
    }
}
