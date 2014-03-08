package cz.dix.mil.view.game.hint;

import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog that is shown when answer is selected and allows moderator to do some disguise talks.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class PhoneFriendDialog extends JDialog {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private Skin skin = SkinManager.getSkin();

    private Timer timer;
    private int remainingSecs = 29;
    private final JLabel remainingLabel = new JLabel("30");


    public PhoneFriendDialog(JFrame owner) {
        super(owner);
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imgs/icon.png")).getImage());
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        remainingLabel.setForeground(skin.phoneFriendCountdownText());
        remainingLabel.setFont(skin.largerFont());
        remainingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        remainingLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(remainingLabel, BorderLayout.CENTER);
    }

    /**
     * Starts the count-down in dialog and disposes it when time runs out.
     */
    public void startCountdown() {
        setVisible(true);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingLabel.setText(String.valueOf(remainingSecs));
                if (remainingSecs <= 0) {
                    stopTimer();
                    PhoneFriendDialog.this.dispose();
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
