package nz.ac.vuw.ecs.swen225.gp22.domain;

import imgs.Img;
import java.awt.image.BufferedImage;


public class Monster implements Entity{

    MonsterState state;
    Point location;
    DIRECTION direction;
    public enum DIRECTION{
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    Monster(Point location, String moves){
        this.location = location;
        this.state = new MappedMonster(moves);
        this.direction = DIRECTION.DOWN;
    }

    @Override
    public Point getLocation() { return location; }
    public void setState(MonsterState s){ this.state = s; }
    public void setLocation(Point p){ this.location = p; }
    public void setDirection(String d){ this.direction = DIRECTION.valueOf(d); }
    public void tick(Maze m){ state.tick(m, this); }
    public char toChar(){ return 'M'; }
    public String movesToString(){ return state.movesToString(); }
    public BufferedImage getImage(){ 
        switch(this.direction){
            case DOWN: return Img.craigfront.image;
            case UP: return Img.craigback.image;
            case LEFT: return Img.craigL.image;
            case RIGHT: return Img.craigR.image;
            default: return Img.craigfront.image;
        }
    }
}




interface MonsterState{
    public void tick(Maze maze, Monster m);
    public String movesToString();
}

class MappedMonster implements MonsterState{
    String mapping;
    char[] moves;
    int tickcount;
    int cycle;

    MappedMonster(String mapping){
        this.mapping = mapping;
        mappingToMoves();
    }

    @Override
    public void tick(Maze maze, Monster m){
        tickcount ++;
        if(tickcount >= 20){
            tickcount = 0;
            if(cycle >= moves.length){
                cycle = 0;
            }
            move(moveFromChar(moves[cycle], m), maze, m);
            cycle ++;
        }
    }

    public String movesToString(){
        return tickcount + "," + cycle + "," + String.copyValueOf(moves);
    }

    private void mappingToMoves(){
        if(mapping == null){ tickcount = 0; cycle = 0; moves = "udud".toCharArray(); return; }
        String[] temp = mapping.split(",");
        if(temp.length != 3){ tickcount = 0; cycle = 0; moves = "udud".toCharArray(); return; }
        else{
            assert temp.length == 3;
            tickcount = Integer.parseInt(temp[0]);
            cycle = Integer.parseInt(temp[1]);
            moves = temp[2].toCharArray();
        }
    }
    
    private Point moveFromChar(char move, Monster m){
        switch (move){
            case 'u': m.setDirection("UP"); return m.getLocation().up();
            case 'd': m.setDirection("DOWN");return m.getLocation().down();
            case 'l': m.setDirection("LEFT");return m.getLocation().left();
            case 'r': m.setDirection("RIGHT");return m.getLocation().right();
            default: m.setDirection("DOWN");return m.getLocation();
        }
    }

    private void move(Point p, Maze maze, Monster m){
        if(maze.getTile(p).isFree() && !maze.getTile(p).hasPickup()){
            maze.getTile(m.getLocation()).removeEntity();
            maze.getTile(p).setEntity(m);
            m.setLocation(p);
        }
        else throw new IllegalArgumentException("Monster "+ m+ " is trying to move to an illegal tile");
    }
}