package sounds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class sounds {
    public Clip clip;
    AudioInputStream sound;
    public void setFile(String soundFileName) {
        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
        }
    }
    public void play() {
        clip.start();
    }
    public void stop(){
        clip.close();
        clip.stop();
    }
}