package cz.dix.mil.services.rnd;

import java.util.Collection;

/**
 * Algorithm that is able to select one random entry from given collection of possibilities.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface RandomAlgorithm<T> {

    /**
     * Selects one random entry.
     *
     * @param possibilities possibilities
     * @return one random entry
     */
    T random(Collection<T> possibilities);
}
