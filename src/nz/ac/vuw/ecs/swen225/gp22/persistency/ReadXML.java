package nz.ac.vuw.ecs.swen225.gp22.persistency;



import java.io.File;
import java.io.IOException;
import java.util.List;

//import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class ReadXML {

    public record gameData(int w, int h, String board, String description){}

    static int wid,hei;
    static String board;
    static String desc;

   public static void main(String[] args) throws Exception {
      try {
        long t1 = System.currentTimeMillis();
         File inputFile = new File("levelPers.xml");
         SAXBuilder saxBuilder = new SAXBuilder();
         Document document = saxBuilder.build(inputFile);
         Element rootElement = document.getRootElement();
         List<Element> elements = rootElement.getChildren();
         for (int i = 0; i < elements.size(); i++) {    
            Element curr = elements.get(i);
            switch (curr.getName()) {
                case "width":
                    wid = Integer.parseInt(curr.getText());
                    break;
                case "height":
                    hei = Integer.parseInt(curr.getText());
                    break;
                case "board":
                    board = curr.getText();
                    break;
                case "desc":
                    desc = curr.getText();
                    break;
                default:
                    throw new Exception("malformed xml, unexpected element: "+curr.getText());
            }
         }

         System.out.println("Root: "+rootElement.getName()+"\n---------------");
         System.out.println("Width: "+wid);
         System.out.println("Height: "+hei);
         System.out.println("Board: "+board);
         System.out.println("Descripton: "+desc);
        System.out.println("-----Total Time: " +(System.currentTimeMillis()-t1)+"ms-----");

        gameData d = new gameData(wid, hei, board, desc);

      } catch(JDOMException e) {
         e.printStackTrace();
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
}

