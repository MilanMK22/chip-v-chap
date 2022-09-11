package src.domain;

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
}
