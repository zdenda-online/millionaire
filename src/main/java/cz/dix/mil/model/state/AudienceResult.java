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
     * @param countForA count of people that voted for A answer
     * @param countForB count of people that voted for B answer
     * @param countForC count of people that voted for C answer
     * @param countForD count of people that voted for D answer
     */
    public AudienceResult(int countForA, int countForB, int countForC, int countForD) {
        int sum = countForA + countForB + countForC + countForD;
        this.percentsForA = count(countForA, sum);
        this.percentsForB = count(countForB, sum);
        this.percentsForC = count(countForC, sum);
        this.percentsForD = 100 - (percentsForA + percentsForB + percentsForC);
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
