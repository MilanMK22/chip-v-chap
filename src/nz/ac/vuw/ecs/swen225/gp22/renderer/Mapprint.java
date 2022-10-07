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
        int MapDim = m.getMaze().xlen;
       

        int x = 0;
        int y = 0;
        int size = 42;

        Point chaploc = m.chap().getLocation();
        int chapx = chaploc.getX();
        int chapy = chaploc.getY();
        System.out.println("chap loc= " + chapx + " ," + chapy);
        // for loop for pritnig map

        for(int i = chapy - 4; i < chapy + 4; i++) {
           for(int j = chapx - 5; j <= chapx + 5; j++) {
            //for (int i = chapx-4; i < chapx + 4; i++) {
               // for (int j = chapy-4; j <= chapy +4; j++) {

                // getting tile image from domain and pritning it
                if (i < 0 || j < 0 || i >= m.getMaze().xlen || j >= m.getMaze().ylen) {
                    g.drawImage(Img.walls.image, x, y, size, size, null);
                } else {
                    java.awt.image.BufferedImage cur = m.getMaze().getTile(j,i).getImage();

                    g.drawImage(Img.floor_tiles.image, x, y, size, size, null);
                    g.drawImage(cur, x, y, size, size, null);

                    if (m.getMaze().getTile(j,i).hasEntity()) {
                        if ((m.getMaze().getTile(j,i).getEntity() != m.chap())) {
                            g.drawImage(m.getMaze().getTile(j,i).getEntity().getImage(), x, y, size, size, null);
                        }
                    }
                }
                x += size;
                if (x >= m.getMaze().xlen * size) {
                    x = 0;
                    y += size;
                    if (y >= m.getMaze().ylen * size) {
                        break;
                    }
                }
            }
        }
        // geting entitys from domain and pritning them
        for (nz.ac.vuw.ecs.swen225.gp22.domain.Entity e : m.entities()) {
            if (e == m.chap() ) {
                g.drawImage(e.getImage(), 5 * size, 4 * size, size, size, null);
            }
        }
        

    }
}
