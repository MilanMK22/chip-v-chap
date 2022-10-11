package nz.ac.vuw.ecs.swen225.gp22.domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.KEYCOLOR;
import java.awt.image.BufferedImage;

public class Tile{


    Point location;
    TileState state;
    public Entity entity;

    //ilya
    public boolean visited = false;

    Tile(TileState tileState, Point location){
        this.state = tileState;
        this.location = location;
    }


    Tile (Entity entity, TileState tileState, Point location){
        this.state = tileState;
        this.entity = entity;
        this.location = location;
    }


    //Factory Patterns
    static public Tile wallTile(Point location){ return new Tile(new WallTile(), location); }
    static public Tile freeTile(Point location){ return new Tile(new FreeTile(), location); }
    static public Tile keyTile(Point location, KEYCOLOR color){ return new Tile(new Pickup().new Key(location, color), new KeyTile(), location); }
    static public Tile treasureTile(Point location){ return new Tile(new Pickup().new Treasure(location), new TreasureTile(), location); }
    static public Tile lockedDoorTile(Point location, KEYCOLOR color){ return new Tile(new LockedDoorTile(color), location); }
    static public Tile infoTile(Point location){ return new Tile(new InfoTile(), location); }
    static public Tile exitLockTile(Point location){ return new Tile(new ExitLockTile(), location); }
    static public Tile exitTile(Point location){ return new Tile(new ExitTile(), location); }
    static public Tile chapTile(Point location){ return new Tile(new Chap(location), new FreeTile(), location); }



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
    public TileState getState(){
        return this.state;
    }

    //Util methods
    public Point getLocation(){ return this.location;}
    public boolean isFree(){ return state.isFree(); }
    boolean isExit(){ return state.isExit(); }
    boolean hasPickup(){ return state.hasPickup(); }
    boolean isDoor() { return state.isDoor(); }
    public boolean hasEntity(){ return entity != null; }
    public Entity getEntity(){ if (hasEntity()) return entity; else throw new Error("No Entity present"); }
    public BufferedImage getImage(){ return state.getImage(); }
    public char getChar(){ 
        if(hasEntity()){
            return entity.toChar();
        }
        return state.toChar();
    }

    //ilya
    public void visited(boolean status){
        this.visited = status;
    }
}
