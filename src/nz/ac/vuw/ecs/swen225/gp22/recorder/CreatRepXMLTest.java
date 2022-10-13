package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.LinkedList;
import java.util.Stack;

// Just a test to make sure a replay can be turned into an XML
public class CreatRepXMLTest {



    public static void main(String[] args) throws Exception {
        
        Replay R2 = new Replay(new LinkedList<GameAction>(),1, "");
        R2.addMove(new GameAction("UP", 30));
        R2.addMove(new GameAction("UP", 30));
        R2.saveReplay();

}

}