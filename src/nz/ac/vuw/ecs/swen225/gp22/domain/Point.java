package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author Leo Gaynor: 300437633
 * <p>
 * The Point class is a convinience class to track (x, y) positions within the maze.
 * 
 * It also provides Utility methods to get adjacent tiles in each direction.
 */
public class Point {
    int x;
    int y;

    public Point(int xpos, int ypos){
        this.x = xpos;
        this.y = ypos;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Point up(){
        return new Point(this.x, this.y-1);
    }

    public Point down(){
        return new Point(this.x, this.y+1);
    }

    public Point left(){
        return new Point(this.x-1, this.y);
    }

    public Point right(){
        return new Point(this.x+1, this.y);
    }
    public String toString(){
        return "("+x+", "+y+")";
    }

    public boolean equals(Object obj){
        if(obj != null){
            if(obj instanceof Point){
                Point temp = (Point)obj;
                return this.x == temp.getX() && this.y == temp.getY();
            }
        }
        return false;
    }
    public int hashCode() {
        return super.hashCode();
    }
}
