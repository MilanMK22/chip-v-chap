package nz.ac.vuw.ecs.swen225.gp22.app;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;


/*
 * This class will contain the levels within the game.
 */
record Level(Controller c) {
    static Level level1(Runnable next, Runnable back, int[] keys){
        Maze m = new Maze(){

            
          };
        Chap c = new Chap(m, new Point(5,5));

        return new Level(new Controller(c,keys)); 
    }
}
