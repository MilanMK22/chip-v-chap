package nz.ac.vuw.ecs.swen225.gp22.app;

import java.util.function.Function;

abstract class ControllableDirection{
  private Direction direction=Direction.NONE;
  public Direction direction(){ return direction; }
  public void direction(Direction d){ direction=d; }
  public Runnable set(Function<Direction,Direction> f){
    return ()->direction=f.apply(direction);
  }
}