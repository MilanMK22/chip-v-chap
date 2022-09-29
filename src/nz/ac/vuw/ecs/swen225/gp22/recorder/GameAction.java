package nz.ac.vuw.ecs.swen225.gp22.recorder;

/**
 * Class for each Action that was in the game
 */
public class GameAction {

    String name;   // name of action
    int numPings;  //number of pings since last action 

    /**
     * 
     * @param name The name of the action "UP,DOWN..."
     * @param pings The amount of pings since the last move
     */
    public GameAction(String name, int pings){

        this.name = name;
        this.numPings = pings;

    }

    /**
     * 
     * @return The Name of the Action
     */
    public String getName(){
        return name;
    }

    /**
     * 
     * @return the amount of pings since the last move
     */

    public int getNumPings(){
        return numPings;
    }
    
}
