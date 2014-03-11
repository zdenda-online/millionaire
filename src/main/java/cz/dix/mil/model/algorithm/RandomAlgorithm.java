package cz.dix.mil.model.algorithm;

import java.util.Collection;

/**
 * Algorithm that is able to select one random entry from given collection of possibilities.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface RandomAlgorithm {

    /**
     * Selects one random entry.
     *
     * @param possibilities possibilities
     * @return one random entry
     */
    <T> T random(Collection<T> possibilities);

    /**
     * Selects one random entry.
     *
     * @param possibilities possibilities
     * @return one random entry
     */
    <T> T random(T[] possibilities);
}
