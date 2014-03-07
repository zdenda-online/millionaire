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

    private static final int NOT_PAUSED_POSITION = -1;

    private Clip clip;
    private int pausePosition = NOT_PAUSED_POSITION;
    private boolean isLooping = false;
    private boolean isStopped = false;

    public JmfSound(InputStream is) {
        if (is == null) {
            throw new IllegalArgumentException("Given input stream is null!");
        }
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(is);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
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
    public void play() {
        play(false, null, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playLooped() {
        play(true, null, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final ChainedAction chainedAction) {
        play(false, chainedAction, 0);
    }

    private void play(final boolean isLooping, final ChainedAction chainedAction, int playFrom) {
        this.isLooping = isLooping;
        this.isStopped = false;
        this.pausePosition = NOT_PAUSED_POSITION;

        clip.flush();
        clip.setFramePosition(playFrom);

        new Thread(new Runnable() {
            @Override
            public void run() {
                long lengthMillis = clip.getMicrosecondLength() / 1000;
                if (isLooping) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    clip.start();
                }
                if (chainedAction != null) {
                    try {
                        Thread.sleep(lengthMillis);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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
        pausePosition = clip.getFramePosition();
        clip.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void continuePlaying() {
        if (pausePosition == NOT_PAUSED_POSITION) {
            throw new RuntimeException("Sound is not paused!");
        }
        if (isStopped) {
            throw new RuntimeException("Sound is stopped and cannot be continued, use play instead");
        }
        play(isLooping, null, pausePosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        isStopped = true;
        clip.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        clip.close();
    }
}
