package nz.ac.vuw.ecs.swen225.gp22.app;
import java.awt.*;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Model;
import nz.ac.vuw.ecs.swen225.gp22.domain.Phase;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Mapprint;

import java.util.Arrays;
import java.awt.event.*;
import java.lang.System.Logger.Level; 
/*
 * Game components will run from this class.
 */
public class ChipVsChap extends JFrame{
    public Character[] characters = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public int[] arrows = {KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT};
    public int[] controls = new int[]{KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT};
    public Character[] characterControls = new Character[]{	'\u2191', '\u2193','\u2190','\u2192'};

    Timer timer;
    int count = 0;
    int delay = 1000;

    private JLabel timerLabel = new JLabel("test");
    

    private void updateKeys() {for (int i = 0; i < controls.length; i++) {controls[i] = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(characterControls[i]);}}

    public Character getArrow(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                return '\u2191';
            case KeyEvent.VK_DOWN:
                return '\u2193';
            case KeyEvent.VK_LEFT:
                return '\u2190';
            case KeyEvent.VK_RIGHT:
                return '\u2192';
        }
       return '\u2190';
    }

    public void startTimer(int timeDone){
        ActionListener action = (e) -> {
            if(count == 0){
                timer.stop();
                timerLabel.setText("No time");
            }else{
                int minutes = count /60;
                int seconds = count% 60;
                timerLabel.setText(String.format("%d:%02d", minutes,seconds) );
                count --;
            }
        };
        timer = new Timer(delay, action);
        timer.setInitialDelay(0);
        count = timeDone;  
        timer.start();
    }
    

    public ActionListener reMap(int code, JButton component){
        return (e) -> {
                component.setText("Waiting for input");
                component.addKeyListener(new KeyListener(){
                    public void keyTyped(KeyEvent e) {}
                    public void keyPressed(KeyEvent e) {
                            if(!Arrays.stream(characterControls).anyMatch(c->getArrow(e).equals(c)) && Arrays.stream(arrows).anyMatch(k->k==e.getExtendedKeyCode())){
                                switch(e.getKeyCode()){
                                    case KeyEvent.VK_UP:
                                        characterControls[code] = '\u2191';
                                        break;
                                    case KeyEvent.VK_DOWN:
                                        characterControls[code] = '\u2193';
                                        break;
                                    case KeyEvent.VK_LEFT:
                                        characterControls[code] = '\u2190';
                                        break;
                                    case KeyEvent.VK_RIGHT:
                                        characterControls[code] = '\u2192';
                                        break;
                                }
                                component.setText(""+ characterControls[code]);
                                component.removeKeyListener(this);
                            }
                            else if(!Arrays.stream(characterControls).anyMatch(c->c==Character.toUpperCase(e.getKeyChar())) && Arrays.stream(characters).anyMatch(c->c==Character.toUpperCase(e.getKeyChar()))){
                                System.out.println("true 2");
                                characterControls[code] = Character.toUpperCase(e.getKeyChar());
                                component.setText(""+ characterControls[code]);
                                component.removeKeyListener(this);   
                            }  
                            else{
                            JOptionPane.showMessageDialog(null, "Key is Invalid");
                            component.setText(""+characterControls[code]);
                            component.removeKeyListener(this);
                            }
                        updateKeys();
                    }
                    public void keyReleased(KeyEvent e) {} 
                });
            };
        };

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

    public void controls(){
        var controls = new JLabel("Control Panel");
        var menu = new JButton("Back to main menu");
        JPanel frame = new JPanel();
        frame.setLayout(new FlowLayout());
        var upLabel = new JLabel("Up");
        var up = new JButton("" + characterControls[0]);
        var downLabel = new JLabel("Down");
        var down = new JButton("" + characterControls[1]);
        var leftLabel = new JLabel("Left");
        var left = new JButton("" + characterControls[2]);
        var rightLabel = new JLabel("Right");
        var right = new JButton("" + characterControls[3]);
        
        closePhase.run();
        closePhase=()->{
         remove(controls);
         remove(menu);
         remove(frame);
         remove(up);
         remove(down);
         remove(left);
         remove(right);

         };
        add(BorderLayout.CENTER,controls);
        add(frame);
        add(BorderLayout.NORTH,menu);
        frame.add(upLabel);
        frame.add(up);
        frame.add(downLabel);
        frame.add(down);
        frame.add(leftLabel);
        frame.add(left);
        frame.add(rightLabel);
        frame.add(right);

        up.addActionListener(reMap(0,up));
        down.addActionListener(reMap(1,down));
        left.addActionListener(reMap(2,left));
        right.addActionListener(reMap(3,right));

        menu.addActionListener(s->menu());
        setPreferredSize(new Dimension(800,400));
        pack();

    }


    private void menu(){
        var header = new JLabel("Chip Vs Chap", SwingConstants.CENTER);
        var start = new JButton("Play");
        var controls = new JButton("Controls");
        var load = new JButton("Load Game");
        JFileChooser open = new JFileChooser();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        closePhase.run();
        closePhase=()->{
            remove(header);
            remove(start);
            remove(controls);
            remove(panel);
        };
        add(BorderLayout.CENTER,header);
        add(BorderLayout.SOUTH,panel);
        add(BorderLayout.NORTH,controls);
        panel.add(load);
        panel.add(start);
        this.setFocusable(true);
        KeyListener menuKeyListener = new KeyListener(){
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                System.out.println(e.getKeyCode());
                if((e.getKeyCode() == KeyEvent.VK_C) && e.isControlDown()){
                    dispose();
                    JOptionPane.showMessageDialog(panel, "Closed Game");
                }   
                if((e.getKeyCode() == KeyEvent.VK_S) && e.isControlDown()){
                    dispose();
                    JOptionPane.showMessageDialog(panel, "Saved Game");
                }   
                if((e.getKeyCode() == KeyEvent.VK_R) && e.isControlDown()){
                    open.showOpenDialog(panel); // needs variable
                }   
                if((e.getKeyCode() == KeyEvent.VK_1) && e.isControlDown()){
                    // opens new game at level 1
                    System.out.println("level 1");
                }   
                if((e.getKeyCode() == KeyEvent.VK_2) && e.isControlDown()){
                    // opens new game at level 2
                    System.out.println("level 2");
                }        
            }
            public void keyReleased(KeyEvent e) {}
        };
        addKeyListener(menuKeyListener);
        start.addActionListener(s -> {
            testLevel();
            removeKeyListener(menuKeyListener);
        });
        controls.addActionListener(s->controls());
        setPreferredSize(new Dimension(800,400));
        pack();
    }


    private void levelOne(){setLevel(Level.level1(()->levelOne(), ()->menu(), controls)); }

    private void setLevel(Level p){
        closePhase.run();//close phase before adding any element of the new phase
        closePhase=()->{};
        setPreferredSize(getSize());
        pack();                   
      }
    
     
    private void testLevel(){
        closePhase.run();//close phase before adding any element of the new phase
        closePhase=()->{};
        setPreferredSize(getSize());
        pack(); 
        repaint();
        var level = new JLabel("test",SwingConstants.CENTER);

        level.setBounds(67, 52, 380, 280);
        timerLabel.setBounds(630, 140,60, 30);


        var chips = new JLabel("test",SwingConstants.CENTER);
        chips.setBounds(615, 203,60, 30);


        var backgroundImage = new JLabel();
        backgroundImage.setBounds(0,0,800,375);
        ImageIcon img = new ImageIcon("src/imgs/fullmap.png");
        var background = new JLabel();
        background.setOpaque(true);
        background.setBounds(67, 52, 380, 280);
        background.setBackground(Color.red);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        backgroundImage.setIcon(img);

        startTimer(20);

        JOptionPane pane = new JOptionPane("Paused", JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane .createDialog(null, "Paused");
        dialog.setModal(false);
        dialog.setVisible(false);
        //for renderer 
        Model m = new Model(new Maze(Persistency.readXML("level1")));
        closePhase.run();
        closePhase=()->{
            remove(panel);
        };
        add(panel);
        KeyListener gameKeyListener = new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if((e.getKeyCode() == KeyEvent.VK_SPACE)){
                    dialog.setVisible(true);
                } 
                if((e.getKeyCode() == KeyEvent.VK_ESCAPE)){
                    dialog.setVisible(false);
                }  
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            
        };
        addKeyListener(gameKeyListener);
       

        panel.add(backgroundImage);
        backgroundImage.add(level);
        backgroundImage.add(timerLabel);
        backgroundImage.add(chips);
       
        setPreferredSize(new Dimension(800,400));
        pack();

        backgroundImage.add(background);
        Mapprint.printMap(m, background.getGraphics());
        backgroundImage.repaint();
    }   
    
   
}
