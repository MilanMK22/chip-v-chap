package nz.ac.vuw.ecs.swen225.gp22.domain;

import imgs.Img;
import java.awt.image.BufferedImage;

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
    private void setLocation(Point p){ this.location = p; }
    public void tick(){ state.tick(this); }
    public BufferedImage getImage(){ return Img.Marco.image; }
}




interface MonsterState{
    public void tick(Monster m);
}

class MappedMonster implements MonsterState{
    


    MappedMonster(String mapping){
        
    }


    @Override
    public void tick(Monster m){

    }

}