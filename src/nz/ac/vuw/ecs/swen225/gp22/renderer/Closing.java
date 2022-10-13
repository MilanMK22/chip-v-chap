package nz.ac.vuw.ecs.swen225.gp22.renderer;
import java.awt.Color;
import java.awt.Image;
//import graphics
//include Img file
import imgs.Img;

import java.awt.Graphics;
import javax.swing.ImageIcon;
public class Closing {
    

    public static void drawClose(Graphics g, int i) throws InterruptedException{
        int x = 0;
        int y = 0;
        while(i < 450){
        ImageIcon winning = new ImageIcon(Img.win.image);
        Image closeImage = winning.getImage();
        g.drawImage(closeImage, x, i, 800, 450, null);
        i++;
       // g.wait(100);
        } 
    }
}
        
