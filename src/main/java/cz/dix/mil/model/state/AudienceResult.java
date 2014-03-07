package cz.dix.mil.model.state;

import java.util.Arrays;

/**
 * Represents results of audience hint.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AudienceResult {

    private int[] percents;

    /**
     * Creates a new audience result.
     *
     * @param counts counts (number of votes) for answers
     */
    public AudienceResult(int... counts) {
        this.percents = new int[counts.length];
        int sum = sum(counts);

        // last question is rest from 100%
        for (int i = 0; i < counts.length - 1; i++) {
            percents[i] = count(counts[i], sum);
        }
        percents[counts.length - 1] = 100 - sum(percents);
    }

    private int sum(int[] values) {
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum;
    }

    private int count(int value, int sum) {
        double res = ((double) value / (double) sum) * 100;
        return (int) Math.round(res);
    }

    /**
     * Gets a percents of people who voted for I-th answer.
     *
     * @param idx index of answer (starting at 0)
     * @return percents for I-th answer
     */
    public int getPercents(int idx) {
        return percents[idx];
    }

    /**
     * Gets a count of answers (percents).
     *
     * @return count of answers
     */
    public int getPercentsSize() {
        return percents.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Arrays.toString(percents);
    }
}
