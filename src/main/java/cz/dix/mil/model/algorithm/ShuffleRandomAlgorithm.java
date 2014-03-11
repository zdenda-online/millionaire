package cz.dix.mil.model.algorithm;

import java.util.*;

/**
 * Implementation of random algorithm that uses {@link Collections#shuffle(List)}.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class ShuffleRandomAlgorithm implements RandomAlgorithm {

    private final Random random = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T random(Collection<T> possibilities) {
        if (possibilities == null || possibilities.size() == 0) {
            throw new IllegalArgumentException("Must have at least one possibility");
        }
        List<T> asList = new ArrayList<>(possibilities);
        int randomIndex = random.nextInt(asList.size());
        return asList.get(randomIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T random(T[] possibilities) {
        return random(Arrays.asList(possibilities));
    }
}
