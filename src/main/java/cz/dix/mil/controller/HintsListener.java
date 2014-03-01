package cz.dix.mil.controller;

/**
 * Listener for events whether player takes specific hint.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface HintsListener {

    /**
     * Callback when player asks audience for help.
     */
    void onAskAudience();

    /**
     * Callback when player asks for 50-50 hint.
     */
    void onFiftyFifty();

    /**
     * Callback when player asks for phone friend hint.
     */
    void onPhoneFriend();
}
