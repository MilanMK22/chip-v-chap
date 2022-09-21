package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

public class Model {
    
    Maze maze;
    Chap chap;
    List<Entity> entities;
    
    public Model(Maze m, Chap c){
        this.maze = m;
        this.chap = c;
        c.setMaze(m);
        this.entities = m.stream().filter(t-> t.hasEntity()).map(t->t.getEntity()).toList();
    }

    public Chap chap(){ return this.chap; }
    public Maze maze(){ return this.maze; }

    private void win(){
        
    }
    private void loss(){

    }

    public void tick(){
        entities.forEach(e->e.tick());
        if(maze.getTile(chap.getLocation()).isExit()){
            win();
        }
    }
}
