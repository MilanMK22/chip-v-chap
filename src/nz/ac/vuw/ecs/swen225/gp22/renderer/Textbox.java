package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//import font library
import java.awt.Font;
import imgs.Img;
import nz.ac.vuw.ecs.swen225.gp22.domain.Model;



//print a textbox on the screen with a message printable 
public class Textbox{
 



public static void printTextBox(String printable, int charIndex,  Graphics g) {  
    //print the textbox
  
    char characters[] =  printable.toCharArray();
    String combinedtxt ="";
    if(charIndex < characters.length){
        String s = String.valueOf(characters[charIndex]);
        combinedtxt = combinedtxt + s;
    }
 

   
    ImageIcon textbox = new ImageIcon(Img.textbox.image);
    Image textboxImage = textbox.getImage();
    g.drawImage(textboxImage, 67, 345, 380, 75, null);
    
    //print the message
    //Font textfont = createFont.font();
    JLabel message = new JLabel(combinedtxt);
    message.setFont(new Font("Arial", Font.PLAIN, 20));
    message.setForeground(Color.WHITE);
    message.setBounds(67, 350, 800, 100);
    
    




    }
}
