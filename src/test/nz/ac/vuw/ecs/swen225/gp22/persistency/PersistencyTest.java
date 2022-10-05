package test.nz.ac.vuw.ecs.swen225.gp22.persistency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import javax.swing.*;
import java.io.File;

import nz.ac.vuw.ecs.swen225.gp22.persistency.*;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

public class PersistencyTest {
    @Test
    public void test1() {
        
        Tile[][] t = Persistency.readXML("/Users/luke/chip-vs-chap/levels/level1.xml");
        assertEquals(11, t.length);
        assertEquals(11, t[0].length);
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
               assert(t[i][j]!= null);
            }
        }
    }

    @Test
    public void test2() {
        assertThrows(Exception.class,()->{Tile[][] tiles = Persistency.readXML("/Users/luke/chip-vs-chap/levels/levelTest1.xml");});
    }
    @Test
    public void test3() {
        assertThrows(Exception.class,()->{Tile[][] tiles = Persistency.readXML("/Users/luke/chip-vs-chap/levels/levelTest2.xml");});
    }
    @Test
    public void test4() {
        assertThrows(Exception.class,()->{Tile[][] tiles = Persistency.readXML("/Users/luke/chip-vs-chap/levels/levelTest3.xml");});
    }
    @Test
    public void test5() {
        assertThrows(Exception.class,()->{Tile[][] tiles = Persistency.readXML("/Users/luke/chip-vs-chap/levels/levelTest4.xml");});
    }
    @Test
    public void test6() {
        assertThrows(Exception.class,()->{Tile[][] tiles = Persistency.readXML("/Users/luke/chip-vs-chap/levels/levelTest5.xml");});
    }
    @Test
    public void test7() {
        assert(Persistency.readXML("/Users/luke/chip-vs-chap/levels/level1.xml") != null);
        Persistency.createPXML(Persistency.readXML("/Users/luke/chip-vs-chap/levels/level1.xml"));
    }

    @Test
    public void test8() {
        Tile[][] t = Persistency.readXML("/Users/luke/chip-vs-chap/levels/level2.xml");
        assertEquals(24, t.length);
        assertEquals(20, t[0].length);
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
               assert(t[i][j]!= null);
            }
        }
    }
}
