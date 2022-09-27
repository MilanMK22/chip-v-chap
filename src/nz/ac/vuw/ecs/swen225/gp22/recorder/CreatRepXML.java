package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.Stack;

// Just a test to make sure a replay can be turned into an XML
public class CreatRepXML {



    public static void main(String[] args) throws Exception {
        
        Replay R2 = new Replay(new Stack<Action>(),1, "");
        R2.addMove(new Action("UP", 30));
        R2.addMove(new Action("UP", 30));
        R2.saveReplay();

}

}