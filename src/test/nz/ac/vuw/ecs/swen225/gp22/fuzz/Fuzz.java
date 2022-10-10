package test.nz.ac.vuw.ecs.swen225.gp22.fuzz;

import nz.ac.vuw.ecs.swen225.gp22.app.*;
import javax.swing.SwingUtilities;
import org.junit.Test;
/** 
* this is the class reresenting the Fuzz testing module
* 
* @author Ilya Mashkov
*/
public class Fuzz{
    
    /**
    * Test1 tests the first level of the game by generating random inputs with a weighted probability.
    * Each possible move initially starts with a probability of 0.25. When a move is excecuted, the probability 
    * of that move is lowered by 0.03 and this is distributed evenly among the other moves making them more likely to occur.
    * 
    * This results in a more natural test of the game as it prevents the test from performing the same move a lot of 
    * times in a row. For example a human player would never click right 10 times in a row unintentinally. 
    */
    @Test
    public void test1(){
        try{
            SwingUtilities.invokeAndWait(() -> {
                double up = 0.25, down = 0.25, left = 0.25;
                
                // for counting the number of moves done
                int x = 0;
                
                // getting the chap
                //Chap chap = new ChipVsChap().getChap();
                ChipVsChap chipvchap = new ChipVsChap();
                long time = 0;
                
                // setting the start time
                long startTime = System.currentTimeMillis();
                
                // doing the moves
                while (true) {
                    double randomNum = Math.random();
                    x++;
                    long milliseconds = System.currentTimeMillis();
                    time = (milliseconds - startTime)/1000;
                    if(randomNum < up) { chipvchap.up(); up -= 0.03; down += 0.01; left += 0.01; }
                    else if(randomNum < down + up) { chipvchap.down(); down -= 0.03; up += 0.01; left += 0.01; }
                    else if(randomNum < left + down + up) { chipvchap.left(); left -= 0.03; down += 0.01; up += 0.01; }
                    else { chipvchap.right(); down += 0.01; left += 0.01; up += 0.01; }
                    
                    System.out.println("Number of moves done: " + x);
                    System.out.println("Test Runtime: " + time + "s");
                    if(System.currentTimeMillis() >= startTime + 60000) { return; }
                }
            });
        } catch (Exception e) { e.printStackTrace(); throw new IllegalArgumentException("test 1 failed", e); }
    }

    /**
     * Test 2 test the second level of the game in the same way Test 1 does.
     */
    @Test
    public void test2(){
        try{
            SwingUtilities.invokeAndWait(() -> {
                double up = 0.25, down = 0.25, left = 0.25;
                
                // for counting the number of moves done
                int x = 0;
                
                // getting the chap
                ChipVsChap chipvchap = new ChipVsChap();
                long time = 0;
                
                // setting the start time
                long startTime = System.currentTimeMillis();
                
                // doing the moves
                while (true) {
                    double randomNum = Math.random();
                    x++;
                    long milliseconds = System.currentTimeMillis();
                    time = (milliseconds - startTime)/1000;
                    if(randomNum < up) { chipvchap.upL2(); up -= 0.03; down += 0.01; left += 0.01; }
                    else if(randomNum < down + up) { chipvchap.downL2(); down -= 0.03; up += 0.01; left += 0.01; }
                    else if(randomNum < left + down + up) { chipvchap.leftL2(); left -= 0.03; down += 0.01; up += 0.01; }
                    else { chipvchap.rightL2(); down += 0.01; left += 0.01; up += 0.01; }
                    
                    System.out.println("Number of moves done: " + x);
                    System.out.println("Test Runtime: " + time + "s");
                    if(System.currentTimeMillis() >= startTime + 60000) { return; }
                }

            });
        } catch (Exception e) { e.printStackTrace(); throw new IllegalArgumentException("test 1 failed", e); }
    }
}



