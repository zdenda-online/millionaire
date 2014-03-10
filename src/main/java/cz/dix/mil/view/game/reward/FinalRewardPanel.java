package cz.dix.mil.view.game.reward;

import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;

/**
 * Simple panel with single label for showing final reward.
 * Note that before showing it, you should call {@link #refresh()} to fetch correct reward from model.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class FinalRewardPanel extends JPanel implements Refreshable {

    private final GameModel model;
    private final JLabel rewardLabel = new JLabel();

    public FinalRewardPanel(GameModel model) {
        super(new GridBagLayout());
        this.model = model;

        Skin skin = SkinManager.getSkin();
        rewardLabel.setForeground(skin.finalRewardText());
        rewardLabel.setFont(skin.ultraLargeFont());
        rewardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rewardLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(rewardLabel);
    }

    @Override
    public void refresh() {
        rewardLabel.setText("Final Reward: " + model.getFinalReward());
    }
}
