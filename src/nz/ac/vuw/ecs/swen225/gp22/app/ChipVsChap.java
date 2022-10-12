package nz.ac.vuw.ecs.swen225.gp22.app;
import java.awt.*;

import javax.print.attribute.standard.NumberOfDocuments;
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

import org.apache.bcel.generic.LNEG;

import imgs.Img;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Model;
import nz.ac.vuw.ecs.swen225.gp22.domain.Phase;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.recorder.GameAction;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Replay;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Mapprint;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Textbox;
import nz.ac.vuw.ecs.swen225.gp22.renderer.printInventory;
import nz.ac.vuw.ecs.swen225.gp22.persistency.*;
import sounds.sounds;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;
import java.util.stream.Stream;
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
    public Model model2 = Phase.level2(null, null).model();
    public static int numOfChips = 5;
    public static int levelNum = 1;
    public static JLabel chips;
    public static JLabel level;
    private static final int HEIGHT = 450;
    private static final int WIDTH = 800;



    // by ilya 
    public List<Tile> listOfVisitedTiles = new ArrayList<Tile>();
    public List<Tile> unvisitedTilesList = new ArrayList<Tile>();
    
    //jack
    public int txtIndex = 0;
   
    public Timer timer;
    public int count = 0;
    public int delay = 50;
    public int timePassed = 0;
    public int totalticks=0;
    public KeyListener Replistner; //so we can remove the key listner when doing a replay
    public ActionListener ReplayListner;
    public KeyListener PlaybPlayListner;
    public Replay replay;

    public sounds s = new sounds();
    public static JLabel timerLabel = new JLabel("test");
    
    /**
     * Updates the keybindings.
     */
    private void updateKeys() {for (int i = 0; i < controls.length; i++) {controls[i] = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(characterControls[i]);}}

    public void setBackGround(){
        background.setOpaque(true);
        background.setBounds(65, 27, 380, 280);
        background.setBackground(Color.black);
    }

    public void action(Replay r, Model model, String move, Runnable direction){
        if(r != null){
        r.addMove(new GameAction(move, totalticks));
        }
        System.out.println(model.chap().getLocation().getX() + " , "+ model.chap().getLocation().getY());
        direction.run();
    }

    public void addRecorder(){
        replay = new Replay(new LinkedList<GameAction>(),levelNum, "");
    }

    /**
     * a method to get the chap form the game 
     * @param level the level of the game
     * @return a chap 
     */
    public Chap getChap(int level){
        if(level == 1){ return model.chap(); }
        else if (level == 2){ return model2.chap(); }
        else{ throw new IllegalArgumentException("Invalid level"); }
    }

    /**
     * move methods for Fuzz to use on the chap deending on the level
     * @param level the level of the game which is being tested
     */
    public void up(int level){ getChap(level).up(); }
    public void down(int level){ getChap(level).down(); }
    public void left(int level){ getChap(level).left(); }
    public void right(int level){ getChap(level).right(); }

    public Tile[][] getTiles(int level){ 
        if(level == 1){ return model.getMaze().getTiles();}
        else if (level == 2){ return model2.getMaze().getTiles(); }
        else{ throw new IllegalArgumentException("Invalid level"); }
    }
     // ilya
    // public boolean isVisited() { return true; }
    // ilya
    // public Point location(int level) {
    //     if(level == 1){ return getChap(level).getLocation(); }
    //     else if (level == 2){ return model2.chap().getLocation(); }
    //     else{ throw new IllegalArgumentException("Invalid level"); }
    // }
    // ilya
    public Tile getTileAtLocation(Point location, int level) {
        Tile[][] tiles;
        if(level == 1){ 
            tiles = getTiles(level);
            return tiles[location.getX()][location.getY()];
        }
        else if (level == 2){
            tiles = getTiles(level);
            return tiles[location.getX()][location.getY()];
        }
        else{ throw new IllegalArgumentException("Invalid level"); }
    }

    //ilya
    public void setVisitedTiles(int level){
        Tile tile;
        if (level == 1){ 
            tile = getTileAtLocation(model.chap().getLocation(), level); 
            unvisitedTilesList.remove(tile);
            listOfVisitedTiles.add(tile);
            tile.visited(true);
        }
        else if (level == 2){ 
            tile = getTileAtLocation(model2.chap().getLocation(), level); 
            unvisitedTilesList.remove(tile);
            listOfVisitedTiles.add(tile);
            tile.visited(true);
        }
        else{ throw new IllegalArgumentException("Invalid level"); }
    }
//ilya
    public void unvisitedTiles(int level){
        Tile[][] tiles = getTiles(level);
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                if(tiles[i][j].isFree()){
                    unvisitedTilesList.add(tiles[i][j]);
                }
            }
        }
    }

    //ilya
    public String chapToString(int level){
        if(level == 1){ return model.chap().toString(); }
        else if(level == 2){ return model2.chap().toString(); }
        else{ throw new IllegalArgumentException("invalid level");}
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
    public void startTimer(int timeDone, Model m, JLabel chips){
        ActionListener action = (e) -> {
            if(timePassed % 1000 == 0){
                if(count == 0){
                    timer.stop();
                    m.loss();
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
            if(totalticks%2==0 && background.getGraphics()!= null){
            Mapprint.printMap(m, background.getGraphics());
            printInventory.printIn(m,backgroundImage.getGraphics());
            if(m.onInfo()){
                Textbox.printTextBox(getName(), txtIndex, backgroundImage.getGraphics());
                txtIndex++;
            }
            }
            chips.setText("" + (numOfChips - model.chap().heldTreasure()));
            

        };
        
        timer = new Timer(delay, action);
        if(ReplayListner != null){
            timer.addActionListener(ReplayListner);
        }
        timer.setInitialDelay(0);
        count = timeDone;  
        if(PlaybPlayListner == null){
        timer.start();
        }
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

    public Runnable closePhase = () -> {};

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
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        pack();

    }


    /**
     * Start menu frame.
     * @throws IOException
     */
    public void menu() {
        System.out.println("Menu Loaded...");
        var start = new JButton();
        start.setOpaque(false);
        start.setContentAreaFilled(false);
        start.setBorderPainted(false);
        start.setBounds(315, 210, 170, 70);

        var controls = new JButton();
        controls.setOpaque(false);
        controls.setContentAreaFilled(false);
        controls.setBorderPainted(false);
        controls.setBounds(510, 205, 170, 70);

        var load = new JButton();
        load.setOpaque(false);
        load.setContentAreaFilled(false);
        load.setBorderPainted(false);
        load.setBounds(115, 205, 170, 70);

        var replay = new JButton();
        replay.setOpaque(true);
        replay.setOpaque(false);
        replay.setContentAreaFilled(false);
        replay.setBorderPainted(false);
        replay.setBounds(200, 310, 165, 70);

        var playByPlay = new JButton();
        playByPlay.setOpaque(true);
        playByPlay.setOpaque(false);
        playByPlay.setContentAreaFilled(false);
        playByPlay.setBorderPainted(false);
        playByPlay.setBounds(395, 310, 165, 70);

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
        HomeScreen.add(playByPlay);
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
                if((e.getKeyCode() == KeyEvent.VK_R) && e.isControlDown()){Recorder.Auto(ChipVsChap.this);}   
                if((e.getKeyCode() == KeyEvent.VK_1) && e.isControlDown()){
                    // opens new game at level 1
                    levelOne();
                    removeKeyListener(this);
                }   
                if((e.getKeyCode() == KeyEvent.VK_2) && e.isControlDown()){
                    // opens new game at level 2
                    levelTwo();
                    removeKeyListener(this);

                }        
            }
        };
        addKeyListener(menuKeyListener);
        start.addActionListener(s -> {
            levelOne();
            removeKeyListener(menuKeyListener);
        });
        load.addActionListener(s -> {
            levelPersistency();
            removeKeyListener(menuKeyListener);
        });
        replay.addActionListener(s -> {
            Recorder.Auto(this);
            removeKeyListener(menuKeyListener);
        });
        playByPlay.addActionListener(s -> {
            Recorder.PlaybPlay(this);
            removeKeyListener(menuKeyListener);
        });

        controls.addActionListener(s->controls());
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
       
        pack();
    }

    private void winner(){
        var start = new JLabel("WINNER");
        JPanel panel = new JPanel();
        JButton restart = new JButton("Back to Menu");
        restart.setBounds(315, 235, 170, 70);
        panel.setLayout(new FlowLayout());
        closePhase.run();
        closePhase=()->{
          remove(panel);
        };
        panel.add(start);
        panel.add(restart);

        add(panel);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        pack();
    }


    /**
     * Setting to level one.
     */

    public void levelOne(){setLevel(Phase.level1(()->levelTwo(), ()->menu()), 1,120,5); }
    public void levelTwo(){setLevel(Phase.level2(()->{timer.stop(); winner();}, ()->levelTwo()),2,180,3); }
    public void levelPersistency(){setLevel(Phase.levelSave(()->menu(), ()->{timer.stop(); winner();}),2,180,Persistency.getNumChips("levelPers")); }


    /**
     * Set level function to change between levels.
     * @param p
     */
    public void setLevel(Phase p, int level,int timer,int numChips){
        closePhase.run();//close phase before adding any element of the new phase
        closePhase=()->{};
        setPreferredSize(getSize());
        numOfChips = numChips;
        levelNum = level;
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

        if(replay == null){
            this.addRecorder();
        }
        

        //Set model to correct model.
        model = lvl.model();

        //Graphical Interface Initialization.
        level = Board.getLevelLabel(levelNum);
        chips= Board.getChipLabel(numOfChips);
        var inventory = Board.getInventory();
        JPanel panel = new JPanel(null);
        setBackGround();
       
        //Initalize Timer.
        timerLabel.setBounds(630, 140,60, 30);
        startTimer(time,model,chips);


        //Pause Dialog Box.
        JDialog dialog = Board.getPause();

        //Close Phase
        closePhase=()->{
            timer.stop();
            remove(panel);
            remove(level);
        };
        //KeyListener for the chap movement and game functions.
       KeyListener controls = new Keys(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == getCode(characterControls[0])){action(replay,model,"Up",()->model.chap().up());}
                if(e.getKeyCode() == getCode(characterControls[1])){action(replay,model,"Down",()->model.chap().down());}
                if(e.getKeyCode() == getCode(characterControls[2])){action(replay,model,"Left",()->model.chap().left());}
                if(e.getKeyCode() == getCode(characterControls[3]) ){action(replay,model,"Right",()->model.chap().right());}
                if((e.getKeyCode() == KeyEvent.VK_C) && e.isControlDown()){dispose();}   
                if((e.getKeyCode() == KeyEvent.VK_S) && e.isControlDown()){
                    dispose();
                    replay.saveReplay();
                    replay = null;
                }   
                if((e.getKeyCode() == KeyEvent.VK_SPACE)){
                    removeKeyListener(this);
                    dialog.setVisible(true);
                    timer.stop();
                } 
            }
        };
        if(ReplayListner == null && PlaybPlayListner == null){
        System.out.println("null");
        addKeyListener(controls);
        Replistner = controls;
        }

        //Pause box closed
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                    addKeyListener(controls);
                    timer.start();
            }
        };
        dialog.addWindowListener(listener);

        //Close Phase
        closePhase=()->{
            timer.stop();
            remove(panel);
            remove(level);
            removeKeyListener(controls);
            backgroundImage.remove(level);
            backgroundImage.remove(chips);

        };

        //Add components to respective panels and labels.
        add(panel);
        panel.add(background);
        panel.add(backgroundImage);
        backgroundImage.add(level);
        backgroundImage.add(timerLabel);
        backgroundImage.add(chips);
        backgroundImage.add(inventory);
    }  
    
}
