package nz.ac.vuw.ecs.swen225.gp22.domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.KEYCOLOR;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.awt.image.BufferedImage;

import imgs.Img;

public class Chap implements Entity{

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


    public void up(){ move(location.up()); direction = DIRECTION.UP; }
    public void down(){ move(location.down()); direction = DIRECTION.DOWN; }
    public void left(){ move(location.left()); direction = DIRECTION.LEFT; }
    public void right(){ move(location.right()); direction = DIRECTION.RIGHT; }

    //Chap Methods

    public Chap(Point location){
        this.maze = null;
        this.location = location;
    }

    public int heldTreasure(){
        return heldTreasure;
    }

    public  Point getLocation(){ return location; }
    public DIRECTION getDirection(){ return direction; }
    public void setMaze(Maze m){ this.maze = m; }
    
    private boolean hasKey(KEYCOLOR color){
        return Stream.of(inventory).filter(t -> t!= null).filter(t -> t.color == color).count() >= 1;
    }
    
    public boolean useKey(KEYCOLOR color){
        if(hasKey(color)){
            removeFromInventory(color);
            return true;
        }
        else return false;
    }
    
    
    public void move(Point p){
        if(maze.getTile(p).interact(this, maze)){
            maze.getTile(this.location).removeEntity();
            maze.getTile(p).setEntity(this);
            location = p;
        }
        else{
            throw new Error("Chap cannot move to this tile.");
        }
    }
    
    public void pickUpTreasure(){
        heldTreasure += 1;
    }
    
    public void pickUpKey(Pickup.Key key){
        heldItems += 1;
        addToInventory(key);
    }
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
    private void addToInventory(Pickup.Key p){
        //Check if overwriting another item;
        if(inventory[heldItems] != null){
            throw new IllegalStateException("Overwriting Inventory Slot " + heldItems + ": " 
            + inventory[heldItems].toString() + " with " + p.toString()); }
        else{
            inventory[heldItems] = p;
        }
    }
    public List<Pickup.Key> inventory(){
        return Stream.of(inventory).filter(i->i != null).toList();
    }
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
