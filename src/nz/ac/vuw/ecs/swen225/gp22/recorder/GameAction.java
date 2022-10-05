package nz.ac.vuw.ecs.swen225.gp22.recorder;

/**
 * Class for each Action that was in the game
 */
public class GameAction {

    String name;   // name of action
    long Time;  //number of pings since last action 

    /**
     * 
     * @param name The name of the action "UP,DOWN..."
     * @param time The amount of Time since the last move
     */
    public GameAction(String name, long Time){

        this.name = name;
        this.Time = Time;

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

    public long getTime(){
        return Time;
    }
    
}
