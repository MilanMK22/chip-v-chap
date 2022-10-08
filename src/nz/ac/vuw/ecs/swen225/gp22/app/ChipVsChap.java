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

import imgs.Img;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Model;
import nz.ac.vuw.ecs.swen225.gp22.domain.Phase;

import nz.ac.vuw.ecs.swen225.gp22.recorder.GameAction;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Replay;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Mapprint;
import nz.ac.vuw.ecs.swen225.gp22.renderer.printInventory;
import sounds.sounds;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;
import java.awt.event.*;
import java.io.IOException;
/*
 * Game components will run from this class.
 */
public class ChipVsChap extends JFrame{
    public Character[] characters = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public int[] arrows = {KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT};
    public int[] controls = new int[]{KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT};
    public Character[] characterControls = new Character[]{	'\u2191', '\u2193','\u2190','\u2192'};
    public  JLabel background = new JLabel();
    public JLabel backgroundImage = Board.getBackgroundImage();
    public Model model = Phase.level1(()->levelTwo(), ()->levelOne()).model();
    
   
    static Timer timer;
    int count = 0;
    int delay = 100;
    int timePassed = 0;
    int totalticks=0;

    public sounds s = new sounds();
    public static JLabel timerLabel = new JLabel("test");
    

    /**
     * Updates the keybindings.
     */
    private void updateKeys() {for (int i = 0; i < controls.length; i++) {controls[i] = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(characterControls[i]);}}

    public void setBackGround(){
        background.setOpaque(true);
        background.setBounds(67, 52, 380, 280);
        background.setBackground(Color.black);
    }

    private void action(Replay r, Model model, JLabel chips, String move, Runnable direction ){
        if(r != null){
        r.addMove(new GameAction(move, totalticks));
        }
        System.out.println(model.chap().getLocation().getX() + " , "+ model.chap().getLocation().getY());
        direction.run();
        chips.setText("" + (5 - model.chap().heldTreasure()));
    }


    public Chap getChap(){
        return model.chap();
    }


    /**
     * Returns the character relative to the character.
     * @param e
     * @return
     */
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
       return e.getKeyChar();
    }  
    public int getCode(Character c){
        switch(c){
            case '\u2191':
                return KeyEvent.VK_UP;
            case '\u2193':
                return KeyEvent.VK_DOWN;
            case '\u2190':
                return KeyEvent.VK_LEFT;
            case '\u2192':
                return KeyEvent.VK_RIGHT;
        }
       return java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c);
    }  


    /**
     * Starts the timer for the game level.
     * timeDone is set in the levels method and can be set to how many seconds needed.
     * @param timeDone
     */
    public void startTimer(int timeDone, Model m){
        ActionListener action = (e) -> {
            if(timePassed % 1000 == 0){
                if(count == 0){
                    timer.stop();
                    timerLabel.setText("No time");
                }else{
                    int minutes = count /60;
                    int seconds = count% 60;
                    timerLabel.setText(String.format("%d:%02d", minutes,seconds) );
                    count --;
                }
            }
            timePassed += delay;
            m.tick();
            totalticks++;
            Mapprint.printMap(m, background.getGraphics());
            printInventory.printIn(m,backgroundImage.getGraphics());

        };
        timer = new Timer(delay, action);
        timer.setInitialDelay(0);
        count = timeDone;  
        timer.start();
    }

    /**
     * Updates the keys to be relative to the user input.
     * @param code
     * @param component
     * @return
     */

    public ActionListener reMap(int code, JButton component){
        return (e) -> {
                component.setText("Waiting for input");
                component.addKeyListener(new Keys(){
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

    /*
     * Control frame that allows user to change controls.
     */
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


    /**
     * Start menu frame.
     * @throws IOException
     */
    private void menu() {
        System.out.println("Menu Loaded...");
        var start = new JButton("");
        start.setOpaque(false);
        start.setContentAreaFilled(false);
        start.setBorderPainted(false);
        start.setBounds(315, 235, 170, 70);

        var controls = new JButton("");
        controls.setOpaque(false);
        controls.setContentAreaFilled(false);
        controls.setBorderPainted(false);
        controls.setBounds(515, 235, 170, 70);

        var load = new JButton("");
        load.setOpaque(false);
        load.setContentAreaFilled(false);
        load.setBorderPainted(false);
        load.setBounds(115, 235, 170, 70);

        var replay = new JButton("Replay");
        replay.setOpaque(true);
        replay.setBounds(315, 320, 170, 35);

        var HomeScreen = new JLabel();
        HomeScreen.setBounds(0,0,800,375);
        HomeScreen.setIcon(new ImageIcon(Img.HomeScreen.image));

        JFileChooser open = new JFileChooser();
        s.setFile("src/sounds/menu.wav");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        closePhase.run();
        closePhase=()->{
            remove(start);
            remove(controls);
            remove(HomeScreen);
            remove(panel);
            s.stop();
        };
       
        //add(BorderLayout.SOUTH,panel);
        HomeScreen.add(controls);
        HomeScreen.add(start);
        HomeScreen.add(load);
        HomeScreen.add(replay);
        s.play();
        add(HomeScreen);
       
       
        this.setFocusable(true);
        Keys menuKeyListener = new Keys(){
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                System.out.println(e.getKeyCode());

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
        };
        addKeyListener(menuKeyListener);
        start.addActionListener(s -> {
            levelOne();
            removeKeyListener(menuKeyListener);
        });
        replay.addActionListener(s -> {
            Replay1();
          
            removeKeyListener(menuKeyListener);
        });
        controls.addActionListener(s->controls());
        setPreferredSize(new Dimension(800,400));
       
        pack();
    }


    /**
     * Setting to level one.
     */

    private void levelOne(){setLevel(Phase.level1(()->levelTwo(), ()->menu()), 1,120); }
    private void levelTwo(){setLevel(Phase.level2(()->menu(), ()->levelOne()),2,120); }


    /**
     * Set level function to change between levels.
     * @param p
     */
    private void setLevel(Phase p, int levelNum,int timer){
        closePhase.run();//close phase before adding any element of the new phase
        closePhase=()->{};
        setPreferredSize(getSize());
        run(p,levelNum,timer);
        pack();                   
      }
    

    /**
     * Testing level that is being used to display the game and test the game is functioning as it should.
     */

    private void run(Phase lvl, int levelNum,int time){
        //Replay
        totalticks=0;
        s.stop();
        s.setFile("src/sounds/game.wav");
        s.play();
        Replay r = new Replay(new LinkedList<GameAction>(),levelNum, "");

        //Set model to correct model.
        Model model = lvl.model();

        //Initalize Timer.
        timerLabel.setBounds(630, 140,60, 30);
        startTimer(time,model);

        //Graphical Interface Initialization.
        var level = Board.getLevelLabel(1);
        var chips = Board.getChipLabel();
        var inventory = Board.getInventory();
        JPanel panel = new JPanel(null);
        setBackGround();

        //Pause Dialog Box.
        JOptionPane pane = new JOptionPane("Paused", JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Paused");
        dialog.setModal(false);
        dialog.setVisible(false);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //test
        var pause = new JLabel("PAUSED");
        pause.setBounds(630, 140,60, 30);
        
        //Close Phase
        closePhase=()->{
            timer.stop();
            remove(panel);
        };
        //KeyListener for the chap movement and game functions.
       KeyListener controls = new Keys(){
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if(e.getKeyCode() == getCode(characterControls[0])){
                    action(r,model,chips,"Up",()->model.chap().up());
                }
                if(e.getKeyCode() == getCode(characterControls[1])){
                    action(r,model,chips,"Down",()->model.chap().down());
                }
                if(e.getKeyCode() == getCode(characterControls[2])){
                    action(r,model,chips,"Left",()->model.chap().left());
                }
                if(e.getKeyCode() == getCode(characterControls[3]) ){
                    action(r,model,chips,"Right",()->model.chap().right());
                }
                if((e.getKeyCode() == KeyEvent.VK_S) && e.isControlDown()){
                    dispose();
                    r.saveReplay();
                }   
                if((e.getKeyCode() == KeyEvent.VK_C) && e.isControlDown()){
                    dispose();
                }   
                if((e.getKeyCode() == KeyEvent.VK_SPACE)){
                    removeKeyListener(this);
                    dialog.setVisible(true);
                    timer.stop();
                } 
               
            }
        };
        addKeyListener(controls);
       
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                    addKeyListener(controls);
                    timer.start();
            }
        };
        dialog.addWindowListener(listener);
        //Add components to respective panels and labels.
        add(panel);
        panel.add(background);
        panel.add(backgroundImage);
        backgroundImage.add(level);
        backgroundImage.add(timerLabel);
        backgroundImage.add(chips);
        backgroundImage.add(inventory);
    }   


    public void Replay1(){
        s.stop();
        s.setFile("src/sounds/game.wav");
        s.play();
        closePhase.run();//close phase before adding any element of the new phase
        closePhase=()->{};
        setPreferredSize(getSize());
        pack(); 
        Phase p = Phase.level1(()->menu(), ()->menu());
        Model model = p.model();
        repaint();

        timerLabel.setBounds(630, 140,60, 30);
        var level = Board.getLevelLabel(1);
        var chips = Board.getChipLabel();
        var backgroundImage = Board.getBackgroundImage();
        var inventory = Board.getInventory();

        
        setBackGround();


        JPanel panel = new JPanel(null);
    


        JOptionPane pane = new JOptionPane("Paused", JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane .createDialog(null, "Paused");
        dialog.setModal(false);
        dialog.setVisible(false);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        //for renderer 
        
        //inventory.setBackground(Color.black);
       
        closePhase.run();
        closePhase=()->{
            remove(panel);
        };
        add(panel);
        Keys gameKeyListener = new Keys(){
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if((e.getKeyCode() == KeyEvent.VK_SPACE)){
                    timer.stop();
                    dialog.setVisible(true);
                } 
                if((e.getKeyCode() == KeyEvent.VK_ESCAPE)){
                        timer.start();
                }  
            } 
        };
        addKeyListener(gameKeyListener);

        
        Replay rep = Replay.readXML();
        startTimer(120, model);
    
        panel.add(background);
        panel.add(backgroundImage);
       
        backgroundImage.add(level);
        backgroundImage.add(timerLabel);
        backgroundImage.add(chips);
        backgroundImage.add(inventory);
       

        setPreferredSize(new Dimension(800,400));
        pack();
        Mapprint.printMap(model, background.getGraphics());

        //backgroundImage.repaint();
        ActionListener newone = e -> {
            if(rep.getMoves().isEmpty()){
                System.out.println("empty");
                dispose();
                System.exit(ABORT);
            }
            GameAction r = rep.getMoves().peek();

            if(r.getTime() == totalticks){
                r = rep.getMoves().remove();
            try{
            if(r.getName().equals("Up")){             
                action(null,model,chips,"Up",()->model.chap().up());
            }
            else if(r.getName().equals("Down")){
                action(null,model,chips,"Down",()->model.chap().down());
            }
            else if(r.getName().equals("Left")){
                action(null,model,chips,"Left",()->model.chap().left());
            }
            else if(r.getName().equals("Right")){
                action(null,model,chips,"Right",()->model.chap().right());
            }
        }

        catch(Error b){

        }
        }
            
        };

        timer.addActionListener(newone);
    }
    
   
}
