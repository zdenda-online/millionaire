package cz.dix.mil.view.game.hint;

import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Dialog that is shown when audience is voting.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AudienceVotingDialog extends JDialog {

    private static final int WIDTH = 250;
    private static final int HEIGHT = 70;
    private Skin skin = SkinManager.getSkin();

    public AudienceVotingDialog(JFrame owner) {
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

        JLabel label = new JLabel("Audience is voting...");
        label.setForeground(skin.audienceVotingText());
        label.setFont(skin.defaultFont());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
