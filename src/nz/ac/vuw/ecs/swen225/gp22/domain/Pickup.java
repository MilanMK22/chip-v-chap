package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.awt.image.BufferedImage;
import imgs.Img;

public class Pickup{

    
    public enum KEYCOLOR {
    RED(Img.red_key.image, Img.red_lock.image),
    GREEN(Img.green_key.image,Img.green_lock.image),
    BLUE(Img.blue_key.image, Img.blue_lock.image),
    YELLOW(Img.yellow_key.image, Img.yellow_lock.image);

    public final BufferedImage key;
    public final BufferedImage lock;
    
    KEYCOLOR(BufferedImage key, BufferedImage lock){
        this.key = key;
        this.lock = lock;
    }
    public BufferedImage key(){ return this.key; }
    public BufferedImage lock(){ return this.lock; }
    }

    class Treasure implements Entity{

        Point location;

        Treasure(Point loc){
            this.location = loc;
        }
        public BufferedImage getImage(){ return Img.coin.image; }
        public boolean isKey(){ return true; }
        public boolean isChap(){ return false; }
        public boolean isTreasure() { return false; }
        public boolean isPickup() { return true; }
        public Point getLocation() { return this.location;}
    }

    public class Key implements Entity{
        KEYCOLOR color;
        Point location;
        Key(Point loc, KEYCOLOR col){
            color = col;
            location = loc;
        }
        public BufferedImage getImage(){ return color.key(); }
        public boolean isKey(){ return true; }
        public boolean isChap(){ return false; }
        public boolean isTreasure() { return false; }
        public boolean isPickup() { return true; }
        public Point getLocation() { return this.location;}
    }
}

