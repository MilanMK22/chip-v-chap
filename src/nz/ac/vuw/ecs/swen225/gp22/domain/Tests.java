package nz.ac.vuw.ecs.swen225.gp22.domain;
import static org.junit.Assert.*;
import org.junit.Test;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.*;

public class Tests {

    public Tile[][] buildTestMaze(){
        Tile[][] testMaze = new Tile[][]{
        new Tile[]{
            new Tile(new FreeTile(), new Point(0,0)),
            new Tile(new Pickup().new Key(new Point(1,0),KEYCOLOR.BLUE), new KeyTile(), new Point(1,0)),
            new Tile(new LockedDoorTile(KEYCOLOR.BLUE), new Point(2,0)),
            new Tile(new FreeTile(), new Point(3,0))
        }
        };
        return testMaze;
    }
    

    @Test
    public void keyTest1(){
        Chap c = new Chap(new Maze(), new Point(0,0));
        c.pickUpKey(new Pickup().new Key(new Point(0,0), KEYCOLOR.BLUE));

        assertFalse("Invalid Key",c.useKey(KEYCOLOR.RED));
    }
    @Test
    public void keyTest2(){
        Chap c = new Chap(new Maze(), new Point(0,0));
        c.pickUpKey(new Pickup().new Key(new Point(0,0), KEYCOLOR.BLUE));

        assertTrue("Valid Key",c.useKey(KEYCOLOR.BLUE));
    }

    @Test
    public void mazeTest(){
        
        Maze m = new Maze(buildTestMaze());
        Chap c = new Chap(m, new Point(0,0));
        m.getTile(0, 0).setEntity(c);
        c.move(c.getLocation().down());
        c.move(c.getLocation().down());
        c.move(c.getLocation().down());
    }
}
