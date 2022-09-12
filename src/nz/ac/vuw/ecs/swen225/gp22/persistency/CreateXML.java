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
            Element widthElement = new Element("width").setText("9");
            //height element
            Element heightElement = new Element("height").setText("16");
            //board element
            Element boardElement = new Element("board").setText("** * *** **** *");
            //description element
            Element descElement = new Element("desc").setText("level 1: easy");
            //add elems
            doc.getRootElement().addContent(widthElement);
            doc.getRootElement().addContent(heightElement);
            doc.getRootElement().addContent(boardElement);
            doc.getRootElement().addContent(descElement);
            XMLOutputter xmlOutput = new XMLOutputter();
            //setup printstream
            PrintStream writeLevel = new PrintStream(new FileOutputStream("levelPers.xml", false));
            // write xml
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc,writeLevel); 

         } catch(IOException e) {
            e.printStackTrace();
         }
    }
}
