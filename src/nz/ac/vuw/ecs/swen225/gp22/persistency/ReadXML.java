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

    public record gameData(int w, int h, String board, String description, String id, int tres){}

    static int wid,hei,tres;
    static String board, id;
    static String desc;

   public static void main(String[] args) throws Exception {
      try {
       
         File inputFile = new File("levels/level1.xml");
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
                case "id":
                    id = curr.getText();
                    break;
                case "tres":
                    tres =  Integer.parseInt(curr.getText());
                    break;
                default:
                    throw new Exception("malformed xml, unexpected element: "+curr.getText());
            }
         }

         if(board.length()!=wid*hei){
            throw new JDOMException("Malformed xml, board size does not match width*height");
            
         } else {
            System.out.println("check-passed\n");
            gameData d = new gameData(wid, hei, board, desc, id, tres);
            System.out.println("Width: "+d.w());
            System.out.println("Heigth: "+d.h());
            System.out.println("Desc: "+d.description());
            System.out.println("ID: "+d.id());
            System.out.println("Tresures: "+d.tres());
            System.out.println("BoardStr: "+d.board());
         }

        
      } catch(JDOMException e) {
         e.printStackTrace();
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
}

