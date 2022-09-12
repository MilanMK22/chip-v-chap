package nz.ac.vuw.ecs.swen225.gp22.domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup;

interface Tile{

}


abstract class AbstractTile implements Tile {
    boolean isFree(){ return false; }
    boolean isExit(){ return false; }
    boolean hasPickup(){ return false; }
    boolean isDoor() { return false; }
}


class FreeTile extends AbstractTile {
    @Override
    boolean isFree(){ return true; }
}

class WallTile extends AbstractTile {

}

class KeyTile extends AbstractTile {
    Pickup key;

    KeyTile(Pickup key){
        this.key = key;
    }
    
    @Override
    boolean isFree(){ return true; }
    @Override
    boolean hasPickup(){ return true; }

    Pickup pickUpKey() {
        return key;
    }
}

class LockedDoorTile extends AbstractTile {
    KEYCOLOR color;
    boolean locked;
    LockedDoorTile(KEYCOLOR color){
        this.color = color;
        this.locked = true;
    }

    @Override
    boolean isDoor(){ return true; }
    @Override
    boolean isFree(){ return !locked; }

}