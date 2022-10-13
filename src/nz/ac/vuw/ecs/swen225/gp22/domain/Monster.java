package nz.ac.vuw.ecs.swen225.gp22.domain;

import imgs.Img;
import java.awt.image.BufferedImage;


/**
 * @author Leo Gaynor: 300437633
 * <p>
 * The Monster class implements Entity and represents a Monster within the maze.
 */
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

    /**
     * Create a monster at a Point 'location' in the maze.
     * It will move as described in the passed String 'moves'
     * @param location
     * @param moves
     */
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



/**
 * An interface for monster states.
 */
interface MonsterState{
    public void tick(Maze maze, Monster m);
    public String movesToString();
}

/**
 * A Mapped Monster is a MonsterState, it uses the mapping String to determine its behavior.
 */
class MappedMonster implements MonsterState{
    String mapping;
    char[] moves;
    int tickcount;
    int cycle;

    /**
     * Create a MappedMonster.
     * @param mapping to determine behavior.
     */
    MappedMonster(String mapping){
        this.mapping = mapping;
        mappingToMoves();
    }


    /**
     * Check when the next monster move should occur.
     */
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

    /**
     * return the Monster's current state (Utility for Saving games).
     */
    public String movesToString(){
        return tickcount + "," + cycle + "," + String.copyValueOf(moves);
    }

    /**
     * Decode the mapping into the correct Moveset for this monster.
     */
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
    
    /**
     * Get a Move from the next Character in the move list.
     * @param move The Character
     * @param m the Monster to move.
     * @return The Point to move to.
     */
    private Point moveFromChar(char move, Monster m){
        switch (move){
            case 'u': m.setDirection("UP"); return m.getLocation().up();
            case 'd': m.setDirection("DOWN");return m.getLocation().down();
            case 'l': m.setDirection("LEFT");return m.getLocation().left();
            case 'r': m.setDirection("RIGHT");return m.getLocation().right();
            default: m.setDirection("DOWN");return m.getLocation();
        }
    }

    /**
     * Move the Monster to the specified Point.
     * @param p The Point to move to.
     * @param maze The Maze that the monster is part on.
     * @param m The Monster to move.
     */
    private void move(Point p, Maze maze, Monster m){
        if(maze.getTile(p).isFree() && !maze.getTile(p).hasPickup()){
            maze.getTile(m.getLocation()).removeEntity();
            maze.getTile(p).setEntity(m);
            m.setLocation(p);
        }
        else throw new IllegalArgumentException("Monster "+ m+ " is trying to move to an illegal tile");
    }
}