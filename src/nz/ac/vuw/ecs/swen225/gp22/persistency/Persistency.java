package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.*;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;

public class Persistency {
    
    public Tile[][] readXML(String level){
         int wid,hei,tres;
         String board, id;
         String desc;
        try {
            File inputFile = new File("levels/"+level+".xml");
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
            }
         } catch(JDOMException e) {
            e.printStackTrace();
         } catch(IOException ioe) {
            ioe.printStackTrace();
         }

         return new ArrayMaker(board, wid, hei).makeArray();
    }

    public void createPXML(Tile[][] tiles){

        try{
            //root element
            Element levelElement = new Element("level");
            Document doc = new Document(levelElement);
            //width element
            Element widthElement = new Element("width").setText(tiles.length);
            //height element
            Element heightElement = new Element("height").setText(tiles[0].length);
             //treasure element
             Element tresElement = new Element("tres").setText("5");
            //board element
            Element boardElement = new Element("board").setText("WWWWWWWWWWWWotoWXWotoWWWWGWlWGWWWWoBoooooRoWWtWboiorWtWWWWooCooWWWWoWgooogWoWWoWootooWoWWWWWWWWWWWWWoWooWooWoWWWWWWWWWWWW");
            //description element
            Element descElement = new Element("desc").setText("saved progress");
            //id element
            Element idElement = new Element("id").setText("levelSave");
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
