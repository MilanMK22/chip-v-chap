package nz.ac.vuw.ecs.swen225.gp22.domain;

import imgs.Img;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.KEYCOLOR;
import java.awt.image.BufferedImage;

public interface TileState{

    default boolean isFree(){ return false; }
    default boolean isExit(){ return false; }
    default boolean hasPickup(){ return false; }
    default boolean isDoor() { return false; }
    boolean interact(Tile t, Chap c, Maze m);
    public BufferedImage getImage();
    default public char toChar(){ return 'o';}
}

class FreeTile implements TileState {

    public FreeTile(){

    }

    @Override
    public boolean isFree(){ return true; }

    public boolean interact(Tile t, Chap c, Maze m){
        if(t.hasEntity()){
            return !(t.getEntity() instanceof Monster);
        }
        return true;
    }
    @Override
    public BufferedImage getImage() {
        return Img.floor_tiles.image;
    }

}

class WallTile implements TileState {

    public WallTile(){

    }
    public boolean interact(Tile t, Chap c, Maze m){
        return false;       
    }
    @Override
    public BufferedImage getImage() {
        return Img.walls.image;
    }
    public char toChar(){
        return 'W';
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
    @Override
    public BufferedImage getImage() {
        return Img.floor_tiles.image;
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
    @Override
    public BufferedImage getImage() {
        return color.lock();
    }
    public char toChar(){
        return color.lockChar();
    }

}

class InfoTile implements TileState{
    @Override
    public boolean isFree() { return true; }
    @Override
    public boolean interact(Tile t, Chap c, Maze m) {
        return true;
    }
    public BufferedImage getImage() {
        return Img.info.image;
    }
    public char toChar(){
        return 'i';
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
        else throw new IllegalStateException("No treasure on this treasure tile!");
    }
    public BufferedImage getImage() {
        return Img.floor_tiles.image;
    }
    public char toChar(){
        return 't';
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
    public BufferedImage getImage() {
        return Img.exitlock.image;
    }
    public char toChar(){
        return 'l';
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
    
    public BufferedImage getImage() {
        return Img.exit.image;
    }
    public char toChar(){
        return 'X';
    }
}