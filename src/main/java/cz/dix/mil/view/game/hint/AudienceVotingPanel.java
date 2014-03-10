package cz.dix.mil.view.game.hint;

import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple panel with single label for showing that audience is voting.
 * The text changes every second, start it by {@link #startVoting()}.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AudienceVotingPanel extends JPanel {

    private static final int DELAY_BETWEEN_TEXT_CHANGE_MILLIS = 500;
    private static final String[] TEXTS = {"Audience is voting...", "Audience is voting..", "Audience is voting.",
            "Audience is voting", "Audience is voting.", "Audience is voting.."};
    private static final int VOTING_SOUND_LENGTH_SECS = 10; // 9s lasts voting sound

    private final JLabel label = new JLabel();
    private int actualTick = 0;
    private Timer timer;


    /**
     * Creates a new audience voting panel.
     */
    public AudienceVotingPanel() {
        super(new GridBagLayout());
        Skin skin = SkinManager.getSkin();

        label.setForeground(skin.audienceVotingText());
        label.setFont(skin.largerFont());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label);
    }

    /**
     * Starts the voting.
     */
    public void startVoting() {
        label.setText(getActualText());
        setVisible(true);
        timer = new Timer(DELAY_BETWEEN_TEXT_CHANGE_MILLIS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualTick++;
                label.setText(getActualText());
                if (actualTick > (VOTING_SOUND_LENGTH_SECS * 2)) { // -2 because text changes every 0.5s
                    label.setText("Voting finished!");
                    stopTimer();
                }
            }
        });
        timer.start();
    }

    private void stopTimer() {
        timer.stop();
    }

    private String getActualText() {
        return TEXTS[actualTick % TEXTS.length];
    }
}
