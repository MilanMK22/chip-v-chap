package src.app;
import src.domain.*;

import java.awt.Dimension;
import java.awt.Graphics;

class Chap extends ControllableDirection{
  private Point location;
  Chap(Point location){ this.location=location; }  
  public Point location(){ return location; }
  public void location(Point p){ location=p; }


  public void draw(Graphics g,Point center,Dimension size){
    // waiting on render
    }
}