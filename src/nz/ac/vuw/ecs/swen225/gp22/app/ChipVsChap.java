package nz.ac.vuw.ecs.swen225.gp22.app;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.event.*;
import java.io.IOException;
/*
 * Game components will run from this class.
 */
public class ChipVsChap extends JFrame{
    public  JLabel background = new JLabel();
    public JLabel backgroundImage = Board.getBackgroundImage();
    public Model model = Phase.level1(()->levelTwo(), ()->levelOne()).model();
    public Model fuzzModel;
    public int numOfChips = 5;
    public int levelNum = 1;
    public static JLabel chips;
    public static JLabel level;
    private static final int HEIGHT = 450;
    private static final int WIDTH = 800;
    public static JLabel info;
    public static String infoString1 = "Find the keys to collect the coins and escape!";
    public static String infoString2 = "Beware of the monsters!";
    public static JLabel infoTextLabel = Board.getInfoText(infoString1);
    JDialog pause = Board.getPause();
    JDialog timeOut = Board.getTimeout();



    // by ilya 
    public List<Tile> listOfVisitedTiles = new ArrayList<Tile>();
    public List<Tile> unvisitedTilesList = new ArrayList<Tile>();
    public int modelCount = 0;
    
    //jack
    public int txtIndex = 0;
   
    public Timer timer;
    public int count = 0;
    public int delay;
    public int timePassed = 0;
    public int totalticks=0;
    public KeyListener Replistner; //so we can remove the key listner when doing a replay
    public ActionListener ReplayListner;
    public KeyListener PlaybPlayListner;
    public Replay replay;

    public sounds s = new sounds();
    public static JLabel timerLabel = new JLabel();
    
    /**
     * Updates the keybindings.
     */

    public void setBackGround(){
        background.setOpaque(true);
        background.setBounds(67, 52, 380, 280);
        background.setBackground(Color.black);
    }

    public void action(Replay r, Model model, String move, Runnable direction){
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
     * timeDone is set in the levels method and can be set to how many seconds needed.
     * @param timeDone
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
            timePassed += delay;
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
     * Updates the keys to be relative to the user input.
     * @param code
     * @param component
     * @return
     */

   
    public Runnable closePhase = () -> {};

    public ChipVsChap(){
        assert SwingUtilities.isEventDispatchThread();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        var up = new JButton("" + Controller.characterControls[0]);
        var downLabel = new JLabel("Down");
        var down = new JButton("" + Controller.characterControls[1]);
        var leftLabel = new JLabel("Left");
        var left = new JButton("" + Controller.characterControls[2]);
        var rightLabel = new JLabel("Right");
        var right = new JButton("" + Controller.characterControls[3]);
        
        closePhase.run();
        closePhase=()->{removeAll();};
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

        up.addActionListener(Controller.reMap(0,up));
        down.addActionListener(Controller.reMap(1,down));
        left.addActionListener(Controller.reMap(2,left));
        right.addActionListener(Controller.reMap(3,right));

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
        var controls = new JButton();
        var load = new JButton();
        var replay = new JButton();
        var playByPlay = new JButton();
        var homeScreen = new JLabel();



        start.setBorderPainted(false);
        replay.setBorderPainted(false);
        load.setBorderPainted(false);
        controls.setBorderPainted(false);
        playByPlay.setBorderPainted(false);

        start.setBounds(315, 235, 170, 70);
        controls.setBounds(510, 230, 170, 70);
        load.setBounds(115, 230, 170, 70);
        replay.setBounds(200, 330, 165, 70);
        playByPlay.setBounds(395, 330, 165, 70);
        homeScreen.setBounds(0,0,800,375);


        homeScreen.setIcon(new ImageIcon(Img.HomeScreen.image));
        JFileChooser open = new JFileChooser();
        s.setFile("src/sounds/menu.wav");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        closePhase.run();
        closePhase=()->{
            remove(start);
            remove(controls);
            remove(homeScreen);
            remove(panel);
            s.stop();
        };
       
        homeScreen.add(controls);
        homeScreen.add(playByPlay);
        homeScreen.add(start);
        homeScreen.add(load);
        homeScreen.add(replay);
        s.play();
        add(homeScreen);
       
        this.setFocusable(true);
        Keys menuKeyListener = new Keys(){
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
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
        JButton saveReplay = new JButton("Save Replay");

        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent event){

            }
        };
        Timer winTimer = new Timer(100, listener);
        winTimer.setRepeats(false);
        winTimer.start();


        restart.setBounds(315, 235, 170, 70);
        panel.setLayout(new FlowLayout());
        closePhase.run();
        closePhase=()->{
          remove(panel);
        };
        panel.add(start);
        panel.add(restart);
        panel.add(saveReplay);

        add(panel);

        saveReplay.addActionListener(s->{
            replay.saveReplay();
            replay = null;});
        restart.addActionListener(s->menu());
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        pack();
    }


    /**
     * Setting to level one.
     */

    public void levelOne(){setLevel(Phase.level1(()->levelTwo(), ()->levelOne()), 1,60,5, infoString1); }
    public void levelTwo(){setLevel(Phase.level2(()->{timer.stop(); winner();}, ()->levelTwo()),2,120,3, infoString2); }
    public void levelPersistency(){setLevel(Phase.levelSave(()->levelTwo(), ()->levelOne()),3,Persistency.getLevelTime("levelPers"),Persistency.getNumChips("levelPers"),infoString1); }


    /**
     * Set level function to change between levels.
     * @param p
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
     * Testing level that is being used to display the game and test the game is functioning as it should.
     */

    private void run(Phase lvl, int levelNum,int time, String infoText){
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
        info = Board.getInfo();
        infoTextLabel = Board.getInfoText(infoText);
        level = Board.getLevelLabel(levelNum);
        chips= Board.getChipLabel(numOfChips);
        var inventory = Board.getInventory();
        JPanel panel = new JPanel(null);
        info.add(infoTextLabel);
        setBackGround();
       
        //Initalize Timer.
        timerLabel.setBounds(630, 140,60, 30);
        startTimer(time,model,chips);

        //Pause Dialog Box.
        pause = Board.getPause();

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
                if(e.getKeyCode() == Controller.getCode(Controller.characterControls[0])){action(replay,model,"Up",()->model.chap().up());}
                if(e.getKeyCode() == Controller.getCode(Controller.characterControls[1])){action(replay,model,"Down",()->model.chap().down());}
                if(e.getKeyCode() == Controller.getCode(Controller.characterControls[2])){action(replay,model,"Left",()->model.chap().left());}
                if(e.getKeyCode() == Controller.getCode(Controller.characterControls[3]) ){action(replay,model,"Right",()->model.chap().right());}
                if((e.getKeyCode() == KeyEvent.VK_X) && e.isControlDown()){
                    dispose();
                    System.exit(808);
                }   
                if((e.getKeyCode() == KeyEvent.VK_S) && e.isControlDown()){
                    Persistency.createPXML(model.maze().getTiles(), model.maze().getChap().getInvKeys(),count);
                    dispose();
                    System.exit(808);
                }   
                if((e.getKeyCode() == KeyEvent.VK_SPACE)){
                    removeKeyListener(this);
                    pause.setVisible(true);
                    timer.stop();
                } 
            }
        };
        if(ReplayListner == null && PlaybPlayListner == null){
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
    }  
    
}
