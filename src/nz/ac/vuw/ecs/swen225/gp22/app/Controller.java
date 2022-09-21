package nz.ac.vuw.ecs.swen225.gp22.app;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;
class Controller extends Keys{

  public Controller(Chap c, int[] keys){
    setAction(keys[0],() -> c.up(), () -> c.move(c.getLocation()));
    setAction(keys[1],() -> c.down(),() ->c.move(c.getLocation()));
    setAction(keys[2],() ->c.left(),() ->c.move(c.getLocation()));
    setAction(keys[3],() ->c.right(),() ->c.move(c.getLocation()));
  }
}