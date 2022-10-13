package nz.ac.vuw.ecs.swen225.gp22.app;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import imgs.Img;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Model;
import nz.ac.vuw.ecs.swen225.gp22.domain.Phase;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.recorder.GameAction;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Replay;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Mapprint;
import nz.ac.vuw.ecs.swen225.gp22.renderer.printInventory;
import nz.ac.vuw.ecs.swen225.gp22.persistency.*;
import sounds.sounds;
import sounds.sounds.SOUND;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.*;

/**
 * Represents the ChipVsChap game and is constructed in the main class.
 * This class is in charge of running the game by calling upon every other component.
 * Displays buttons, game and labels and contains keyListeners as controls. 
 * Timer is stored here.
 */
public class ChipVsChap extends JFrame{
    public Runnable closePhase = () -> {};
    public JLabel background = new JLabel(); // Background in which the game runs on.
    public JLabel backgroundImage = Board.getBackgroundImage(); // Background image of the game.
    public Model model = Phase.level1(()->levelTwo(), ()->levelOne()).model(); //contains the objects associated with the game.
    public Model fuzzModel;

    //Number of chips and level number and their respective label objects.
    public int numOfChips = 5;
    public int levelNum = 1;
    public static JLabel chips;
    public static JLabel level;


    private static final int HEIGHT = 450; // Height of the game window.
    private static final int WIDTH = 800; // Width of the game window.
    //Information box variables.
    public static JLabel info;
    public static String infoString1 = "Find the keys to collect the coins and escape!";
    public static String infoString2 = "Beware of the monsters!";
    public static JLabel infoTextLabel = Board.getInfoText(infoString1);
    //JDialog box variables.
    JDialog pause = Board.getPause();
    JDialog timeOut = Board.getTimeout();



    // by ilya 
    public List<Tile> listOfVisitedTiles = new ArrayList<Tile>();
    public List<Tile> unvisitedTilesList = new ArrayList<Tile>();
    public int modelCount = 0;
    
    //jack
    public int txtIndex = 0;
   
    //Timer variables.
    public Timer timer;
    public int count = 0;
    public int delay;
    public int timePassed = 0;
    public int totalticks=0;
    public static JLabel timerLabel = new JLabel();

    public KeyListener Replistner; //so we can remove the key listner when doing a replay
    public ActionListener ReplayListner;
    public KeyListener PlaybPlayListner;
    public Replay replay;

    public sounds s = new sounds();
    
    /**
     * This method sets the background to it's respective properties relative to the game window.
     */
    public void setBackGround(){
        background.setOpaque(true);
        background.setBounds(67, 52, 380, 280);
        background.setBackground(Color.black);
    }

    /**
     * This method calls upon the Runnable direction interface which contains a function that moves Chap.
     * 
     * @param r Replay object that stores the moves made within the game.
     * @param move Direction in string form.
     * @param direction Contains the movement function from domain associated with the direction.
     */
    public void action(Replay r, String move, Runnable direction){
        if(r != null){r.addMove(new GameAction(move, totalticks));}
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
    public Chap getChap(){
        return fuzzModel.chap();
    }

    //ilya
    public void tick(){
        fuzzModel.tick();
    }

    /**
     * move methods for Fuzz to use on the chap deending on the level
     * @param level the level of the game which is being tested
     */
    public void up(){ getChap().up(); }
    public void down(){ getChap().down(); }
    public void left(){ getChap().left(); }
    public void right(){ getChap().right(); }

    public Tile[][] getTiles(){ 
        return fuzzModel.maze().getTiles();
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
    public Tile getTileAtLocation(Point location) {
        Tile[][] tiles;
        tiles = getTiles();
        return tiles[location.getX()][location.getY()];
    }

    //ilya
    public void setVisitedTiles(){
        Tile tile;
        tile = getTileAtLocation(fuzzModel.chap().getLocation()); 
        unvisitedTilesList.remove(tile);
        listOfVisitedTiles.add(tile);
        tile.visited(true);
    }
//ilya
    public int unvisitedTiles(){
        return (int)fuzzModel.maze().stream().filter(i->!i.getVisited() && i.isFree()).count();
    }

    //ilya
    public String chapToString(){
        return fuzzModel.chap().toString();
    }

    public void fuzzModel(int level){
        modelCount++;
        listOfVisitedTiles = new ArrayList<>();
        unvisitedTilesList = new ArrayList<>();
        if(level == 1){ fuzzModel = Phase.level1(()->fuzzModel(1), ()->fuzzModel(1)).model(); }
        if(level == 2){ fuzzModel = Phase.level2(()->fuzzModel(2), ()->fuzzModel(2)).model(); }
    }

    /**
     * Starts the timer for the game level.
     * timeDone is set in the levels method and can be set to how many seconds are needed.
     * Timer label and chips count are changed within the actionListener in the Swing timer.
     * This method is responsible for drawing the rendering module, ticks and information tile popups within the game.
     * 
     * @param timeDone
     * @param m
     * @param chips
     */
    public void startTimer(int timeDone, Model m, JLabel chips){
        ActionListener action = (e) -> {
            if(timePassed % 1000 == 0){
                if(count == 0){
                    timer.stop();
                    timeOut.setVisible(true);
                    timeOut.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowDeactivated(WindowEvent e) {
                            m.loss();
                        }
                    });
                }else{
                    int minutes = count /60;
                    int seconds = count% 60;
                    timerLabel.setText(String.format("%d:%02d", minutes,seconds) );
                    count --;
                }
            }
            timePassed += 50;
            m.tick();
            totalticks++;
            if(totalticks%2==0 && background.getGraphics()!= null){
            Mapprint.printMap(m, background.getGraphics());
            printInventory.printIn(m,backgroundImage.getGraphics());
            if(m.onInfo()){info.setVisible(true);}
            else{info.setVisible(false);}
            }
            chips.setText("" + (numOfChips - model.chap().heldTreasure()));
        };
        if(delay != 25){delay = 50;}
        timer = new Timer(delay, action);
        if(ReplayListner != null){timer.addActionListener(ReplayListner);}
        timer.setInitialDelay(0);
        count = timeDone;  
        if(PlaybPlayListner == null){
        timer.start();
        }
    }

    /**
     * ChipVsChap constructor that calls the menu function to start off the game.
     */

    public ChipVsChap(){
        assert SwingUtilities.isEventDispatchThread();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu();//CHANGE THIS
        setVisible(true);
        addWindowListener(new WindowAdapter(){
          public void windowClosed(WindowEvent e){closePhase.run();}
        });
    }

    /*
     * Control frame that allows user to change controls.
     * Also contains the instructions for the game.
     */
    public void controlsAndHelp(){
        var container = new JLabel("a");
        container.setIcon(new ImageIcon(Img.Controls.image));

        //Control variables.
        var controls = new JLabel("Control Panel");
        var menu = new JButton("Back to main menu");
        menu.setBounds((WIDTH/2)-100,HEIGHT-150,200,50);
        JLabel panel = new JLabel();
        panel.setLayout(new FlowLayout());
        panel.setBounds(0,50,WIDTH,50);
        var upLabel = new JLabel("Up");
        var up = new JButton("" + Controller.characterControls[0]);
        var downLabel = new JLabel("Down");
        var down = new JButton("" + Controller.characterControls[1]);
        var leftLabel = new JLabel("Left");
        var left = new JButton("" + Controller.characterControls[2]);
        var rightLabel = new JLabel("Right");
        var right = new JButton("" + Controller.characterControls[3]);

        //Instruction variable
        JLabel instructions = new JLabel("<html><p style=\"text-align:center\">Find the keys to unlock the doors<br/><br/>Collect all the coins to escape the maze<p><html>",SwingConstants.CENTER);
        instructions.setBounds((WIDTH/2) - 200,(HEIGHT/2) - 75,400,200);
        
        closePhase.run();
        closePhase=()->{
            remove(panel);
            remove(container);
            remove(menu);
            remove(controls);
            remove(instructions);
        };

        //Adding components to the frame.
        add(container);
        
        container.add(panel);
        container.add(menu);
        container.add(instructions);
        panel.add(upLabel);
        panel.add(up);
        panel.add(downLabel);
        panel.add(down);
        panel.add(leftLabel);
        panel.add(left);
        panel.add(rightLabel);
        panel.add(right);

        //Adding action listeners.
        up.addActionListener(Controller.reMap(0,up));
        down.addActionListener(Controller.reMap(1,down));
        left.addActionListener(Controller.reMap(2,left));
        right.addActionListener(Controller.reMap(3,right));

        menu.addActionListener(s->menu());
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        pack();
    }


    /**
     * Represents the first opening screen containing the buttons required to start,load,replay and settings and help screens.
     */
    public void menu() {
        //Swing components.
        var start = new JButton();
        var controls = new JButton();
        var load = new JButton();
        var replay = new JButton();
        var playByPlay = new JButton();
        var homeScreen = new JLabel();

        //Swing component properties set.
        start.setBorderPainted(false);
        replay.setBorderPainted(false);
        load.setBorderPainted(false);
        controls.setBorderPainted(false);
        playByPlay.setBorderPainted(false);

        //Swing component bounds set.
        start.setBounds(315, 235, 170, 70);
        controls.setBounds(510, 230, 170, 70);
        load.setBounds(115, 230, 170, 70);
        replay.setBounds(200, 330, 165, 70);
        playByPlay.setBounds(395, 330, 165, 70);
        homeScreen.setBounds(0,0,800,375);


        //Background image set.
        homeScreen.setIcon(new ImageIcon(Img.HomeScreen.image));
        //File loader for saved games
        JFileChooser open = new JFileChooser();
        SOUND.MENU.play();
        SOUND.MENU.looping();

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        closePhase.run();
        closePhase=()->{
            remove(start);
            remove(controls);
            remove(homeScreen);
            remove(panel);
            SOUND.MENU.stop();
        };
       
        //Adding swing components to the background.
        homeScreen.add(controls);
        homeScreen.add(playByPlay);
        homeScreen.add(start);
        homeScreen.add(load);
        homeScreen.add(replay);
        add(homeScreen);
    
        //KeyListeners
        this.setFocusable(true);
        Keys menuKeyListener = new Keys(){
            public void keyPressed(KeyEvent e) {
                if((e.getKeyCode() == KeyEvent.VK_R) && e.isControlDown()){
                    open.showSaveDialog(null);
                    levelPersistency();
                }   
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
                if((e.getKeyCode() == KeyEvent.VK_X) && e.isControlDown()){
                    dispose();
                    System.exit(ABORT);
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

        controls.addActionListener(s->controlsAndHelp());
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
       
        pack();
    }

    
    /**
     * Winner screen appears when the game is beat.
     * Allows user to save replay and go back to the main menu.
     */
    private void winner(){
        SOUND.GAME.stop();
        var endScreen = new JLabel();
        endScreen.setIcon(new ImageIcon(Img.win.image));
        endScreen.setBounds(0,0,WIDTH,HEIGHT);
        JButton restart = new JButton();
        JButton saveReplay = new JButton();
        restart.setBounds(212,304,165,70);
        restart.setBorderPainted(false);
  

        saveReplay.setBounds(415, 304, 165, 70);
        saveReplay.setBorderPainted(false);
        closePhase.run();
        closePhase=()->{
          remove(endScreen);
        };
        endScreen.add(restart);
        endScreen.add(saveReplay);

        add(endScreen);

        saveReplay.addActionListener(s->{
            replay.saveReplay();
            replay = null;});
        restart.addActionListener(s->menu());
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        pack();
    }

    /**
     * Level One
     * Changes the level to the level one settings with its respective properties.
     */
    public void levelOne(){setLevel(Phase.level1(()->levelTwo(), ()->levelOne()), 1,60,5, infoString1); }
    /**
     * Level Two
     * Changes the level to the level two settings with its respective properties.
     */
    public void levelTwo(){setLevel(Phase.level2(()->{timer.stop(); winner();}, ()->levelTwo()),2,120,3, infoString2); }
    /**
     * Loaded Level
     * Changes the level to the loaded level settings with its respective properties.
     */
    public void levelPersistency(){setLevel(Phase.levelSave(()->levelTwo(), ()->levelOne()),3,Persistency.getLevelTime("levelPers"),Persistency.getNumChips("levelPers"),infoString1); }


   /**
    * Set level function to change between levels and contains variables that need to be changed every level.

    * @param p Phase variable, contains model and chap.
    * @param level Integer used to set the label.
    * @param timer Time limit for level.
    * @param numChips Number of chips required on this level.
    * @param infoText Information text on this level.
    */
    public void setLevel(Phase p, int level,int timer,int numChips,String infoText){
        closePhase.run();//close phase before adding any element of the new phase
        closePhase=()->{};
        setPreferredSize(getSize());
        numOfChips = numChips;
        levelNum = level;
        run(p,levelNum,timer,infoText);
        pack();                   
      }
    

    /**
     * Runs the game using Phase lvl to change between levels
     * Called everytime a new level is reached but with different arguements.
     * 
     * @param lvl Contains the objects on this level.
     * @param levelNum Level number.
     * @param time Time allowed on this level.
     * @param infoText Information text to be shown.
     */
    private void run(Phase lvl, int levelNum,int time, String infoText){
        //Replay
        totalticks=0;
        SOUND.GAME.play();
        SOUND.GAME.looping();

        if(replay == null){
            this.addRecorder();
        }
        
        //Set model to correct model.
        model = lvl.model();


        //Graphical Interface Initialization.
        JLabel navBar = new JLabel();
        navBar.setBounds(0, 0, 800, 30);
        navBar.setLayout(new FlowLayout());
        JButton pauseButton = new JButton("Pause");
        pauseButton.setFocusable(false);
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");
        info = Board.getInfo();
        infoTextLabel = Board.getInfoText(infoText);
        level = Board.getLevelLabel(levelNum);
        chips= Board.getChipLabel(numOfChips);
        var inventory = Board.getInventory();
        JPanel panel = new JPanel(null);
        setBackGround();
       
        //Initalize Timer.
        timerLabel.setBounds(630, 140,60, 30);
        startTimer(time,model,chips);

        //Pause Dialog Box.
        pause = Board.getPause();

        //KeyListener for the chap movement and game functions.
       KeyListener controls = new Keys(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == Controller.getCode(Controller.characterControls[0])){action(replay,"Up",()->model.chap().up());}
                if(e.getKeyCode() == Controller.getCode(Controller.characterControls[1])){action(replay,"Down",()->model.chap().down());}
                if(e.getKeyCode() == Controller.getCode(Controller.characterControls[2])){action(replay,"Left",()->model.chap().left());}
                if(e.getKeyCode() == Controller.getCode(Controller.characterControls[3]) ){action(replay,"Right",()->model.chap().right());}
                if((e.getKeyCode() == KeyEvent.VK_X) && e.isControlDown()){
                    dispose();
                    System.exit(ABORT);
                }   
                if((e.getKeyCode() == KeyEvent.VK_S) && e.isControlDown()){
                    Persistency.createPXML(model.maze().getTiles(), model.maze().getChap().getInvKeys(),count);
                    dispose();
                    System.exit(ABORT);
                }   
                if((e.getKeyCode() == KeyEvent.VK_SPACE)){
                    removeKeyListener(this);
                    pause.setVisible(true);
                    timer.stop();
                } 
            }
        };

        //Navbar action listeners.
        pauseButton.addActionListener(e->{
            removeKeyListener(controls);
            pause.setVisible(true);
            timer.stop();
        });
        saveButton.addActionListener(e->{
            Persistency.createPXML(model.maze().getTiles(), model.maze().getChap().getInvKeys(),count);
            menu();
        });
        quitButton.addActionListener(e->{
            replay = null;
            dispose();
            System.exit(ABORT);
        });

        if(ReplayListner == null && PlaybPlayListner == null){
        addKeyListener(controls);
        Replistner = controls;
        }

        //Pause box on deactivated.
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                    addKeyListener(controls);
                    timer.start();
            }
        };
        pause.addWindowListener(listener);

        //Close Phase
        closePhase=()->{
            timer.stop();
            remove(panel);
            remove(level);
            removeKeyListener(controls);
            backgroundImage.remove(level);
            backgroundImage.remove(chips);
            info.remove(infoTextLabel);
            backgroundImage.remove(info);

        };

        //Add components to respective panels and labels.
        add(panel);
        panel.add(background);
        panel.add(backgroundImage);
        backgroundImage.add(level);
        backgroundImage.add(timerLabel);
        backgroundImage.add(chips);
        backgroundImage.add(inventory);
        backgroundImage.add(info);
        backgroundImage.add(navBar);
        navBar.add(pauseButton);
        navBar.add(saveButton);
        navBar.add(quitButton);
        info.add(infoTextLabel);

    }  

    
}
