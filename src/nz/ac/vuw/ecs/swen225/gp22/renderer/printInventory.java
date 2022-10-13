package nz.ac.vuw.ecs.swen225.gp22.renderer;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import java.awt.Graphics;
import imgs.Img;
/**
 * 
 * 
 * @author Jack Grunfeld
 * @version 1.0
 * pritning map and enititys for chap to interract with
 * @param m
 * @param g
 */
 
public class printInventory {
    
    public static void printIn(Model m, Graphics g){
        int x = 585;
        int y = 253;
        
        for(int i = 0; i < 8; i++){
            java.awt.image.BufferedImage cur = Img.white.image;
            if(m.chap().inventory().size() > i){
                cur = m.chap().inventory().get(i);
            }
            g.drawImage(cur, x, y, 23, 24, null);
            
            //System.out.println("inventory: " + m.chap().inventory().size());

            if(x >= 663){
                x = 585;
                y += 28;
            }
            else{
                x += 30;
            } 
            
        }
        
        }

    
}
