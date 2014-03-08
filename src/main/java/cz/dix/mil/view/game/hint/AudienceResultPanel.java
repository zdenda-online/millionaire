package cz.dix.mil.view.game.hint;

import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.runtime.AudienceResult;
import cz.dix.mil.model.runtime.GameModel;
import cz.dix.mil.view.skin.Gradient;
import cz.dix.mil.view.skin.Skin;
import cz.dix.mil.view.skin.SkinManager;

import javax.swing.*;
import java.awt.*;

/**
 * Panel with results of audience help.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AudienceResultPanel extends JPanel implements Refreshable {

    private static final int COLUMNS_GAP = 20;
    private static final int RECTANGLE_WIDTH = 70;
    private static final int RECTANGLE_HEIGHT = 200;
    private static final int COL_WIDTH = RECTANGLE_WIDTH;
    private static final int COL_HEIGHT = RECTANGLE_HEIGHT + 30;

    private Skin skin = SkinManager.getSkin();

    private final GameModel model;

    public AudienceResultPanel(GameModel model) {
        this.model = model;
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, COLUMNS_GAP, 0);
        setLayout(layout);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    /**
     * Refreshes panel according to the results of audience voting.
     */
    @Override
    public void refresh() {
        removeAll();

        if (model.hasAudienceResult()) {
            AudienceResult result = model.getAudienceResult();
            for (int i = 0; i < result.getPercentsSize(); i++) {
                char letter = (char) (65 + i);
                add(new AnswerResultPanel(result.getPercents(i), String.valueOf(letter)));
            }
        }

        revalidate();
        repaint();
    }

    private class AnswerResultPanel extends JPanel {

        private AnswerResultPanel(int percents, String letter) {
            setLayout(new BorderLayout(10, 10));
            setPreferredSize(new Dimension(COL_WIDTH, COL_HEIGHT));
            setOpaque(false);
            setBackground(new Color(0, 0, 0, 0));

            add(new ResultRectangle(percents), BorderLayout.CENTER);

            JLabel label = new JLabel(letter + ": " + percents + "%");
            label.setFont(skin.smallerFont());
            label.setForeground(skin.audienceResultTextColor());
            add(label, BorderLayout.SOUTH);
        }
    }

    private class ResultRectangle extends JPanel {

        private final int percents;

        private ResultRectangle(int percents) {
            super();
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
            setOpaque(false);
            setBackground(new Color(0, 0, 0, 0));
            this.percents = percents;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int rectHeight = heightOfRectangle();
            Gradient grad = skin.audienceResultColumn();
            GradientPaint gp1 = new GradientPaint(0, getHeight() - rectHeight,
                    grad.color1, 0, getHeight(), grad.color2, true);

            g2d.setPaint(gp1);
            g2d.fillRect(0, getHeight() - rectHeight, RECTANGLE_WIDTH, rectHeight);
        }

        private int heightOfRectangle() {
            double ccaHeight = ((double) getHeight()) * ((double) percents / 100);
            return (int) Math.round(ccaHeight);
        }
    }
}
