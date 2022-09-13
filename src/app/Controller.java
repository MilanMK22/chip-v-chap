package src.app;

class Controller extends Keys{

  public Controller(Chap c, int[] keys){
    setAction(keys[0],c.set(Direction::up),c.set(Direction::unUp));
    setAction(keys[1],c.set(Direction::down),c.set(Direction::unDown));
    setAction(keys[2],c.set(Direction::left),c.set(Direction::unLeft));
    setAction(keys[3],c.set(Direction::right),c.set(Direction::unRight));
  }
}