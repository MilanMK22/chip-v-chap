package nz.ac.vuw.ecs.swen225.gp22.renderer;

import imgs.Img;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Model;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Graphics;

public class Mapprint {

      
    public static void printMap(Model m, Graphics g){
       
        int x =-40;
        int y =0;
        for(int i = 0 ; i < m.getMaze().xlen; i++){
            for(int j = 0; j < m.getMaze().ylen; j++){
                 java.awt.image.BufferedImage cur = m.getMaze().getTiles()[i][j].state.getImage();
                 g.drawImage(cur, x, y, 40, 40, null);
                 x+=40;
                 if(x >= 440){
                    x = 0;
                    y+=40;
                    if(y > 440){
                        break;
                    }
                 }
            } 
        }
      
     }
}
