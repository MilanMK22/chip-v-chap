package nz.ac.vuw.ecs.swen225.gp22.recorder;

/**
 * Class for each Action that was in the game
 */
public class GameAction {

    String name;   // name of action
    int Time;  //Time the action occured

    /**
     * 
     * @param name The name of the action "UP,DOWN..."
     * @param Time The Time the action occured
     */
    public GameAction(String name, int Time){

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
     * @return get the time the moved occured
     */

    public int getTime(){
        return Time;
    }
    
}
