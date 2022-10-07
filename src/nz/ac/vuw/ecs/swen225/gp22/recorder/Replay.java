package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import javax.swing.JFileChooser;



/**
 * This class stores a Replay with a Queue, level number and name
 */
public class Replay {
  
	private Queue<GameAction> moves;
	private int level;
    private String Name = "Replay--";
	
/**
 * 
 * @param moves The moves that occured in the game e.g "Up,Down..."
 * @param level The level number that this game was recordered on
 * @param Name The Name of the Replay
 */
	public Replay(Queue<GameAction> moves, int level, String Name) {
		this.moves = moves;
		this.level = level;
        this.Name = this.Name + Name;
	}
	
	
	/**
     * 
     * @return the moves Queue 
     */
	public Queue<GameAction> getMoves() {
		return moves;
	}

    /**
     * 
     * @param moves set our moves list
     */

	public void setMoves(Queue<GameAction> moves) {
		this.moves = moves;
	}

    /**
     * 
     * @param move add a move to Queue
     */
    public void addMove(GameAction move){
        moves.add(move);
    }

    /**
     * 
     * @return the level our replay is on
     */

	public int getLevel() {
		return level;
	}
    /**
     * 
     * @return the replay name
     */

    public String getName() {
		return Name;
	}

    /**
     * 
     * @param level set the level of the replay
     */
	public void setLevel(int level) {
		this.level = level;
	}

    /**
     * Empty Constructor
     */

	public Replay() {
		
	}

    
    /**
     * Saves a replay to an XML file
     */
    public void saveReplay(){
        try{
            //root element

            String name = JOptionPane.showInputDialog(null, "Enter Replay Name");
            Name = Name + name + "--";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yy--HH-mm-ss");
            LocalDateTime now = LocalDateTime.now();
            Name = Name + dtf.format(now);;
            Element replayElement = new Element("Replay");
            Document doc = new Document(replayElement);
           
            Element movesElement = new Element("Moves");
            
            Element levelNumElement = new Element("LevelNumber").setText("" + level);
            Element NameAndDate = new Element("NameAndDate").setText("" + Name);
             
            moves.stream().forEach(r -> {
                Element Action = new Element("Action");
                Element AName = new Element("ActionName").setText(r.name);
                Element numPings = new Element("Time").setText("" + r.getTime());
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

    /**
     * 
     * @return return a Replay Object that was read from the XML file
     */
    public static Replay readXML(){

    JFileChooser jfc = new JFileChooser("Replays/");
    jfc.showDialog(null,"Please Select the File");
    jfc.setVisible(true);
    File filename = jfc.getSelectedFile();
    System.out.println("File name "+filename.getName());

    
            String Name = "";
            int Level = 0;
            Queue<GameAction> moves = new LinkedList<GameAction>();
            Replay ReadReplay = new Replay();

        try {
            File inputFile = new File("Replays/" + filename.getName());
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.getChildren();

            Level = Integer.parseInt(elements.get(0).getText());
            Name = elements.get(1).getText();
            Element RealMoves = elements.get(2);

            List<Element> MovesActions = RealMoves.getChildren();

            for (int i = 0; i < MovesActions.size(); i++) {    

                Element Action = MovesActions.get(i);
                List<Element> details = Action.getChildren();
                String direction = details.get(0).getText();
                int pings = Integer.parseInt(details.get(1).getText());
                GameAction A = new GameAction(direction, pings);
                moves.add(A);
            }

                ReadReplay = new Replay(moves,Level,Name);

           
         } catch(JDOMException e) {
            e.printStackTrace();
         } catch(IOException ioe) {
            ioe.printStackTrace();
         }


         return ReadReplay;



    }
	
}

