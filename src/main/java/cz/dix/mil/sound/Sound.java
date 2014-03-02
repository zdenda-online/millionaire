package cz.dix.mil.sound;

import cz.dix.mil.controller.ChainedAction;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Simple sound that can be played once or infinite loop until stopped.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class Sound {

    private final InputStream is;
    private final boolean isLooping;
    private final int lengthSecs;
    private Clip clip;
    private int pausePosition = -1;

    public Sound(InputStream is, boolean isLooping, int lengthSecs) {
        if (is == null) {
            throw new IllegalArgumentException("Given input stream is null!");
        }
        this.is = is;
        this.isLooping = isLooping;
        this.lengthSecs = lengthSecs;
    }

    /**
     * Plays the sound and does not block processing.
     * If you want blocking processing, use {@link #playBlocked(ChainedAction)} instead.
     */
    public void play() {
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(is);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();  //TODO
        } finally {
            if (audioIn != null) {
                try {
                    audioIn.close();
                } catch (IOException e) {
                    e.printStackTrace();  //TODO
                }
            }
        }
    }

    /**
     * Plays the sound to the end and fires given {@link ChainedAction} after the sound is played.
     * If you don't want blocking processing, use {@link #play()} instead.
     *
     * @param chainedAction action to be fired after sound is played
     */
    public void playBlocked(final ChainedAction chainedAction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                play();
                try {
                    Thread.sleep(lengthSecs * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (chainedAction != null) {
                    chainedAction.toNextAction();
                }
            }
        }).start();
    }

    /**
     * Pauses this sound.
     * Fire {@link #continuePlaying()} if you want to continue where you paused.
     */
    public void pausePlaying() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getFramePosition();
            clip.stop();
        }
    }

    /**
     * Continues playing previously paused sound.
     * May raise exception if sound was not paused before by {@link #pausePlaying()}.
     */
    public void continuePlaying() {
        if (pausePosition == -1) {
            throw new RuntimeException("Sound is not paused!");
        }
        if (clip != null) {
            clip.setFramePosition(pausePosition);
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        }
    }

    /**
     * Stops playing this sound.
     * Note that the sound cannot be continued.
     * Consider using {@link #pausePlaying()} if you want this functionality.
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
