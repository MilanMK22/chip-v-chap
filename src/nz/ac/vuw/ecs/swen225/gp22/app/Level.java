package nz.ac.vuw.ecs.swen225.gp22.app;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;


/*
 * This class will contain the levels within the game.
 */
public record Level(Controller c) {
    public static Level level1(Runnable next, Runnable back, int[] keys){
        Chap c = new Chap(new Point(5,5));
        // Maze cells = new Maze();
        // var m = new Model(){
        //   List<Entity> entities = List.of(c,s,new Monster(new Point(0,0)));
        //   public Chap[] chap(){ return c; }
        //   public List<Entity> entities(){ return entities; }
        //   public void remove(Entity e){ 
        //     entities = entities.stream()
        //       .filter(ei->!ei.equals(e))
        //       .toList();
        //   }
        //   public Cells cells(){ return cells; }
        // };
        return new Level(new Controller(c,keys)); 
    }
}
