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
import java.util.List;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import javax.swing.JFileChooser;




public class Replay {
  
	private Stack<Action> moves;
	private int level;
    private String Name = "Replay--";
	

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

    public void addMove(Action move){
        moves.push(move);
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

    public static Replay readXML(){

    JFileChooser jfc = new JFileChooser("Replays/");
    jfc.showDialog(null,"Please Select the File");
    jfc.setVisible(true);
    File filename = jfc.getSelectedFile();
    System.out.println("File name "+filename.getName());

    
            String Name = "";
            int Level = 0;
            Stack<Action> moves = new Stack<Action>();
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
                Action A = new Action(direction, pings);
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

