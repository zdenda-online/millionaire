package cz.dix.mil.services.rnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of random algorithm that uses {@link Collections#shuffle(List)}.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class ShuffleRandomAlgorithm<T> implements RandomAlgorithm<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public T random(Collection<T> possibilities) {
        List<T> asList = new ArrayList<T>(possibilities);
        Collections.shuffle(asList);
        return asList.get(0);
    }
}
