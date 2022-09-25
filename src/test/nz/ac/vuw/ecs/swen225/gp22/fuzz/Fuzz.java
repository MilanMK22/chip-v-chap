package test.nz.ac.vuw.ecs.swen225.gp22.fuzz;

import nz.ac.vuw.ecs.swen225.gp22.app.Level;
import java.util.List;

import javax.swing.SwingUtilities;

import org.junit.Test;
import java.awt.event.KeyEvent;
// import java.awt.Robot;
// import java.awt.AWTException;
import java.awt.Canvas;

public class Fuzz{
    @Test
    public void test1(){
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Robot robot = null;
                // try{robot = new Robot();}
                // catch(AWTException e){e.printStackTrace();}
                
                //int[] array = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
                int[] array = {0, 1, 2, 3};
                Level level = Level.level1(null, null, array);
                List<String> possibleInput = List.of("up", "down", "left", "right");
                
                long startTime = System.currentTimeMillis();
                
                while(true){
                    switch(possibleInput.get((int)Math.random()*4)){
                        case "up":
                        // robot.keyPress(KeyEvent.VK_UP);
                        // robot.keyRelease(KeyEvent.VK_UP);
                            KeyEvent upkey = new KeyEvent(new Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  0,'Z');
                            level.c().keyPressed(upkey);
                            level.c().keyReleased(upkey);
                            break;
                        case "down":
                        // robot.keyPress(KeyEvent.VK_DOWN);
                        // robot.keyRelease(KeyEvent.VK_DOWN);
                            KeyEvent downkey = new KeyEvent(new Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  1,'Z');
                            level.c().keyPressed(downkey);
                            level.c().keyReleased(downkey);
                            break;
                        case "left":
                        // robot.keyPress(KeyEvent.VK_LEFT);
                        // robot.keyRelease(KeyEvent.VK_LEFT);
                            KeyEvent leftkey = new KeyEvent(new Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  2,'Z');
                            level.c().keyPressed(leftkey);
                            level.c().keyReleased(leftkey);
                            break;
                        case "right":
                        // robot.keyPress(KeyEvent.VK_RIGHT);
                        // robot.keyRelease(KeyEvent.VK_RIGHT);
                            KeyEvent rightkey = new KeyEvent(new Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  3,'Z');
                            level.c().keyPressed(rightkey);
                            level.c().keyReleased(rightkey);
                            break;
                    }
                    if(System.currentTimeMillis() >= startTime + 60000) return;
                }});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        @Test
        public void test2(){
            
        }
    }