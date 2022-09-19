package nz.ac.vuw.ecs.swen225.gp22.app;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;
class Controller extends Keys{

  public Controller(Chap c, int[] keys){
    setAction(keys[0],() -> c.move(c.getLocation().up()), () -> c.move(c.getLocation()));
    setAction(keys[1],() -> c.move(c.getLocation().down()),() ->c.move(c.getLocation()));
    setAction(keys[2],() ->c.move(c.getLocation().left()),() ->c.move(c.getLocation()));
    setAction(keys[3],() ->c.move(c.getLocation().right()),() ->c.move(c.getLocation()));
  }
}