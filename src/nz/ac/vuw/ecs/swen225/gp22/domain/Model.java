package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;



public class Model {
    
    Maze maze;
    Chap chap;
    List<Entity> entities;
    
    public Model(Maze m){
        this.maze = m;
        this.chap = m.getChap();
        this.chap.setMaze(m);
        this.entities = m.stream().filter(t-> t.hasEntity()).map(t->t.getEntity()).toList();
    }

    public Chap chap(){ return this.chap; }
    public Maze maze(){ return this.maze; }
    public List<Entity> entities(){ return this.entities; }
    public void win(){}
    public void loss(){}
    public boolean onInfo(){ return maze.getTile(chap.getLocation()).getState() instanceof InfoTile; }

    public Maze getMaze() { return maze; }

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
