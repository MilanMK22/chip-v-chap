package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

public class TestFunct {
    public static void main(String[] args) {
        try {
            Tile[][] readTiles = Persistency.readXML("level1");
            Tile[][] readTiles2 = Persistency.readXML("level1");
            System.out.println(readTiles.length);
            System.out.println(readTiles[0].length);
            System.out.println();
           
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("done :)");
    }
}
