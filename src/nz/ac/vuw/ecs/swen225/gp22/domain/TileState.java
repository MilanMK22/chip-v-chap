package nz.ac.vuw.ecs.swen225.gp22.domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.KEYCOLOR;

public interface TileState{

    default boolean isFree(){ return false; }
    default boolean isExit(){ return false; }
    default boolean hasPickup(){ return false; }
    default boolean isDoor() { return false; }
    boolean interact(Tile t, Chap c, Maze m);
}

class FreeTile implements TileState {

    public FreeTile(){

    }

    @Override
    public boolean isFree(){ return true; }

    public boolean interact(Tile t, Chap c, Maze m){
        return true;
    }


}

class WallTile implements TileState {

    public WallTile(){

    }
    public boolean interact(Tile t, Chap c, Maze m){
        return false;       
    }

}

class KeyTile implements TileState {;

    public KeyTile(){}
    
    @Override
    public boolean isFree(){ return true; }
    @Override
    public boolean hasPickup(){ return true; }

    public boolean interact(Tile t, Chap c, Maze m){
        if(t.getEntity() instanceof Pickup.Key){
            c.pickUpKey((Pickup.Key)t.getEntity());
        }
        t.setState(new FreeTile());
        return true;
    }
}

class LockedDoorTile implements TileState {
    KEYCOLOR color;
    boolean locked;
    LockedDoorTile(KEYCOLOR color){
        this.color = color;
    }

    public boolean interact(Tile t, Chap c, Maze m){
            if(c.useKey(this.color)){
                t.setState(new FreeTile());
                return true;
            }
            else{
                return false;
            }
    }

    @Override
    public boolean isDoor(){ return true; }

}

class InfoTile implements TileState{
    @Override
    public boolean isFree() { return true; }
    @Override
    public boolean interact(Tile t, Chap c, Maze m) {
        return true;
    }
    
}

class TreasureTile implements TileState{

    @Override
    public boolean isFree() { return true; }

    @Override
    public boolean interact(Tile t, Chap c, Maze m){
        if(t.getEntity() instanceof Pickup.Treasure){
            c.pickUpTreasure();
            t.setState(new FreeTile());
            return true;
        }
        else throw new Error("No treasure on this treasure tile!");
    }
}

class ExitLockTile implements TileState{
    @Override
    public boolean interact(Tile t, Chap c, Maze m){
        if(m.totalTreasure() == c.heldTreasure()){
            t.setState(new FreeTile());
            return true;
        }
        else return false;
    }
}

class ExitTile implements TileState{


    @Override
    public boolean isExit() { return true; }
    @Override
    public boolean isFree() { return true; }
    @Override
    public boolean interact(Tile t, Chap c, Maze m) {
        return true;
    }
    
}