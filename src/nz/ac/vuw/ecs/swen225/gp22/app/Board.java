
package nz.ac.vuw.ecs.swen225.gp22.app;
import imgs.Img;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.*;



public class Board extends JFrame {
    public static JLabel getLevelLabel(int levelNum){
        var level = new JLabel("LEVEL "+ levelNum,SwingConstants.CENTER);
        level.setBounds(613, 77, 60, 30);
        return level;
    }
    public static JLabel getChipLabel(int numOfChips){
        var chips = new JLabel(Integer.toString(numOfChips),SwingConstants.CENTER);
        chips.setBounds(615, 203,60, 30);
        return chips;
    }
    public static JLabel getInventory(){
        var inventory = new JLabel();
        inventory.setBounds(585,150,119,53);
        return inventory;
    }
    public static JLabel getBackgroundImage(){
        var backgroundImage = new JLabel();
        backgroundImage.setBounds(0,0,800,425);
        ImageIcon img = new ImageIcon(Img.fullmap.image);
        backgroundImage.setIcon(img);
        return backgroundImage;
    }
    public static JDialog getPause(){
        JOptionPane pane = new JOptionPane("Paused", JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Paused");
        dialog.setModal(false);
        dialog.setVisible(false);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        return dialog;
    }
    public static JDialog getTimeout(){
        JOptionPane pane = new JOptionPane("Time Out", JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Time Out");
        dialog.setModal(false);
        dialog.setVisible(false);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        return dialog;
    }
    public static JLabel getInfo(){
        JLabel info = new JLabel();
        info.setBounds(55, 350,400, 60);
        info.setOpaque(true);
        info.setVisible(false);
        Image img = new ImageIcon(Img.textbox.image).getImage().getScaledInstance(info.getWidth(), info.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon textbox = new ImageIcon(img);
        info.setIcon(textbox);
        return info;
    }

    public static JLabel getInfoText(String infoString){
        JLabel infoText = new JLabel(infoString,SwingConstants.CENTER);
        infoText.setBounds(0,20,400,20);
        return infoText;
    }
}
