package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.util.HashMap;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;



public class Persistency {

    Map<Character, Tile> tileMap = new HashMap<Character, Tile>(){{
        put('W', new Tile());
    }};
    
    public Tile[][] makeArray(String s, int w, int h) {
        Tile[][] tiles = new Tile[w][h];
        for (int i = 0; i < s.toCharArray().length; i++) {
            char c = s.toCharArray()[i];

                tiles[i / w][i % w] = new Tile();

            switch (c) {
                case 'W':
                    
                    break;
                case 'o':
                    
                    break;
                case 'b':
                    
                    break;
                case 'r':
                    
                    break;
                case 'g':
                    
                    break;
                case 'B':
                    
                    break;
                case 'R':
                    
                    break;
                case 'G':
                    
                    break;
                case 'i':
                    
                    break;
                case 't':
                    
                    break;
                case 'l':
                    
                    break;
                case 'X':
                    
                    break;
                case 'C':
                    
                    break;

            
                default:
                    break;
            }
        }

    }
}
