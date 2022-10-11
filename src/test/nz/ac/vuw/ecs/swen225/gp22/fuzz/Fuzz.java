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

                ChipVsChap chipvchap = new ChipVsChap();
                double up = 0.25, down = 0.25, left = 0.25;
                int x = 0;  // for counting the number of moves done
                long time = 0;
                long startTime = System.currentTimeMillis(); // setting the start time
                chipvchap.unvisitedTiles(1);
                
                // doing the moves
                while (true) {
                    x++;
                    double randomNum = Math.random();
                    long milliseconds = System.currentTimeMillis();
                    time = (milliseconds - startTime)/1000;
                    if(randomNum < up) { chipvchap.up(1); probability("up"); }
                    else if(randomNum < down + up) { chipvchap.down(1); probability("down"); }
                    else if(randomNum < left + down + up) { chipvchap.left(1); probability("left"); }
                    else { chipvchap.right(1); probability("right"); }
                    chipvchap.setVisitedTiles(1);
                    

                    //! was trying to record visited tiles (SUCCESS) and make the chap prefer moving to unvisited tiles (unless all tiles around are visited)
                    //! but no clue how to check the tiles around him and get their visited status??

                    //! leo add a method to get the tile from above, bellow, left and right of the chap? - then check their visited status
                    //! also need to implement a way to recognise a wall and walk away from it 
                    
                    System.out.println("Number of moves done: " + x);
                    System.out.println("unique tiles visited: " + chipvchap.listOfVisitedTiles.stream().distinct().toList().size());
                    System.out.println("unvisited tiles: " + chipvchap.unvisitedTilesList.size());
                    System.out.println("Test Runtime: " + time + "s");
                    System.out.println("--------------------------------------");
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
                ChipVsChap chipvchap = new ChipVsChap();
                double up = 0.25, down = 0.25, left = 0.25; 
                int x = 0;
                long time = 0;
                long startTime = System.currentTimeMillis(); // setting the start time
                chipvchap.unvisitedTiles(2);
                
                while (true) {
                    x++;
                    double randomNum = Math.random();
                    long milliseconds = System.currentTimeMillis();
                    time = (milliseconds - startTime)/1000;
                    if(randomNum < up) { chipvchap.up(2); probability("up"); }
                    else if(randomNum < down + up) { chipvchap.down(2); probability("down"); }
                    else if(randomNum < left + down + up) { chipvchap.left(2); probability("left"); }
                    else { chipvchap.right(2); probability("up"); }
                    chipvchap.setVisitedTiles(2);
                    
                    System.out.println("Number of moves done: " + x);
                    System.out.println("unique tiles visited: " + chipvchap.listOfVisitedTiles.stream().distinct().toList().size());
                    System.out.println("unvisited tiles: " + chipvchap.unvisitedTilesList.size());
                    System.out.println("Test Runtime: " + time + "s");
                    System.out.println("--------------------------------------");
                    if(System.currentTimeMillis() >= startTime + 60000) { return; }
                }
            });
        } catch (Exception e) { e.printStackTrace(); throw new IllegalArgumentException("test 2 failed", e); }
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
        else if(move.equals("right")){right -= 0.03; left += 0.01; right += 0.01; up +=0.01; }
        else {throw new IllegalArgumentException("Invalid move: " + move);}
    }
}



