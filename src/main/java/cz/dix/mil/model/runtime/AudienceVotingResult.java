package cz.dix.mil.model.runtime;

import java.util.Arrays;

/**
 * Represents results of audience voting hint.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class AudienceVotingResult {

    private int[] percents;

    /**
     * Creates a new audience voting result.
     *
     * @param counts counts (number of votes) for answers
     */
    public AudienceVotingResult(int[] counts) {
        int sum = sum(counts);
        if (sum == 100) {
            this.percents = counts;
            return; // no need to compute anything
        }

        this.percents = new int[counts.length];
        for (int i = 0; i < counts.length - 1; i++) {
            percents[i] = toPercents(counts[i], sum);
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

    private int toPercents(int value, int sum) {
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
     * Gets a vote of answers (percents).
     *
     * @return vote of answers
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
