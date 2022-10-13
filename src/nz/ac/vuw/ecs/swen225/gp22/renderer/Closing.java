package nz.ac.vuw.ecs.swen225.gp22.renderer;
import java.awt.Color;
import java.awt.Image;
//import graphics
//include Img file
import imgs.Img;

import java.awt.Graphics;
import javax.swing.ImageIcon;
public class Closing {
    

    public void drawClose(Graphics g, int i){
        int x = 0;
        int y = 0;
        ImageIcon winning = new ImageIcon(Img.win.image);
        Image closeImage = winning.getImage();
        g.drawRect(0, 0, 800, 450);
                g.drawImage(closeImage, x, i, 800, 450, null);
               
            
    }
}
        
