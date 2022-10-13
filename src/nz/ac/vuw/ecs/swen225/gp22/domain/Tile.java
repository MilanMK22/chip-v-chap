package nz.ac.vuw.ecs.swen225.gp22.domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.KEYCOLOR;
import java.awt.image.BufferedImage;

/**
 * @author Leo Gaynor: 300437633
 * 
 * The Tile class is the Container for different Tile States.
 * 
 * As Chap interacts with each Tile, the Tile calls interact on the 
 * TileState, which determines the correct action to take.
 */
public class Tile{


    Point location;
    TileState state;
    public Entity entity;

    /**
     * For fuzz testing.
     */
    public boolean visited = false;

    /**
     * Create a Tile with a particular TileState.
     * @param tileState The TileState of the Tile.
     * @param location The Location of the Tile.
     */
    Tile(TileState tileState, Point location){
        this.state = tileState;
        this.location = location;
    }

    /**
     * Create a Tile with a particular TileState and an Entity.
     * @param entity The Entity that exists on the Tile.
     * @param tileState The TileState of the Tile.
     * @param location The Location of the Tile.
     */
    Tile (Entity entity, TileState tileState, Point location){
        this.state = tileState;
        this.entity = entity;
        this.location = location;
    }


    //Factory Patterns for TileStates
    static public Tile wallTile(Point location){ return new Tile(new WallTile(), location); }
    static public Tile freeTile(Point location){ return new Tile(new FreeTile(), location); }
    static public Tile keyTile(Point location, KEYCOLOR color){ return new Tile(new Pickup().new Key(location, color), new KeyTile(), location); }
    static public Tile treasureTile(Point location){ return new Tile(new Pickup().new Treasure(location), new TreasureTile(), location); }
    static public Tile lockedDoorTile(Point location, KEYCOLOR color){ return new Tile(new LockedDoorTile(color), location); }
    static public Tile infoTile(Point location){ return new Tile(new InfoTile(), location); }
    static public Tile exitLockTile(Point location){ return new Tile(new ExitLockTile(), location); }
    static public Tile exitTile(Point location){ return new Tile(new ExitTile(), location); }
    static public Tile chapTile(Point location){ return new Tile(new Chap(location), new FreeTile(), location); }
    static public Tile monsterTile(Point location, String moves){ return new Tile(new Monster(location, moves), new FreeTile(), location); }
    


    /**
     * Set the Entity of the Tile.
     * @param e The new Entity.
     */
    public void setEntity(Entity e){
        this.entity = e;
    }

    /**
     * Remove this tiles Entity.
     */
    public void removeEntity(){
        this.entity = null;
    }
    
    /**
     * Let Chap interact with this Tile's TileState.
     * @param c The Chap Object
     * @param m The Maze
     * @return Whether Chap can move to this Tile.
     */
    public boolean interact(Chap c, Maze m){
        return state.interact(this, c, m); 
    }

    /**
     * Set this Tile's State to a new state.
     * @param newState The new State.
     */
    public void setState(TileState newState){
        this.state = newState;
    }

    /**
     * Get this Tile's TileState.
     * @return The TileState.
     */
    public TileState getState(){
        return this.state;
    }

    //Util methods
    public Point getLocation(){ return this.location; }
    public boolean isFree(){ return state.isFree(); }
    public boolean isExit(){ return state.isExit(); }
    boolean hasPickup(){ return state.hasPickup(); }
    public boolean hasEntity(){ return entity != null; }
    public Entity getEntity(){ if (hasEntity()) return entity; else throw new Error("No Entity present"); }
    public BufferedImage getImage(){ return state.getImage(); }
    public char getChar(){ 
        if(hasEntity()){
            return entity.toChar();
        }
        return state.toChar();
    }

    /**
     * Fuzz Testing Method
     * @param status
     */
    public void visited(boolean status){
        this.visited = status;
    }
    /**
     * Fuzz Testing Method
     */
    public boolean getVisited(){
        return this.visited;
    }
}
