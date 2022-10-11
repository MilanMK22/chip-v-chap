package nz.ac.vuw.ecs.swen225.gp22.recorder;
import java.awt.*;
import nz.ac.vuw.ecs.swen225.gp22.app.ChipVsChap;
import nz.ac.vuw.ecs.swen225.gp22.app.Keys;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Mapprint;
import nz.ac.vuw.ecs.swen225.gp22.renderer.printInventory;
import java.awt.event.*;


public class Recorder {

    public static void Auto(ChipVsChap cur){
        
        Replay rep = Replay.readXML();
    var replay = new Checkbox("2x Replay Speed");
    replay.setBounds(215, 5, 180, 20);
    replay.addItemListener(new ItemListener() {    
        public void itemStateChanged(ItemEvent e) {                 
            if(e.getStateChange()==1){
                cur.timer.setDelay(25);   
        }    
        else{
            cur.timer.setDelay(50);
        }
         }
     });    
    cur.add(replay);
    if(rep.getLevel() == 1){
        //one.run(); 
        cur.levelOne();
    }else{
        cur.levelTwo();
    }
    cur.removeKeyListener(cur.Replistner);
     ActionListener newone = e -> {
        if(rep.getMoves().isEmpty()){
            System.out.println("empty");
            cur.dispose();
            //System.exit(A
        }
        GameAction r = rep.getMoves().peek();

        if(r.getTime() == cur.totalticks){
            r = rep.getMoves().remove();
        try{
        if(r.getName().equals("Up")){             
            cur.action(null,cur.model,"Up",()->cur.model.chap().up());
        }else if(r.getName().equals("Down")){
            cur.action(null,cur.model,"Down",()->cur.model.chap().down());
        }else if(r.getName().equals("Left")){
            cur.action(null,cur.model,"Left",()->cur.model.chap().left());
        }else if(r.getName().equals("Right")){
            cur.action(null,cur.model,"Right",()->cur.model.chap().right());
        }
    }
    catch(Error b){
    }
    }  
    };
    cur.timer.addActionListener(newone);

    }

    public static void PlaybPlay(ChipVsChap cur){
        Replay rep = Replay.readXML();
        if(rep.getLevel() == 1){
            cur.levelOne(); 
        }else{
            cur.levelTwo();
        }
        cur.removeKeyListener(cur.Replistner);
        cur.timer.stop();
        Mapprint.printMap(cur.model, cur.background.getGraphics());
    
        KeyListener forward = new Keys(){
    
        public void keyPressed(KeyEvent e) {
    
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
    
                    if(cur.timePassed % 1000 == 0){
                        if(cur.count == 0){
                            cur.timer.stop();
                            ChipVsChap.timerLabel.setText("No time");
                        }else{
                            int minutes = cur.count /60;
                            int seconds = cur.count% 60;
                            ChipVsChap.timerLabel.setText(String.format("%d:%02d", minutes,seconds) );
                            cur.count --;
                        }
                    }
                    cur.timePassed += cur.delay;
                    cur.model.tick();
                    cur.totalticks++;
                    Mapprint.printMap(cur.model, cur.background.getGraphics());
                    printInventory.printIn(cur.model,cur.backgroundImage.getGraphics());
                    ChipVsChap.chips.setText("" + (ChipVsChap.numOfChips - cur.model.chap().heldTreasure()));
    
    
                    if(rep.getMoves().isEmpty()){
                        System.out.println("empty");
                        cur.dispose();
                        System.exit(ChipVsChap.ABORT);
                    }
                    GameAction r = rep.getMoves().peek();
            
                    if(r.getTime() == cur.totalticks){
                        r = rep.getMoves().remove();
                    try{
                    if(r.getName().equals("Up")){             
                        cur.action(null,cur.model,"Up",()->cur.model.chap().up());
                    }else if(r.getName().equals("Down")){
                        cur.action(null,cur.model,"Down",()->cur.model.chap().down());
                    }else if(r.getName().equals("Left")){
                        cur.action(null,cur.model,"Left",()->cur.model.chap().left());
                    }else if(r.getName().equals("Right")){
                        cur.action(null,cur.model,"Right",()->cur.model.chap().right());
                    }
                }
                catch(Error b){
                }
                }  
                }
            }
    
        };
        cur.addKeyListener(forward);

    }
    
}
