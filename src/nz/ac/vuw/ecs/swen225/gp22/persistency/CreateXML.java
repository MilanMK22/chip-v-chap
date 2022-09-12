package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

//test class for writing xml output
public class CreateXML {
    public static void main(String[] args) throws Exception {
        try{
            //root element
            Element levelElement = new Element("level");
            Document doc = new Document(levelElement);
            //width element
            Element widthElement = new Element("width").setText("11");
            //height element
            Element heightElement = new Element("height").setText("11");
             //treasure element
             Element tresElement = new Element("tres").setText("5");
            //board element
            Element boardElement = new Element("board").setText("WWWWWWWWWWWWotoWXWotoWWWWGWlWGWWWWoBoooooRoWWtWboiorWtWWWWooCooWWWWoWgooogWoWWoWootooWoWWWWWWWWWWWWWoWooWooWoWWWWWWWWWWWW");
           
            // WallTile:	W
            // FreeTile:	o
            // BlueKey: 	b
            // RedKey:		r
            // GreenKey:	g
            // BlueDoor(L):	B
            // RedDoor(L):	R
            // GrnDoor(L):	G
            // Info:		i
            // Treasure:	t
            // ExitLock:	l
            // Exit:		X
            // Chap:		C


            //description element
            Element descElement = new Element("desc").setText("level 1: easy");
            //id element
            Element idElement = new Element("id").setText("level1");
            //add elems
            doc.getRootElement().addContent(widthElement);
            doc.getRootElement().addContent(heightElement);
            doc.getRootElement().addContent(boardElement);
            doc.getRootElement().addContent(descElement);
            doc.getRootElement().addContent(idElement);
            doc.getRootElement().addContent(tresElement);
            XMLOutputter xmlOutput = new XMLOutputter();
            //setup printstream
            PrintStream writeLevel = new PrintStream(new FileOutputStream("levels/level1.xml", false));
            // write xml
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc,writeLevel); 

         } catch(IOException e) {
            e.printStackTrace();
         }
    }
}
