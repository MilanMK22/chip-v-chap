package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.File;
import java.io.IOException;
import java.util.List;

//import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.util.Stack;

public class ReadXMLFile {
  
    public static void main(String argv[])   
    {  
            String Name = "";
            int Level = 0;
            Stack<Action> moves = new Stack<Action>();
            Replay ReadReplay = new Replay();

        try {
            File inputFile = new File("Replays/Replay-16|09|22-16:20:54.xml");
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


         System.out.println(ReadReplay.getLevel() + "   level");
         System.out.println(ReadReplay.getName()+ "    name");


         ReadReplay.getMoves().stream().forEach(r -> {
            System.out.println(r.getName() + "(" + r.getNumPings() + ")");});

        ReadReplay.saveReplay();





}
}