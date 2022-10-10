package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

public class TestFunct {
    public static void main(String[] args) {
        try {
            //Tile[][] readTile1s = Persistency.readXML("level1");
            Tile[][] readTiles = Persistency.readXML("level1");
            Tile[][] read2Tiles = Persistency.readXML("level2");

            System.out.println(readTiles.length);
            System.out.println(readTiles[0].length);
            System.out.println();
            System.out.println(read2Tiles.length);
            System.out.println(read2Tiles[0].length);
            System.out.println();
            // Tile t = readTiles[0][0];
            // String s = t.getState().toString();
            // String ss = s.substring(34, s.length() - 9);
            // System.out.println(ss);
            Persistency.createPXML(readTiles);
           
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("caught");
        }

        System.out.println("done :)");
    }
}
