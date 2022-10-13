package sounds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Currency;

import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class sounds {
    public static enum SOUND{
        COLLECTCOIN,
        GAME,
        UNLOCK,
        NOPE,
        MENU;

        public final Clip clip;

        SOUND(){
            Clip c = null;
            try{
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/sounds/" + this.name().toLowerCase() + ".wav").toURI().toURL());
                c = AudioSystem.getClip();
                c.open(audioInputStream);
            }catch(Exception e){
                e.printStackTrace();
            }
            clip = c;
        }

        public void play(){
            if(clip.isRunning()){
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
        public void stop(){
            if(clip.isRunning()){
                clip.stop();
            }
        }
        public void loop(){
            Clip cur = clip;
            cur.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}




   