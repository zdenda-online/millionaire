package cz.dix.mil.ui;

import cz.dix.mil.model.runtime.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Dialog that is shown when game is over (won or answered incorrectly).
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class FinalRewardDialog extends JDialog {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 70;
    private final GameModel model;
    private final JLabel rewardLabel = new JLabel();

    public FinalRewardDialog(JFrame owner, GameModel model) {
        super(owner);
        this.model = model;
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));
        setLayout(new BorderLayout());

        rewardLabel.setForeground(Colors.AUDIENCE_VOTING_TEXT);
        rewardLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        rewardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rewardLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(rewardLabel);

        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void setVisible(boolean visible) {
        rewardLabel.setText("Reward: " + model.getFinalReward());
        super.setVisible(visible);
    }
}
