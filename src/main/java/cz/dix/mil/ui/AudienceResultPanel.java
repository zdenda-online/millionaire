package cz.dix.mil.ui;

import cz.dix.mil.controller.Refreshable;
import cz.dix.mil.model.state.AudienceResult;
import cz.dix.mil.model.state.GameModel;

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

    private final GameModel model;

    public AudienceResultPanel(GameModel model) {
        this.model = model;
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, COLUMNS_GAP, 0);
        setLayout(layout);
    }

    /**
     * Refreshes panel according to the results of audience voting.
     */
    @Override
    public void refresh() {
        removeAll();

        if (model.hasAudienceResult()) {
            AudienceResult result = model.getAudienceResult();
            removeAll();

            add(new AnswerResultPanel(result.getPercentsForA(), "A"));
            add(new AnswerResultPanel(result.getPercentsForB(), "B"));
            add(new AnswerResultPanel(result.getPercentsForC(), "C"));
            add(new AnswerResultPanel(result.getPercentsForD(), "D"));
        }

        revalidate();
        repaint();
    }

    private static class AnswerResultPanel extends JPanel {

        private AnswerResultPanel(int percents, String letter) {
            setLayout(new BorderLayout(10, 10));
            setPreferredSize(new Dimension(COL_WIDTH, COL_HEIGHT));
            add(new ResultRectangle(percents), BorderLayout.CENTER);

            JLabel label = new JLabel(letter + ": " + percents + "%");
            label.setFont(new Font("Dialog", Font.BOLD, 15));
            add(label, BorderLayout.SOUTH);
        }
    }

    private static class ResultRectangle extends JPanel {

        private final int percents;

        private ResultRectangle(int percents) {
            super();
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
            this.percents = percents;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int rectHeight = heightOfRectangle();
            GradientPaint gp1 = new GradientPaint(0, getHeight() - rectHeight,
                    new Color(230, 0, 230), 0, getHeight(),
                    new Color(0, 0, 255), true);

            g2d.setPaint(gp1);
            g2d.fillRect(0, getHeight() - rectHeight, RECTANGLE_WIDTH, rectHeight);
        }

        private int heightOfRectangle() {
            double ccaHeight = ((double) getHeight()) * ((double) percents / 100);
            return (int) Math.round(ccaHeight);
        }
    }
}
