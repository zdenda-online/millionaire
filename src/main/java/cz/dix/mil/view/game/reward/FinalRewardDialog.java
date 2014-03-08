package cz.dix.mil.view.game.reward;

import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog that is shown when game is over (won or answered incorrectly).
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class FinalRewardDialog extends JDialog {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 70;
    private Skin skin = SkinManager.getSkin();

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
        setIconImage(new ImageIcon(getClass().getResource("/imgs/icon.png")).getImage());
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        rewardLabel.setForeground(skin.finalRewardText());
        rewardLabel.setFont(skin.defaultFont());
        rewardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rewardLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(rewardLabel);
    }

    @Override
    public void setVisible(boolean visible) {
        rewardLabel.setText("Reward: " + model.getFinalReward());
        super.setVisible(visible);
    }
}
