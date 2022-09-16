package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.Stack;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  


public class CreatRepXML {



    public static void main(String[] args) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd|MM|yy-HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
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
        Replay R = new Replay(s,1,dtf.format(now));
        R.saveReplay();
}

}