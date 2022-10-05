package test.nz.ac.vuw.ecs.swen225.gp22.fuzz;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.app.*;
import nz.ac.vuw.ecs.swen225.gp22.persistency.*;

import java.util.List;
import javax.swing.SwingUtilities;

import org.junit.Ignore;
import org.junit.Test;
import java.awt.event.KeyEvent;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Canvas;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

//! to make it smart do weighted prob. with ints assinged to each move eg. <25 = up, <50 = down etc.
//! once a command is called reset the int to something new, eg if up has happened - new up <24
//! instead of switch
/** 
 * this is the class reresenting the Fuzz testing module
 * 
 * @method test1
 * @method test2
 * @author Ilya Mashkov
 */
public class Fuzz{
    // @Ignore
    // public void test1(){
    //     try {
    //         SwingUtilities.invokeAndWait(() -> {
    //             // Robot robot = null;
    //             // try{robot = new Robot();}
    //             // catch(AWTException e){e.printStackTrace();}
                
    //             //int[] array = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
    //             int[] array = {0, 1, 2, 3};
    //             //! Level level = Level.level1(null, null, array);
    //             //! assert level.c() != null;
    //             List<String> possibleInput = List.of("up", "down", "left", "right");
                
    //             long startTime = System.currentTimeMillis();
                
    //             while(true){
    //                 //! always changng to 0 so only goes up
    //                 switch(possibleInput.get((int)Math.random()*4)){
    //                     case "up":
    //                     // robot.keyPress(KeyEvent.VK_UP);
    //                     // robot.keyRelease(KeyEvent.VK_UP);
    //                         KeyEvent upkey = new KeyEvent(new Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  0,'Z');
    //                         level.c().keyPressed(upkey);
    //                         level.c().keyReleased(upkey);
    //                         break;
    //                     case "down":
    //                     // robot.keyPress(KeyEvent.VK_DOWN);
    //                     // robot.keyRelease(KeyEvent.VK_DOWN);
    //                         KeyEvent downkey = new KeyEvent(new Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  1,'Z');
    //                         level.c().keyPressed(downkey);
    //                         level.c().keyReleased(downkey);
    //                         break;
    //                     case "left":
    //                     // robot.keyPress(KeyEvent.VK_LEFT);
    //                     // robot.keyRelease(KeyEvent.VK_LEFT);
    //                         KeyEvent leftkey = new KeyEvent(new Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  2,'Z');
    //                         level.c().keyPressed(leftkey);
    //                         level.c().keyReleased(leftkey);
    //                         break;
    //                     case "right":
    //                     // robot.keyPress(KeyEvent.VK_RIGHT);
    //                     // robot.keyRelease(KeyEvent.VK_RIGHT);
    //                         KeyEvent rightkey = new KeyEvent(new Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  3,'Z');
    //                         level.c().keyPressed(rightkey);
    //                         level.c().keyReleased(rightkey);
    //                         break;
    //                 }
    //                 if(System.currentTimeMillis() >= startTime + 60000) return;
    //             }});
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }

        @Test
        public void test1test(){
            try{
                SwingUtilities.invokeAndWait(() -> {
                    //! get tiles, make maze using tile
                    //! get the chap from maze

                    ChipVsChap chipVchap = new ChipVsChap();
                    Chap chap = chipVchap.getChap();
                    List<String> possibleInput = List.of("up", "down", "left", "right");

                    // setting the start time
                    long startTime = System.currentTimeMillis();

                    while (true) {
                        // getting random possible input
                        switch(possibleInput.get(ThreadLocalRandom.current().nextInt(1, 3 + 1))){
                            case "up":
                                chap.up();
                                break;
                            case "down":
                                chap.down();
                                break;
                            case "left":
                                chap.left();
                                break;
                            case "right":
                                chap.right();
                                break;
                        }
                        if(System.currentTimeMillis() >= startTime + 10000) return;
                    }
                });
            }
            catch (Exception e) { e.printStackTrace(); throw new IllegalArgumentException("test 1 failed", e);}
        }

        @Test
        public void robotTest(){
            try{
                Robot robot = null;
                try{robot = new Robot();}
                catch(Exception e){e.printStackTrace(); throw new IllegalArgumentException("Robot not created", e);}

                List<String> possibleInput = List.of("up", "down", "left", "right");

                // setting the start time
                long startTime = System.currentTimeMillis();
                Runtime runtime = Runtime.getRuntime();
                String command = "open /Desktop/Swen225/group proj/chip-vs-chap/src/nz/ac/vuw/ecs/swen225/gp22/app/Main.java";
                runtime.exec(command);
                // wait 20 seconds 
                robot.delay(2000);


                while (true) {
                    // getting random possible input
                    switch(possibleInput.get(ThreadLocalRandom.current().nextInt(1, 3 + 1))){
                        case "up":
                            robot.keyPress(KeyEvent.VK_UP);
                            robot.keyRelease(KeyEvent.VK_UP);
                            break;
                        case "down":
                            robot.keyPress(KeyEvent.VK_DOWN);
                            robot.keyRelease(KeyEvent.VK_DOWN);
                            break;
                        case "left":
                            robot.keyPress(KeyEvent.VK_LEFT);
                            robot.keyRelease(KeyEvent.VK_LEFT);
                            break;
                        case "right":
                            robot.keyPress(KeyEvent.VK_RIGHT);
                            robot.keyRelease(KeyEvent.VK_RIGHT);
                            break;
                    }
                    if(System.currentTimeMillis() >= startTime + 10000) return;
                }

            }
            catch (Exception e) { e.printStackTrace(); throw new IllegalArgumentException("test 1 fail", e);}


        }
        
        @Test
        public void test2(){
            
        }
    }