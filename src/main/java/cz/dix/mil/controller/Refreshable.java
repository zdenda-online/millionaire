package cz.dix.mil.controller;

/**
 * Interface for UI components that can be refreshed to fetch new model values and update its view.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface Refreshable {

    /**
     * Refreshes given component.
     * It should be used for fetching new data from model.
     */
    void refresh();
}
