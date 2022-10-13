package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;


/**
 * @author Leo Gaynor: 300437633
 * <p>
 * The Model Class Provides a centralized place for all essential parts of the game state.
 * 
 * It provides methods for getting various parts of the game logic, and calls win and loss methods.
 */
public class Model {
    
    Maze maze;
    Chap chap;
    List<Entity> entities;
    
    /**
     * Create a Model from a maze. This will retrieve Chap and Other entities from the maze.
     * @param m The Maze to create Model from.
     */
    public Model(Maze m){
        this.maze = m;
        this.chap = m.getChap();
        this.chap.setMaze(m);
        this.entities = m.stream().filter(t-> t.hasEntity()).map(t->t.getEntity()).toList();
    }

    public Chap chap(){ return this.chap; }
    public Maze maze(){ return this.maze; }
    public List<Entity> entities(){ return maze.stream().filter(t-> t.hasEntity()).map(t->t.getEntity()).toList(); }
    public void win(){}
    public void loss(){}
    /**
     * Checks if chap is currently on the Information Tile.
     * @return The result of the boolean expression.
     */
    public boolean onInfo(){ return maze.getTile(chap.getLocation()).getState() instanceof InfoTile; }

    /**
     * Apply the tick() Method to all entities.
     * 
     * This method also runs win() or loss() if appropriate.
     */
    public void tick(){
        entities.forEach(e->e.tick(maze));
        if(maze.getTile(chap.getLocation()).isExit()){
            System.out.println("WIN!");
            win();
        }
        else if(maze.getTile(chap.getLocation()).getEntity() != chap) {
            System.out.println("LOSS!");
            loss();
        }
    }
}
