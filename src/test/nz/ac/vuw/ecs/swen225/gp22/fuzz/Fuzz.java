package test.nz.ac.vuw.ecs.swen225.gp22.fuzz;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.app.*;
import javax.swing.SwingUtilities;
import org.junit.Test;
/** 
 * this is the class reresenting the Fuzz testing module
 * 
 * @author Ilya Mashkov
 */
public class Fuzz{
   
        @Test
        public void test1(){
            try{
                SwingUtilities.invokeAndWait(() -> {
                    double up = 0.25, down = 0.25, left = 0.25;
                   
                    // for counting the number of moves done
                    int x = 0;

                    // getting the chap
                    Chap chap = ChipVsChap.getChap();

                    // setting the start time
                    long startTime = System.currentTimeMillis();
                    
                    // doing the moves
                    while (true) {
                        double randomNum = Math.random();
                        x++;
                        
                        if(randomNum < up) { chap.up(); up -= 0.03; down += 0.01; left += 0.01; }
                        else if(randomNum < down + up) { chap.down(); down -= 0.03; up += 0.01; left += 0.01; }
                        else if(randomNum < left + down + up) { chap.left(); left -= 0.03; down += 0.01; up += 0.01; }
                        else { chap.right(); down += 0.01; left += 0.01; up += 0.01; }

                        System.out.println(x);
                        if(System.currentTimeMillis() >= startTime + 60000) { return; }
                    }
                });
            } catch (Exception e) { e.printStackTrace(); throw new IllegalArgumentException("test 1 failed", e);}
        }
    }

    

           