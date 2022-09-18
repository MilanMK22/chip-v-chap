package nz.ac.vuw.ecs.swen225.gp22.domain;

public class Tile{


    Point location;
    TileState state;
    Entity entity;

    Tile(TileState tileState, Point location){
        this.state = tileState;
        this.location = location;
    }


    Tile (Entity entity, TileState tileState, Point location){
        this.state = tileState;
        this.entity = entity;
        this.location = location;
    }
    public void setEntity(Entity e){
        this.entity = e;
    }
    public void removeEntity(){
        this.entity = null;
    }
    
    public boolean interact(Chap c, Maze m){
        return state.interact(this, c, m); 
    }

    public void setState(TileState newState){
        this.state = newState;
    }


    //Util methods
    Point getLocation(){ return this.location;}
    boolean isFree(){ return state.isFree(); }
    boolean isExit(){ return state.isExit(); }
    boolean hasPickup(){ return state.hasPickup(); }
    boolean isDoor() { return state.isDoor(); }
    boolean hasEntity(){ return entity != null; }
    Entity getEntity(){ if (hasEntity()) return entity; else throw new Error("No Entity present"); }

}
