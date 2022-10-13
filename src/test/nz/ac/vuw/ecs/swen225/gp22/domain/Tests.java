package test.nz.ac.vuw.ecs.swen225.gp22.domain;
import static org.junit.Assert.*;

import java.util.stream.Collectors;

import org.junit.Test;

import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Model;
import nz.ac.vuw.ecs.swen225.gp22.domain.Monster;
import nz.ac.vuw.ecs.swen225.gp22.domain.Phase;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pickup.*;

public class Tests {

   

    @Test
    public void testAllKeys(){
        for(KEYCOLOR c: KEYCOLOR.values()){
            testKeyInvalid(c);
            testKeyValid(c);
        }
    }

    @Test
    public void mazeTest(){
        
        Maze m = new Maze(buildTestMaze());
        Chap c = new Chap(new Point(0,0));
        c.setMaze(m);
        m.getTile(0, 0).setEntity(c);
        c.move(c.getLocation().right());
        c.move(c.getLocation().right());
        c.move(c.getLocation().right());
    }

    @Test
    public void modelMazeTest1(){
        Model m = new Model(new Maze(buildTestMaze()));
        m.chap().right();
        m.chap().right();
        m.chap().right();
    }    
    @Test
    public void modelMazeTest2(){
        Model m = new Model(new Maze(buildTestMaze()));
        m.chap().right();
        m.chap().right();
        m.chap().down();
        assertEquals(m.chap().getLocation(), new Point(2,0));
    }
    @Test
    public void testMonsterVsChap1(){
        Model m = new Model(new Maze(buildTestMaze2()));
        for (int i = 0; i<140; i++){
            m.tick();
        }
        assertNotEquals(m.chap(), m.maze().getTile(m.chap().getLocation()).getEntity());
    }
    @Test
    public void testMonsterVsChap2(){
        Model m = new Model(new Maze(buildTestMaze2()));
        for (int i = 0; i<120; i++){
            m.tick();
        }
        m.chap().right();
        assertNotEquals(m.chap(), m.maze().getTile(1, 0).getEntity());
    }

    @Test
    public void testInfoTile(){
        Model m = new Model(new Maze(buildCustomMaze(Tile.infoTile(new Point(2,0)))));
        m.chap().right();
        m.chap().right();
        assertTrue("Info Tile State", m.onInfo());
    }

    @Test
    public void testWins1(){
        Phase p = Phase.level1(()->assertTrue(true), ()->assertTrue(false));
        Model m = p.model();
        completeLevel1(m.chap());
        m.tick();
    }

    @Test
    public void testWins2(){
        Phase p = Phase.level2(()->assertTrue(true), ()->assertTrue(false));
        Model m = p.model();
        Point exit = m.maze().stream().filter(t->t.isExit()).map(t->t.getLocation()).findFirst().get();
        m.chap().move(exit);
        m.tick();
    }


    @Test
    public void testChar(){
        Maze m = new Maze(buildTestMaze3());
        String temp = m.stream().map(t-> String.valueOf(t.getChar())).collect(Collectors.joining());
        assertEquals("CoiBYGRbygrltXM", temp);
    }

    @Test
    public void testMonsterString(){
        String input = "1,12,uuuuuddddd";
        Tile t = Tile.monsterTile(null, input);
        Monster m = (Monster) t.getEntity();
        assertEquals(input, m.movesToString());
    }


    /*=============================
     * UTILITY METHODS FOR TESTS *|
    =============================*/
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

    public Tile[][] buildTestMaze2(){
        Tile[][] testMaze = new Tile[][]{
            new Tile[]{
                Tile.chapTile(new Point(0,0)),
                Tile.freeTile(new Point(1,0)),
                Tile.freeTile(new Point(2,0)),
                Tile.freeTile(new Point(3,0)),
                Tile.freeTile(new Point(4,0)),
                Tile.freeTile(new Point(5,0)),
                Tile.freeTile(new Point(6,0)),
                Tile.monsterTile(new Point(7,0), "0,0,l")
            }
        };
        return testMaze;
    }

    public Tile[][] buildTestMaze3(){
        Tile[][] testMaze = new Tile[][]{
            new Tile[]{
                Tile.chapTile(new Point(0,0)),
                Tile.freeTile(new Point(1,0)),
                Tile.infoTile(new Point(2, 0)),
                Tile.lockedDoorTile(new Point(3,0), KEYCOLOR.BLUE),
                Tile.lockedDoorTile(new Point(4,0), KEYCOLOR.YELLOW),
                Tile.lockedDoorTile(new Point(5,0), KEYCOLOR.GREEN),
                Tile.lockedDoorTile(new Point(6,0), KEYCOLOR.RED),
                Tile.keyTile(new Point(7,0), KEYCOLOR.BLUE),
                Tile.keyTile(new Point(8,0), KEYCOLOR.YELLOW),
                Tile.keyTile(new Point(9,0), KEYCOLOR.GREEN),
                Tile.keyTile(new Point(10,0), KEYCOLOR.RED),
                Tile.exitLockTile(new Point(11,0)),
                Tile.treasureTile(new Point(12,0)),
                Tile.exitTile(new Point(13,0)),
                Tile.monsterTile(new Point(14,0), "0,0,l")
            }
        };
        return testMaze;
    }

    public Tile[][] buildCustomMaze(Tile t){
        Tile[][] testMaze = new Tile[][]{
            new Tile[]{
                Tile.chapTile(new Point(0,0)),
                Tile.freeTile(new Point(1,0)),
                t
            }
        };
        return testMaze;
    }
    
    public void testKeyInvalid(KEYCOLOR color){
        Chap c = new Chap(new Point(0,0));
        c.pickUpKey(new Pickup().new Key(new Point(0,0), color));
        
        assertFalse("Invalid Key", c.useKey(KEYCOLOR.values()[(color.ordinal()+1)%4]));
    }
    public void testKeyValid(KEYCOLOR color){
        Chap c = new Chap(new Point(0,0));
        c.pickUpKey(new Pickup().new Key(new Point(0,0), color));
        assertTrue("Valid Key", c.useKey(color));
    }

    public void completeLevel1(Chap c){
        c.up();c.up();
        c.right();c.right();
        c.down();c.down();c.down();c.down();
        c.left();c.left();c.left();c.left();
        c.up();c.up();c.up();c.up();
        c.left();c.left();
        c.down();c.up();
        c.right();c.right();
        c.up();c.up();
        c.left();
        c.right();
        c.down();c.down();
        c.right();c.right();c.right();c.right();
        c.up();c.up();
        c.right();
        c.left();
        c.down();c.down();
        c.right();c.right();
        c.down();
        c.up();
        c.left();c.left();c.left();c.left();
        c.up();c.up();
    }
    
}
