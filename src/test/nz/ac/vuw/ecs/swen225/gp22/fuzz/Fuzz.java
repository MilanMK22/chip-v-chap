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
    *
    * @param level the level of the game
    */
    @Test
    public void test1(){ levelTest(1); }

    /**
     * Test 2 test the second level of the game in the same way Test 1 does.
     * 
     * @param level the level of the game
     */
    @Test
    public void test2(){ levelTest(2); }

    /**
     * A general tester method that gets called in the actual test methods. 
     * As both tests are very similar the only changing variable is the level of the game so this helps save space
     * 
     * @param level the level of the game
     */
    public void levelTest(int level){
        try{
            SwingUtilities.invokeAndWait(() -> {
                ChipVsChap chipvchap = new ChipVsChap();
                chipvchap.fuzzModel(level);                                       // setting the model to be the level being tested on
                double up = 0.25, down = 0.25, left = 0.25;                     // initai
                int upmove = 0, downmove = 0, rightmove = 0, leftmove = 0;
                long time = 0;
                int x = 0;                                                        // for counting the number of moves done
                long startTime = System.currentTimeMillis();                      // setting the start time
                chipvchap.unvisitedTiles();
                
                while (true) {
                    x++;
                    chipvchap.tick();
                    double randomNum = Math.random();
                    long milliseconds = System.currentTimeMillis();
                    time = (milliseconds - startTime + 1000)/1000;

                    if(randomNum < up) { chipvchap.up(); probability("up"); upmove++;}
                    else if(randomNum < down + up) { chipvchap.down(); probability("down"); downmove++;}
                    else if(randomNum < left + down + up) { chipvchap.left(); probability("left"); leftmove++;}
                    else { chipvchap.right(); probability("right"); rightmove++;}
                    chipvchap.setVisitedTiles();

                    System.out.println("Test Runtime: " + time + "s");
                   
                    if(System.currentTimeMillis() >= startTime + 1000) { 
                        System.out.println("============ TEST COMPLETE! ============"); 
                        System.out.println("| Number of Moves Done: " + x + "            |");
                        System.out.println("| Level Has Been Played " + chipvchap.modelCount + " Times        |");
                        System.out.println("| Unique Tiles Visited In This Run: " + chipvchap.listOfVisitedTiles.stream().map(i -> i.getLocation()).distinct().toList().size() + " |");
                        System.out.println("| Unvisited Tiles In This Run: " + chipvchap.unvisitedTiles()+ "      |");
                        System.out.println("| Test Runtime: " + time + "s                     |");
                        System.out.println("========================================"); 
                        // System.out.println(chipvchap.chapToString()); 
                        System.out.println("Up Move: " + upmove+ " | Down Move: " + downmove + " | Left Move: " + leftmove + " | Right Move: " + rightmove);

                        //System.out.println("============ TEST COMPLETE! ============");
                        return; 
                    }
                }
            });
        } catch (Exception e) { e.printStackTrace(); throw new IllegalArgumentException("test 1 failed", e); }
    }

    /**
     * method to calculate probabilities of a random move
     * @param move the move being done 
     */
    public void probability(String move){
        double up = 0.25, down = 0.25, left = 0.25, right = 0.25; // initial move option probabilities
        if(move.equals("up")){ up -= 0.03; down += 0.01; left += 0.01; right += 0.01; }
        else if(move.equals("down")){ down -= 0.03; up += 0.01; left += 0.01; right += 0.01; }
        else if(move.equals("left")){ left -= 0.03; down += 0.01; up += 0.01; right += 0.01; }
        else if(move.equals("right")){ right -= 0.03; left += 0.01; right += 0.01; up +=0.01; }
        else { throw new IllegalArgumentException("Invalid move: " + move); }
    }
}