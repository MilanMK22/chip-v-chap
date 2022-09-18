package nz.ac.vuw.ecs.swen225.gp22.domain;

enum KEYCOLOR {
    RED,
    GREEN,
    BLUE,
}

public class Pickup{


    class Treasure implements Entity{

        Point location;

        Treasure(Point loc){
            this.location = loc;
        }
        public boolean isKey(){ return true; }
        public boolean isChap(){ return false; }
        public boolean isTreasure() { return false; }
        public boolean isPickup() { return true; }
        public Point getLocation() { return this.location;}
    }

    class Key implements Entity{
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
    }
}

