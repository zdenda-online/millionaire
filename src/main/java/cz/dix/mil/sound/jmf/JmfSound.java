package cz.dix.mil.sound.jmf;

import cz.dix.mil.controller.ChainedAction;
import cz.dix.mil.sound.Sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation that uses Java Media Framework for playing the sound.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class JmfSound implements Sound {

    private final InputStream is;
    private Clip clip;
    private long lengthMillis;
    private int pausePosition = -1;
    private boolean isLooping = false;

    public JmfSound(InputStream is) {
        if (is == null) {
            throw new IllegalArgumentException("Given input stream is null!");
        }
        this.is = is;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play() {
        play(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playLooped() {
        play(true);
    }

    private void play(boolean isLooping) {
        this.isLooping = isLooping;
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(is);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            lengthMillis = clip.getMicrosecondLength() / 1000;
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        } finally {
            if (audioIn != null) {
                try {
                    audioIn.close();
                } catch (IOException e) {
                    e.printStackTrace(); // should not happen
                }
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final ChainedAction chainedAction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                play();
                try {
                    Thread.sleep(lengthMillis);
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
     * {@inheritDoc}
     */
    @Override
    public void pausePlaying() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getFramePosition();
            clip.stop();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
