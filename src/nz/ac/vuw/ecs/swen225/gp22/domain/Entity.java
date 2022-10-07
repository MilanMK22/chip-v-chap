package nz.ac.vuw.ecs.swen225.gp22.domain;
import java.awt.image.BufferedImage;

import imgs.Img;


/**
 * A public interface for all Entities.
 * <p>
 * An entity is an object that can exist in the maze, and occupy a tile.
 * </p>
 * @see Chap
 * @see Pickup
 * @see Monster
 */
public interface Entity{
    Point getLocation();
    default boolean isTreasure(){ return false; }
    default boolean isChap(){ return false; }
    default boolean isPickup(){ return false; }
    default void tick(Maze m){ return; }
    default BufferedImage getImage(){return Img.coin.image; }
    default char toChar(){ return 'C'; }
}
