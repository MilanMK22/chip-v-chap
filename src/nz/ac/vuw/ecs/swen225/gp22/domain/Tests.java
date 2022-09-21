package nz.ac.vuw.ecs.swen225.gp22.domain;
import static org.junit.Assert.*;
import org.junit.Test;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.*;

public class Tests {

    public Tile[][] buildTestMaze(){
        Tile[][] testMaze = new Tile[][]{
        new Tile[]{
            Tile.chapTile(new Point(0,0)),
            Tile.keyTile(new Point(1,0), KEYCOLOR.BLUE),
            Tile.lockedDoorTile(new Point(2,0), KEYCOLOR.BLUE),
            Tile.exitTile(new Point(3,0))
        }
        };
        return testMaze;
    }
    

    @Test
    public void keyTest1(){
        Chap c = new Chap(new Point(0,0));
        c.pickUpKey(new Pickup().new Key(new Point(0,0), KEYCOLOR.BLUE));

        assertFalse("Invalid Key",c.useKey(KEYCOLOR.RED));
    }
    @Test
    public void keyTest2(){
        Chap c = new Chap(new Point(0,0));
        c.pickUpKey(new Pickup().new Key(new Point(0,0), KEYCOLOR.BLUE));

        assertTrue("Valid Key",c.useKey(KEYCOLOR.BLUE));
    }

    @Test
    public void mazeTest(){
        
        Maze m = new Maze(buildTestMaze());
        Chap c = new Chap(new Point(0,0));
        c.setMaze(m);
        m.getTile(0, 0).setEntity(c);
        c.move(c.getLocation().down());
        c.move(c.getLocation().down());
        c.move(c.getLocation().down());
    }

    @Test
    public void modelMazeTest1(){
        Model m = new Model(new Maze(buildTestMaze()));
        m.chap().down();
        m.chap().down();
        m.chap().down();
    }    
    @Test
    public void modelMazeTest2(){
        Model m = new Model(new Maze(buildTestMaze()));
        m.chap().down();
        m.chap().down();
        m.chap().down();
        assertThrows(Error.class, () -> m.chap().left());
    }

}
