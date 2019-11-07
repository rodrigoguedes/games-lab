package dev.rodrigoguedes.game.zelda.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class Sound {

//    private AudioClip clip;

    public static final Sound backgroundGame = new Sound("/zelda/zelda.wav");

    private Clip clip;

    private Sound(String path) {
        try {
            //https://www.media.io/
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(path));
            this.clip = AudioSystem.getClip();
            this.clip.open(audioIn);
        } catch (Exception exc) {
            exc.printStackTrace();
            exc.printStackTrace(System.out);
        }
    }

    public void play() {
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
