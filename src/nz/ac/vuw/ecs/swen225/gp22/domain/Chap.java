package nz.ac.vuw.ecs.swen225.gp22.domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.KEYCOLOR;
import sounds.sounds;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

import java.awt.image.BufferedImage;

import imgs.Img;


/**
 * @author Leo Gaynor: 300437633
 * 
 */
public class Chap implements Entity{

    /**
     * public enum for Chap's currently facing direction.
     */
    public enum DIRECTION{
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    private Maze maze;
    private Point location;
    private static Pickup.Key[] inventory = new Pickup.Key[8];
    private static int heldItems = 0;
    private static int heldTreasure = 0;
    private DIRECTION direction = DIRECTION.DOWN;
    
    //Entity Methods
    public boolean isChap(){ return true; }
    public boolean isPickup() { return false; }
    public sounds s = new sounds();
    
    public void up(){ move(location.up()); direction = DIRECTION.UP; }
    public void down(){ move(location.down()); direction = DIRECTION.DOWN; }
    public void left(){ move(location.left()); direction = DIRECTION.LEFT; }
    public void right(){ move(location.right()); direction = DIRECTION.RIGHT; }

    //Chap Methods
    /**
     * Constructor for Chap class.
     * @param location
     */
    public Chap(Point location){
        this.maze = null;
        this.location = location;
    }

    /** 
     * Get chaps held treausre.
     * 
     * @return int
     */
    public int heldTreasure(){
        return heldTreasure;
    }

    public  Point getLocation(){ return location; }
    public DIRECTION getDirection(){ return direction; }
    
    /** 
     * Set Chap's internal logic to work with a specific maze.
     * 
     * @param maze
     */
    public void setMaze(Maze maze){ this.maze = maze; }
    
    /**
     * checks if Chap has picked up a key of a particular color.
     * @param color A {@link #Pickup.KEYCOLOR KEYCOLOR} enum, to be checked against Chap's currently held 
     * {@link #Pickup.Key Keys} (Stored in {@link #inventory})
     * @return A boolean of the result.
     * @see Pickup.Key
     * @see Pickup.KEYCOLOR
     */
    private boolean hasKey(KEYCOLOR color){
        return Stream.of(inventory).filter(t -> t!= null).filter(t -> t.color == color).count() >= 1;
    }
    
    /**
     * Use a {@link #Pickup.Key Key} from Chap's {@link #inventory}.
     * @param color The Key Color to be used from Chap's {@link #inventory}
     * @return a {@code boolean} based on if the key was used or not.
     * @see LockedDoorTile
     */
    public boolean useKey(KEYCOLOR color){
        if(hasKey(color)){
            s.setFile("src/sounds/unlock.wav");
            s.play();
            removeFromInventory(color);
            return true;
        }
        else return false;
    }
    
    /**
     * Move Chap to a particular point, utilizing the interact() method.
     * @param p The Point to move to.
     */
    public void move(Point p){
        if(maze.getTile(p).interact(this, maze)){
            maze.getTile(this.location).removeEntity();
            maze.getTile(p).setEntity(this);
            location = p;
            s.setFile("src/sounds/nope.wav");

        }
        else{
            s.play();
            throw new Error("Chap cannot move to this tile." );
        }
    }
    

    /**
     * Increment {@link #heldTreasure} by one.
     */
    public void pickUpTreasure(){
        s.setFile("src/sounds/collectcoin.wav");
        s.play();
        heldTreasure += 1;
    }
    
    /**
     * Increment {@link #heldItems} by one, and add a {@code Pickup.Key} to {@link #inventory}.
     * @param key The {@code Pickup.Key} to add.
     */
    public void pickUpKey(Pickup.Key key){
        s.setFile("src/sounds/collectcoin.wav");
        s.play();
        heldItems += 1;
        addToInventory(key);
    }

    /**
     * Remove the indicated {@code Pickup.Key} from Chap's inventory.
     * @param color
     */
    private void removeFromInventory(KEYCOLOR color){
        OptionalInt keyPos = IntStream
        .range(0,inventory.length)
        .filter(x -> inventory[x] != null && inventory[x].color.equals(color))
        .findFirst();
        if(keyPos.isPresent()){
            inventory[keyPos.getAsInt()] = null;
            heldItems -= 1;
        }
        else{
            throw new IllegalArgumentException("No key of type " + color + " present in inventory: \n" + inventory.toString());
        }
    }

    /**
     * Add the passed {@code Pickup.Key} to Chap's inventory.
     * @param p The {@code Pickup.Key} to be added.
     */
    private void addToInventory(Pickup.Key p){
        //Check if overwriting another item.
        if(inventory[heldItems] != null){
            throw new IllegalStateException("Overwriting Inventory Slot " + heldItems + ": " 
            + inventory[heldItems].toString() + " with " + p.toString()); }
        //Add the item.
        else{
            inventory[heldItems] = p;
        }
    }


    /**
     * Return a list of the items in Chap's inventory.
     * 
     * @return A {@code List<Pickup.Key>} representing Chap's inventory.
     */
    public List<Pickup.Key> inventory(){
        return Stream.of(inventory).filter(i->i != null).toList();
    }


    /**
     * Get the corresponding Image to Chap's current Direction.
     * @return a {@code BufferedImage} of Chap's current Direction.
     */
    public BufferedImage getImage(){
        switch(this.direction){
            case DOWN: return Img.Marco.image;
            case UP: return Img.Marco.image;
            case LEFT: return Img.Marco.image;
            case RIGHT: return Img.Marco.image;
            default: return Img.Marco.image;
        }
   
    

    }
}
