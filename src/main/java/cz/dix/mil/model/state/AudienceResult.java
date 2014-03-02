package cz.dix.mil.model.state;

/**
 * Represents results of audience hint.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AudienceResult {

    private int percentsForA;
    private int percentsForB;
    private int percentsForC;
    private int percentsForD;

    /**
     * Creates a new audience result.
     *
     * @param peopleForA count of people that voted for A answer
     * @param peopleForB count of people that voted for B answer
     * @param peopleForC count of people that voted for C answer
     * @param peopleForD count of people that voted for D answer
     */
    public AudienceResult(int peopleForA, int peopleForB, int peopleForC, int peopleForD) {
        int sum = peopleForA + peopleForB + peopleForC + peopleForD;
        percentsForA = count(peopleForA, sum);
        percentsForB = count(peopleForB, sum);
        percentsForC = count(peopleForC, sum);
        percentsForD = 100 - percentsForA - percentsForB - percentsForB;
    }

    private int count(int value, int sum) {
        double res = ((double) value / (double) sum) * 100;
        return (int) Math.round(res);
    }

    /**
     * Gets a percents of people who voted for A answer.
     *
     * @return percents for A answer
     */
    public int getPercentsForA() {
        return percentsForA;
    }

    /**
     * Gets a percents of people who voted for B answer.
     *
     * @return percents for B answer
     */
    public int getPercentsForB() {
        return percentsForB;
    }

    /**
     * Gets a percents of people who voted for C answer.
     *
     * @return percents for C answer
     */
    public int getPercentsForC() {
        return percentsForC;
    }

    /**
     * Gets a percents of people who voted for D answer.
     *
     * @return percents for D answer
     */
    public int getPercentsForD() {
        return percentsForD;
    }
}
