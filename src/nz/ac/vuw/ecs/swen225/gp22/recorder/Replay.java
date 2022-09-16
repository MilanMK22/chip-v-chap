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
  
	private Stack<String> moves;
	private int level;
	

	public Replay(Stack<String> moves, int level) {
		this.moves = moves;
		this.level = level;
	}
	
	
	
	public Stack<String> getMoves() {
		return moves;
	}

	public void setMoves(Stack<String> moves) {
		this.moves = moves;
	}

	public int getLevel() {
		return level;
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
            //width element
            Element movesElement = new Element("Moves");
            //height element
            Element levelNumElement = new Element("LevelNumber").setText("" + level);
             
            moves.stream().forEach(r -> movesElement.addContent(new Element(r)));
            
            //add elems
            doc.getRootElement().addContent(levelNumElement);
            doc.getRootElement().addContent(movesElement);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            //setup printstream
            PrintStream writeLevel = new PrintStream(new FileOutputStream("Replay-Level:" + level + ".xml", false));
            // write xml
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc,writeLevel); 

         } catch(IOException e) {
            e.printStackTrace();
         }
    }
	
}

