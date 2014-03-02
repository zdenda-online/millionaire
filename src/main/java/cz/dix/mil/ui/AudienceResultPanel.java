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

    private final GameModel model;

    public AudienceResultPanel(GameModel model) {
        this.model = model;
        setLayout(new GridLayout(1, 4));
    }

    @Override
    public void refresh() {
        if (!model.hasAudienceResult()) {
            return; // nothing to do
        }

        AudienceResult result = model.getAudienceResult();
        removeAll();

        add(new AnswerResultPanel(10));
        add(new AnswerResultPanel(30));
        add(new AnswerResultPanel(10));
        add(new AnswerResultPanel(50));


//        add(new JLabel("A=" + result.getPercentsForA() + "% " +
//                "B=" + result.getPercentsForB() + "% " +
//                "C=" + result.getPercentsForC() + "% " +
//                "D=" + result.getPercentsForD() + "%"));

        revalidate();
        repaint();
    }

    private static class AnswerResultPanel extends JPanel {

        private final int percents;

        private AnswerResultPanel(int percents) {
            this.percents = percents;
            init();
        }

        private void init() {
            setLayout(new BorderLayout());
            add(new ResultRectangle(percents), BorderLayout.CENTER);
            add(new JLabel(percents + "%"), BorderLayout.SOUTH);
        }
    }

    private static class ResultRectangle extends JPanel {

        private static final int WIDTH = 50;
        private final int percents;

        private ResultRectangle(int percents) {
            super();
            this.percents = percents;
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2d = (Graphics2D) g;
//
//            GradientPaint gp1 = new GradientPaint(0, getHeight() - percents,
//                    new Color(230, 0, 230), 0, getHeight(),
//                    new Color(0, 0, 255), true);
//
//            g2d.setPaint(gp1);
//            g2d.fillRect(0, getHeight() - percents, WIDTH, percents);

            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int rectHeight = heightOfRectangle();
            GradientPaint gp1 = new GradientPaint(0, getHeight() - rectHeight,
                    new Color(230, 0, 230), 0, getHeight(),
                    new Color(0, 0, 255), true);

            g2d.setPaint(gp1);
            g2d.fillRect(0, getHeight() - rectHeight, WIDTH, rectHeight);
        }

        private int heightOfRectangle() {
            double ccaHeight = ((double) getHeight()) * ((double) percents / 100);
            return (int) Math.round(ccaHeight);
        }
    }
}
