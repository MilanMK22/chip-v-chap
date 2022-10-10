package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.awt.image.BufferedImage;
import imgs.Img;

public class Pickup{

    
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
    public BufferedImage key(){ return this.key; }
    public BufferedImage lock(){ return this.lock; }
    public char keyChar(){ return this.string; }
    public char lockChar(){ return Character.toUpperCase(this.string); }
    }

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

    public class Key implements Entity{
        KEYCOLOR color;
        Point location;
        Key(Point loc, KEYCOLOR col){
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

