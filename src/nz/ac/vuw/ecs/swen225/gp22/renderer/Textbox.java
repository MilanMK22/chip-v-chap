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
import nz.ac.vuw.ecs.swen225.gp22.app.ChipVsChap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Model;



//print a textbox on the screen with a message printable 
public class Textbox{
 



public static JLabel printTextBox(String printable, int charIndex,  Graphics g) {  
    //print the textbox
  
    char characters[] =  printable.toCharArray();
    String combinedtxt ="";
    if(charIndex < characters.length){
        String s = String.valueOf(characters[charIndex]);
        combinedtxt = combinedtxt + s;
    }

    System.out.println("test");
    ImageIcon textbox = new ImageIcon(Img.textbox.image);
    Image img = textbox.getImage();
    JLabel test = new JLabel();
    test.setBounds(200, 350, 500, 100);
    test.setIcon(textbox);
    
    //print the message
    //Font textfont = createFont.font();
    JLabel message = new JLabel("testing");
    message.setFont(new Font("Arial", Font.PLAIN, 20));
    test.add(message);

    return test;
    }
}
