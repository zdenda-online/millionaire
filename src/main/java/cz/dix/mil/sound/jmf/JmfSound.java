package cz.dix.mil.sound.jmf;

import cz.dix.mil.controller.ChainedAction;
import cz.dix.mil.sound.Sound;
import cz.dix.mil.util.Log;
import cz.dix.mil.util.LogFactory;

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
    private static final Log LOG = LogFactory.instance();

    private boolean isPlayable = true;
    private Clip clip;
    private int pausePosition = NOT_PAUSED_POSITION;
    private boolean isLooping = false;
    private boolean isStopped = false;

    public JmfSound(InputStream is) {
        if (is == null) {
            LOG.logError("Unable to load the sound input stream (file with sound is missing?)");
            isPlayable = false;
        }
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(is);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOG.logError("Unable to load the sound: " + e.getMessage());
            isPlayable = false;
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
        if (!isPlayable) {
            if (chainedAction != null) {
                chainedAction.execute();
            }
            return;
        }
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
                    chainedAction.execute();
                }
            }
        }).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pausePlaying() {
        if (!isPlayable) {
            return;
        }
        pausePosition = clip.getFramePosition();
        clip.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void continuePlaying() {
        if (!isPlayable) {
            return;
        }
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
        if (!isPlayable) {
            return;
        }
        isStopped = true;
        clip.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (!isPlayable) {
            return;
        }
        clip.close();
    }
}
