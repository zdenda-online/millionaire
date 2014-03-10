package cz.dix.mil.view.game.hint;

import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel that is shown when phone friend is selected. It contains countdown from 30 to zero.
 * Start the countdown by {@link #startCountdown()}.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class PhoneFriendPanel extends JPanel {

    private static final int SECONDS = 30;
    private final JLabel remainingLabel = new JLabel(String.valueOf(SECONDS));

    private Timer timer;
    private int remainingSecs = SECONDS - 1;

    /**
     * Creates a new phone friend panel.
     */
    public PhoneFriendPanel() {
        super(new GridBagLayout());
        Skin skin = SkinManager.getSkin();
        remainingLabel.setForeground(skin.phoneFriendCountdownText());
        remainingLabel.setFont(skin.ultraLargeFont());
        remainingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        remainingLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(remainingLabel);
    }

    /**
     * Starts the count-down.
     */
    public void startCountdown() {
        setVisible(true);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingLabel.setText(String.valueOf(remainingSecs));
                if (remainingSecs < 0) {
                    stopTimer();
                    remainingLabel.setText("");
                }
                remainingSecs--;
            }
        });
        timer.start();
    }

    private void stopTimer() {
        timer.stop();
    }
}
