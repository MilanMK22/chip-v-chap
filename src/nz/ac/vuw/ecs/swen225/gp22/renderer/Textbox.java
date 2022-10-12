package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


//print a textbox on the screen with a message printable 
class textbox{


public JLabel printTextBox(String printable, Time t, Graphics g, int x, int y){  
    //print the textbox
    ImageIcon textbox = new ImageIcon("src/imgs/textbox.png");
    Image textboxImage = textbox.getImage();
    g.drawImage(textboxImage, 0, 0, 800, 100, null);
    
    //print the message
    JLabel message = new JLabel(printable);
    message.setForeground(Color.WHITE);
    message.setBounds(x, y, 800, 100);
    return message;
    




    }
}