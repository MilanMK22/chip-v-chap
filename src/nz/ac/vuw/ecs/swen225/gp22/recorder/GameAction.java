package nz.ac.vuw.ecs.swen225.gp22.recorder;

public class GameAction {

    String name;   // name of action
    int numPings;  //number of pings since last action 


    public GameAction(String name, int pings){

        this.name = name;
        this.numPings = pings;

    }

    public String getName(){
        return name;
    }

    public int getNumPings(){
        return numPings;
    }
    
}
