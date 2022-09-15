package nz.ac.vuw.ecs.swen225.gp22.renderer;

import imgs.Img;

public class Mapprint {

      
    public static void printMap(Maze maze, Graphics2D g2d, int width, int height){
     
        //get maze dimensions
        int x = maze.getTiles().length;
        int y = maze.getTiles()[0].length;
        //get tile size
        int tileWidth = width/x;
        int tileHeight = height/y;
        //print tiles
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                //get tile
                Tile tile = maze.getTile(i, j);
                //get tile type
                String type = tile.getType();
                //get tile position
                int xPos = i*tileWidth;
                int yPos = j*tileHeight;
                //set tile colour
                switch(type){
                    case "wall":
                       
                        break;
                    case "floor":
                       
                        break;
                    case "exit":
                       
                        break;
                    case "key":
                        
                        break;
                    case "door":
                       
                        break;
                    case "chips":
                       
                        break;
                    case "player":
                       
                        break;
                    default:
                        
                        break;
                }
                //draw tile
                g2d.fillRect(xPos, yPos, tileWidth, tileHeight);
            }
        }
    }
    

}
