package nz.ac.vuw.ecs.swen225.gp22.domain;
import java.awt.image.BufferedImage;

public interface Entity{
    Point getLocation();
    default boolean isTreasure(){ return false; }
    default boolean isChap(){ return false; }
    default boolean isPickup(){ return false; }
    default void tick(){ return; }
    BufferedImage getImage();
}
