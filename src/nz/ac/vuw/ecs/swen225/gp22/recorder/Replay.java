package nz.ac.vuw.ecs.swen225.gp22.recorder;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Stack;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;




public class Replay {
  
	private Stack<Action> moves;
	private int level;
    private String Name = "Replay-";
	

	public Replay(Stack<Action> moves, int level, String Name) {
		this.moves = moves;
		this.level = level;
        this.Name = this.Name + Name;
	}
	
	
	
	public Stack<Action> getMoves() {
		return moves;
	}

	public void setMoves(Stack<Action> moves) {
		this.moves = moves;
	}

	public int getLevel() {
		return level;
	}

    public String getName() {
		return Name;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Replay() {
		
	}

    public void saveReplay(){
        try{
            //root element
            Element replayElement = new Element("Replay");
            Document doc = new Document(replayElement);
           
            Element movesElement = new Element("Moves");
            
            Element levelNumElement = new Element("LevelNumber").setText("" + level);
            Element NameAndDate = new Element("NameAndDate").setText("" + Name);
             
            moves.stream().forEach(r -> {
                Element Action = new Element("Action");
                Element AName = new Element("ActionName").setText(r.name);
                Element numPings = new Element("NumPings").setText("" + r.numPings);
                Action.addContent(AName);
                Action.addContent(numPings);
                movesElement.addContent(Action);});
            
            //add elems
            doc.getRootElement().addContent(levelNumElement);
            doc.getRootElement().addContent(NameAndDate);
            doc.getRootElement().addContent(movesElement);

            XMLOutputter xmlOutput = new XMLOutputter();
            //setup printstream
            PrintStream writeLevel = new PrintStream(new FileOutputStream("Replays/" + Name + ".xml", false));
            // write xml
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc,writeLevel); 

         } catch(IOException e) {
            e.printStackTrace();
         }
    }
	
}

