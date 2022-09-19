package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import java.awt.Color;

public class ArrayMaker {
    String board;
    int w,h;
    public ArrayMaker(String board, int w, int h) {
        this.board = board;
        this.w = w;
        this.h = h;
    }

    public Tile[][] makeArray(){
        Tile[][] tiles = new Tile[w][h];
        char[] chars = board.toCharArray();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                char c = chars[(i*h)+j];
                switch (c) {
                    case 'W':
                        tiles[i][j] = wallTile();
                        break;
                    case 'o':
                        tiles[i][j] = new FreeTile();
                        break;
                    case 'b':
                        tiles[i][j] = new KeyTile(Color.BLUE);
                        break;
                    case 'r':
                        tiles[i][j] = new KeyTile(Color.RED);
                        break;
                    case 'g':
                        tiles[i][j] = new KeyTile(Color.GREEN);
                        break;
                    case 'B':
                        tiles[i][j] = new LockedDoorTile(Color.BLUE);
                        break;
                    case 'R':
                        tiles[i][j] = new LockedDoorTile(Color.RED);
                        break;
                    case 'G':
                        tiles[i][j] = new LockedDoorTile(Color.GREEN);
                        break;
                    case 'i':
                        tiles[i][j] = new
                        break;
                    case 't':
                        tiles[i][j] = new
                        break;
                    case 'l':
                        tiles[i][j] = new
                        break;
                    case 'X':
                        tiles[i][j] = new
                        break;
                    case 'C':
                        tiles[i][j] = new
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid tile char");
                        break;
                }  
         }
        }
    }
}
