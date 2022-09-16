package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.Stack;


public class CreatRepXML {



    public static void main(String[] args) throws Exception {
        Stack<String> s = new Stack<String>();
        s.push("left");
	    s.push("right");
	    s.push("down");
	    s.push("down");
        s.push("up");
        s.push("left");
	    s.push("right");
	    s.push("down");
	    s.push("down");
        s.push("up");
        Replay R = new Replay(s,1);
        R.saveReplay();
}

}