package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import imgs.Img;

public class Board extends JFrame {


    public static JLabel getLevelLabel(int levelNum){
        var level = new JLabel("LEVEL "+ levelNum,SwingConstants.CENTER);
        level.setBounds(615, 75, 60, 30);
        return level;
    }
    public static JLabel getChipLabel(){
        var chips = new JLabel("5",SwingConstants.CENTER);
        chips.setBounds(615, 203,60, 30);
        return chips;
    }

    public static JLabel getInventory(){
        var inventory = new JLabel();
        inventory.setBounds(585,253,119,53);
        return inventory;
    }
    public static JLabel getBackgroundImage(){
        var backgroundImage = new JLabel();
        backgroundImage.setBounds(0,0,800,375);
        ImageIcon img = new ImageIcon(Img.fullmap.image);
        backgroundImage.setIcon(img);
        return backgroundImage;
    }

}
