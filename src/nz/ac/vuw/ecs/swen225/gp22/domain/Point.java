package nz.ac.vuw.ecs.swen225.gp22.domain;

public class Point {
    int x;
    int y;

    Point(int xpos, int ypos){
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
}
