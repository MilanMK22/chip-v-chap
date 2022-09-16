package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.Stack;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  

// Just a test to make sure a replay can be turned into an XML
public class CreatRepXML {



    public static void main(String[] args) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd|MM|yy-HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Stack<Action> s = new Stack<Action>();
        s.push(new Action("UP", 30));
        s.push(new Action("Down", 45));
        s.push(new Action("left", 60));
        s.push(new Action("right", 20));
        s.push(new Action("down", 90));
        s.push(new Action("UP", 30));
        s.push(new Action("Down", 45));
        s.push(new Action("left", 60));
        s.push(new Action("right", 20));
        s.push(new Action("down", 90));
	    
        Replay R = new Replay(s,1,dtf.format(now));
        R.saveReplay();
}

}