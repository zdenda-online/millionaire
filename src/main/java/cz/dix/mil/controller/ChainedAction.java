package cz.dix.mil.controller;

/**
 * Chained action wraps code to be performed after asynchronous processing is finished.
 * </p>
 * Typically is used by {@link SoundsController} to invoke actions after some sound is played.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface ChainedAction {

    /**
     * Execute next action in the chain.
     */
    void execute();
}
