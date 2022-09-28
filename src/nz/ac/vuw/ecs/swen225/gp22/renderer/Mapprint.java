package nz.ac.vuw.ecs.swen225.gp22.renderer;

import imgs.Img;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Model;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.text.html.parser.Entity;

import java.awt.Dimension;
import java.awt.Graphics;

public class Mapprint {

      
    public static void printMap(Model m, Graphics g){
       
        int x =-40;
        int y =0;
        for(int i = 0 ; i < m.getMaze().xlen; i++){
            for(int j = 0; j < m.getMaze().ylen; j++){
                 java.awt.image.BufferedImage cur = m.getMaze().getTiles()[i][j].getImage();
                 if(cur == Img.Marco.image){
                  Chap chap = new Chap(new Point(x, y));
                    cur = chap.getImage();
                    g.drawImage(Img.Marco.image, x, y, 42, 42, null);
                 }
                 g.drawImage(cur, x, y, 42, 42, null);
                 x+=42;
                 if(x >= 440){
                    x = 0;
                    y+=42;
                    if(y > 440){
                        break;
                    }
                 }
            } 
        }
      
     }
}
