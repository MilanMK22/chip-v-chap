package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.awt.image.BufferedImage;
import imgs.Img;


/**
 * @author Leo Gaynor: 300437633
 * 
 * The Pickup Class is the Outer Class of Entities that chap can pick up.
 * 
 * These are Treasure and Keys.
 */
public class Pickup{

    /**
     * The KEYCOLOR enum is used to track the correct usage of keys and locked doors.
     * 
     * It also provides methods for dynamically getting The correct Image files for Rendering and
     * the correct Characters for saving. 
     */
    public enum KEYCOLOR {
    RED(Img.red_key.image, Img.red_lock.image, 'r'),
    GREEN(Img.green_key.image,Img.green_lock.image, 'g'),
    BLUE(Img.blue_key.image, Img.blue_lock.image, 'b'),
    YELLOW(Img.yellow_key.image, Img.yellow_lock.image, 'y');

    public final BufferedImage key;
    public final BufferedImage lock;
    public final char string;
    
    KEYCOLOR(BufferedImage key, BufferedImage lock, char string){
        this.key = key;
        this.lock = lock;
        this.string = string;
    }
    /**
     * Get the Key image that relates to this Color. 
     * @return The BufferedImage of the correct Color.
     */
    public BufferedImage key(){ return this.key; }
    /**
     * Get the Lock image that relates to this Color. 
     * @return The BufferedImage of the correct Color.
     */
    public BufferedImage lock(){ return this.lock; }
    /**
     * Get the Character that relates to this Colors Key. 
     * @return The Character of the correct Color.
     */
    public char keyChar(){ return this.string; }
    /**
     * Get the Character that relates to this Colors Locked Door. 
     * @return The Character of the correct Color.
     */
    public char lockChar(){ return Character.toUpperCase(this.string); }
    }


    /**
     * The Treasure Class is a marker for Treasure (Coins) that Chap can interact with to pick up.
     */
    class Treasure implements Entity{

        Point location;

        Treasure(Point loc){
            this.location = loc;
        }
        public boolean isKey(){ return false; }
        public boolean isChap(){ return false; }
        public boolean isTreasure() { return true; }
        public boolean isPickup() { return true; }
        public Point getLocation() { return this.location;}
        public BufferedImage getImage(){ return Img.coin.image; }
        public char toChar(){ return 't'; }
    }

    /**
     * The Key Class is a marker for Keys that Chap can interact with to pick up and use to unlock doors.
     */
    public class Key implements Entity{
        KEYCOLOR color;
        Point location;
        public Key(Point loc, KEYCOLOR col){
            color = col;
            location = loc;
        }
        public boolean isKey(){ return true; }
        public boolean isChap(){ return false; }
        public boolean isTreasure() { return false; }
        public boolean isPickup() { return true; }
        public Point getLocation() { return this.location;}
        public BufferedImage getImage(){ return color.key(); }
        public char toChar(){ return color.keyChar(); }
        public String toString(){ return color.name();}
    }
}

