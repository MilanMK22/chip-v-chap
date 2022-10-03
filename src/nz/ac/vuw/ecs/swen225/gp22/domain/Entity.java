package nz.ac.vuw.ecs.swen225.gp22.domain;
import java.awt.image.BufferedImage;

import imgs.Img;

public interface Entity{
    Point getLocation();
    default boolean isTreasure(){ return false; }
    default boolean isChap(){ return false; }
    default boolean isPickup(){ return false; }
    default void tick(Maze m){ return; }
    default BufferedImage getImage(){return Img.coin.image; }
}
