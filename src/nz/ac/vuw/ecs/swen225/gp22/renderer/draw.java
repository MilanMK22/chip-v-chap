package nz.ac.vuw.ecs.swen225.gp22.renderer;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;


/**
 * 
 * 
 * @author Jack Grunfeld
 * @version 1.0
 * draw method
 */
interface draw {
  /*
   * @param m
   */
    void ping(Maze m);
    /*
     * @param g
     * @param center
     * @param size
     */
     
    void draw(Graphics g, Point center, Dimension size);
    default void drawImg(BufferedImage img, Graphics g, Point center, Dimension size){
        var l = this.location();
        var lx = (l.getX()-center.getX())*15;
        var ly = (l.getY()-center.getY())*15;
        double iw = img.getWidth()/2d;
        double ih = img.getHeight()/2d;
        int w1 = (int)(lx-iw);
        int w2 = (int)(lx+iw);
        int h1 = (int)(ly-ih);
        int h2 = (int)(ly+ih);
        var isOut = h2<=0 || w2<=0 || h1>=size.height || w1>=size.width;
        if(isOut){ return; }
        g.drawImage(img,w1,h1,w2,h2,0,0,img.getWidth(),img.getHeight(),null);
        }

        /*
         * 
         * @return
         */
        Point location();
        default void location(Point p){
          throw new Error("This entity can not move");
        }
    }

       

    
    
    
    






