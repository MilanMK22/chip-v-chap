package nz.ac.vuw.ecs.swen225.gp22.domain;

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
    public void setLocation(Point p){ this.location = p; }
    public void tick(){ this.state.tick(); }
}
interface MonsterState{
    public void tick();
}

class MappedMonster implements MonsterState{
    


    MappedMonster(String mapping){
        
    }


    @Override
    public void tick(){
           
    }

}