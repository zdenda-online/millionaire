package cz.dix.mil.sound;

import cz.dix.mil.controller.ChainedAction;

/**
 * Sound that can be played during the game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface Sound {

    /**
     * Plays the sound from beginning to the end.
     * Note that this method does not allow to chain any action when sound is play.
     * If you need to do some specific action when sound is finished, use {@link #play(ChainedAction)} instead.
     */
    void play();

    /**
     * Plays the sound in infinite loop.
     * Note that this method does not allow to chain any action when sound is play.
     * If you need to do some specific action when sound is finished, use {@link #play(ChainedAction)} instead.
     */
    void playLooped();

    /**
     * Plays the sound from the beginning and fires given {@link ChainedAction} after the sound is played.
     * If you don't need chained action, use {@link #play()} instead.
     *
     * @param chainedAction action to be fired after sound is played
     */
    void play(final ChainedAction chainedAction);

    /**
     * Pauses this sound.
     * Fire {@link #continuePlaying()} if you want to continue where you paused.
     */
    void pausePlaying();

    /**
     * Continues playing previously paused sound.
     * May raise exception if sound was not paused before by {@link #pausePlaying()}.
     */
    void continuePlaying();

    /**
     * Stops playing this sound.
     * Note that the sound cannot be continued by {@link #continuePlaying()} but must be played from the beginning
     * by one of play method.
     */
    void stop();

    /**
     * Releases all resources that are connected to this sound.
     */
    void close();
}
