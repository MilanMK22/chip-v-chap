package nz.ac.vuw.ecs.swen225.gp22.domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.KEYCOLOR;
import sounds.sounds;
import sounds.sounds.SOUND;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import java.awt.image.BufferedImage;

import imgs.Img;


/**
 * @author Leo Gaynor: 300437633
 * 
 * The Chap class represents the internal state of Chap. 
 * 
 * The class provides methods for utility and moving the Chap object through the maze. 
 * 
 * It has extensive built in movement and interaction safety, preventing IllegalStateExceptions and IllegalArgumentExceptions.
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
    private Pickup.Key[] inventory = new Pickup.Key[8];
    private int heldItems = 0;
    private int heldTreasure = 0;
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
            SOUND.UNLOCK.play();
           
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
        }
        else{
            SOUND.NOPE.play();
            assert !(maze.getTile(location).getState() instanceof WallTile);
        }
    }
    

    /**
     * Increment {@link #heldTreasure} by one.
     */
    public void pickUpTreasure(){
        heldTreasure += 1;
        SOUND.COLLECTCOIN.play();
    }
    
    /**
     * Increment {@link #heldItems} by one, and add a {@code Pickup.Key} to {@link #inventory}.
     * @param key The {@code Pickup.Key} to add.
     */
    public void pickUpKey(Pickup.Key key){
        addToInventory(key);
        heldItems += 1;
        SOUND.COLLECTCOIN.play();
    }

    /**
     * Remove the indicated {@code Pickup.Key} from Chap's inventory.
     * @param color
     */
    private void removeFromInventory(KEYCOLOR color){




        OptionalInt keyPos = IntStream
        .range(0, inventory.length)
        .filter(x -> inventory[x] != null && inventory[x].color.equals(color)).findFirst();
        if(keyPos.isPresent()){
            inventory[keyPos.getAsInt()] = null;
            inventory = (Pickup.Key[])Arrays.copyOf(
                Stream.of(inventory)
                .filter(e->e!=null)
                .toArray(), 8, Pickup.Key[].class);
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
     * Return a list of the item images in Chap's inventory.
     * 
     * @return A {@code List<BufferedImage>} representing Chap's inventory.
     */
    public List<BufferedImage> inventory(){
        return Stream.of(inventory).filter(i->i != null).map(e->e.getImage()).toList();
    }

    public Pickup.Key[] getInvKeys(){
        return inventory.clone();
    }

    /**
     * Ovewrite chaps inventory
     * 
     * @param inventory
     */
    public void setInventory(Pickup.Key[] inventory){
        for(Pickup.Key k : inventory){
            if(k!=null) pickUpKey(k);
        }
        
    }


    /**
     * Get the corresponding Image to Chap's current Direction.
     * @return a {@code BufferedImage} of Chap's current Direction.
     */
    public BufferedImage getImage(){
        switch(this.direction){
            case DOWN: return Img.Marco.image;
            case UP: return Img.MarcoBack.image;
            case LEFT: return Img.MarcoL.image;
            case RIGHT: return Img.MarcoR.image;
            default: return Img.Marco.image;
        }
   
    

    }



    @Override
    public String toString(){
        String ret = "Chap at: " + this.location.toString() + "\n" +
        "Chap Collected: \n   Keys \tTreasure \n" + "    " +
        (maze.totalKeys - maze.keyCount) + "/" + maze.keyCount + "\t" + "          " + 
        (maze.totalTreasure - heldTreasure) + "/" + maze.totalTreasure + "\n" + "Inventory:\n";

        //inventory to string
        for(Pickup.Key k: inventory){
            if(k!= null){
                ret += k.toString() + "\n";
            }
        }

        return ret;

    }
}
