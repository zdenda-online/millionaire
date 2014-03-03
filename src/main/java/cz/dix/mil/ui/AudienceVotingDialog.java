package cz.dix.mil.ui;

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

    public AudienceVotingDialog(JFrame owner) {
        super(owner);
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Audience is voting...");
        label.setForeground(Colors.AUDIENCE_VOTING_TEXT);
        label.setFont(new Font("Dialog", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label);

        setLocationRelativeTo(null);
        setResizable(false);
    }
}
