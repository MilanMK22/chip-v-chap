package nz.ac.vuw.ecs.swen225.gp22.renderer;

import imgs.Img;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;

import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 * 
 * @author Jack Grunfeld
 * @version 1.0
 * pritning map and enititys for chap to interract with
 * 
 */
public class Mapprint {

      /**
       * 
       * @param m
       * @param g
       */
    public static void printMap(Model m, Graphics g){
        int vari = 1;
        int x =-42;
        int y =-42;
        int size = 43;
       
        
        //for loop for pritnig map
        
        for(int i = 0 ; i < m.getMaze().xlen; i++){
            for(int j = 0; j < m.getMaze().ylen; j++){
                //getting tile image from domain and pritning it
                java.awt.image.BufferedImage cur = m.getMaze().getTiles()[i][j].getImage();
                 g.drawImage(Img.floor_tiles.image, x, y, 42, 42, null);
                 g.drawImage(cur, x, y, size, size, null);
                 size = 42;
               
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
        //geting entitys from domain and pritning them
        for(Entity e: m.entities()){
        g.drawImage(e.getImage(), (e.getLocation().getX() * 42)-42, (e.getLocation().getY() * 42)-42, 40,40, null);
        vari = 42;
     }
 
  }
}
