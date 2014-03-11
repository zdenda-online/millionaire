package cz.dix.mil.view.game.hint;

import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.model.runtime.PhoneFriendResult;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel that is shown when phone friend is selected. It contains countdown from 30 to zero.
 * Also may contain conversation of automatic phone friend if no real friend is used.
 * Start the countdown and conversation by {@link #startCountdown(PhoneFriendResult)}.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class PhoneFriendPanel extends JPanel {

    private static final int SECONDS = 30;
    private final JLabel countdownLabel = new JLabel(String.valueOf(SECONDS));
    private final JLabel friendConversationLabel = new JLabel("");

    private final GameModel model;
    private Timer timer;
    private int remainingSecs = SECONDS - 1;

    /**
     * Creates a new phone friend panel.
     */
    public PhoneFriendPanel(GameModel model) {
        super(new GridBagLayout());
        this.model = model;

        Skin skin = SkinManager.getSkin();
        countdownLabel.setForeground(skin.phoneFriendCountdownText());
        countdownLabel.setFont(skin.ultraLargeFont());
        countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countdownLabel.setVerticalAlignment(SwingConstants.CENTER);

        friendConversationLabel.setForeground(skin.phoneFriendConversationText());
        friendConversationLabel.setFont(skin.normalFont());
        friendConversationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        friendConversationLabel.setVerticalAlignment(SwingConstants.CENTER);
        friendConversationLabel.setVisible(false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        add(countdownLabel, constraints);
        constraints.gridx++;
        add(friendConversationLabel, constraints);
    }

    /**
     * Starts the count-down and shows messages for given phone friend result (if any).
     * If no automatic phone friend result is given, only countdown is shown.
     *
     * @param phoneFriendResult result of automatic phone friend, pass null if using real friend
     */
    public void startCountdown(PhoneFriendResult phoneFriendResult) {
        final String[] conversation = getConversation(phoneFriendResult);
        setVisible(true);

        if (conversation != null) {
            friendConversationLabel.setText(setForLabel(conversation[0]));
            friendConversationLabel.setVisible(true);
        }

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownLabel.setText(String.valueOf(remainingSecs));

                if (conversation != null && remainingSecs == 20) {
                    friendConversationLabel.setText(setForLabel(conversation[1]));
                }
                if (conversation != null && remainingSecs == 15) {
                    friendConversationLabel.setText(setForLabel(conversation[2]));
                }
                if (conversation != null && remainingSecs == 10) {
                    friendConversationLabel.setText(setForLabel(conversation[3]));
                }

                if (remainingSecs < 0) {
                    stopTimer();
                    countdownLabel.setText("");
                    friendConversationLabel.setText("");
                }
                remainingSecs--;
            }
        });
        timer.start();
    }

    private void stopTimer() {
        timer.stop();
    }

    private String[] getConversation(PhoneFriendResult phoneFriendResult) {
        if (phoneFriendResult != null) { // automatic phone friend
            return PhoneFriendMessagesFactory.getMessages(phoneFriendResult, model.getActualQuestion(),
                    model.getPossibleAnswers(), model.getActualQuestionDifficulty());
        }
        return null;
    }

    private String setForLabel(String origString) {
        return "<html><div align=\"center\">" + origString.replaceAll("\n", "<br/>") + "</div></html>";
    }
}
