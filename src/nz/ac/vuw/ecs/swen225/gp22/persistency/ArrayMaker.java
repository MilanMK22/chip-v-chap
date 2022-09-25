package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.KEYCOLOR;

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
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                char c = chars[(i*h)+j];
                Point loc = new Point(j,i);
                switch (c) {
                    case 'W':
                        tiles[i][j] = Tile.wallTile(loc);
                        break;
                    case 'o':
                        tiles[i][j] = Tile.freeTile(loc);
                        break;
                    case 'b':
                        tiles[i][j] = Tile.keyTile(loc, KEYCOLOR.BLUE);
                        break;
                    case 'r':
                        tiles[i][j] = Tile.keyTile(loc, KEYCOLOR.RED);
                        break;
                    case 'g':
                        tiles[i][j] = Tile.keyTile(loc, KEYCOLOR.GREEN);
                        break;
                    case 'B':
                        tiles[i][j] = Tile.lockedDoorTile(loc, KEYCOLOR.BLUE);
                        break;
                    case 'R':
                        tiles[i][j] = Tile.lockedDoorTile(loc, KEYCOLOR.RED);
                        break;
                    case 'G':
                        tiles[i][j] = Tile.lockedDoorTile(loc, KEYCOLOR.GREEN);
                        break;
                    case 'i':
                        tiles[i][j] = Tile.infoTile(loc);
                        break;
                    case 't':
                        tiles[i][j] = Tile.treasureTile(loc);
                        break;
                    case 'l':
                        tiles[i][j] = Tile.exitLockTile(loc);
                        break;
                    case 'X':
                        tiles[i][j] = Tile.exitTile(loc);
                        break;
                    case 'C':
                        tiles[i][j] = Tile.freeTile(loc);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid tile char");
                        
                }  
         }
        }
        return tiles;
    }
}
