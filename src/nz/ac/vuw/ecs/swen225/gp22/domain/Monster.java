package nz.ac.vuw.ecs.swen225.gp22.domain;

import imgs.Img;
import java.awt.image.BufferedImage;

public class Monster implements Entity{

    MonsterState state;
    Point location;

    Monster(Point location, String moves){
        this.location = location;
        this.state = new MappedMonster(moves);
    }

    @Override
    public Point getLocation() { return location; }
    public void setState(MonsterState s){ this.state = s; }
    private void setLocation(Point p){ this.location = p; }
    public void tick(Maze m){ state.tick(m, this); }
    public BufferedImage getImage(){ return Img.Marco.image; }
}




interface MonsterState{
    public void tick(Maze maze, Monster m);
}

class MappedMonster implements MonsterState{
    
    char[] moves;
    int tickcount;
    int cycle;

    MappedMonster(String mapping){
        moves = mapping.toCharArray();
        tickcount = 0;
        cycle = 0;
    }

    @Override
    public void tick(Maze maze, Monster m){
        tickcount += 1;
        if(tickcount >= 20){
            tickcount = 0;
            if(cycle >= moves.length){
                cycle = 0;
            }
            move(moveFromChar(moves[cycle], m), maze, m);
        }
    }

    
    private Point moveFromChar(char move, Monster m){
        switch (move){
            case 'u': return m.getLocation().up();
            case 'd': return m.getLocation().down();
            case 'l': return m.getLocation().left();
            case 'r': return m.getLocation().right();
            default: return m.getLocation();
        }
    }

    private void move(Point p, Maze maze, Monster m){
        if(maze.getTile(p).isFree()){
            maze.getTile(m.getLocation()).removeEntity();
            maze.getTile(p).setEntity(m);
        }
        else throw new IllegalArgumentException("Monster "+ m+ " is trying to move to an illegal tile");
    }
    
}