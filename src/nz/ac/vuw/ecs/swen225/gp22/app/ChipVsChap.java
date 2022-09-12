package src.app;

import java.awt.*;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * Game components will run from this class.
 */
public class ChipVsChap extends JFrame{
    Runnable closePhase = () -> {};

    public ChipVsChap(){
        assert SwingUtilities.isEventDispatchThread();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menu();
        setVisible(true);
        addWindowListener(new WindowAdapter(){
          public void windowClosed(WindowEvent e){closePhase.run();}
        });
        
    }


    private void menu(){
        var header = new JLabel("Chip Vs Chap", SwingConstants.CENTER);
        var start = new JButton("Play");
        closePhase.run();
        closePhase=()->{
            remove(header);
        };
        add(BorderLayout.CENTER,header);
        add(BorderLayout.SOUTH,start);
        setPreferredSize(new Dimension(800,400));
        pack();
    }

}
