package cz.dix.mil.controller;

import cz.dix.mil.sound.SoundsPlayer;

/**
 * Chained action wraps code to be performed after asynchronous processing is finished.
 * </p>
 * Typically is used by {@link SoundsPlayer} to invoke actions after some sound is played.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface ChainedAction {

    /**
     * Calls next action in the chain.
     */
    void toNextAction();
}
