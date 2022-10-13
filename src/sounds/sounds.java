package sounds;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * 
 * 
 * @author Jack Grunfeld
 * @version 1.0
 * playing sounds
 *
 */
 
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
        public void looping(){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}