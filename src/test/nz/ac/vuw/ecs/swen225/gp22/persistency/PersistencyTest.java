package test.nz.ac.vuw.ecs.swen225.gp22.persistency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import nz.ac.vuw.ecs.swen225.gp22.persistency.*;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

public class PersistencyTest {
    @Test
    public void test1() {
        
        Tile[][] t = Persistency.readXML("level1");
        assertEquals(11, t.length);
        assertEquals(11, t[0].length);
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
               assert(t[i][j]!= null);
            }
        }
    }

    // @Test
    //     public void dirTest() {
    //       String currentDirectory = System.getProperty("user.dir");
    //       System.out.println("user.dir: " + currentDirectory);
    //   }

    @Test
    public void test2() {
        assertThrows(Exception.class,()->{Persistency.readXML("levelTest1");});
    }
    @Test
    public void test3() {
        assertThrows(Exception.class,()->{Persistency.readXML("levelTest2");});
    }
    @Test
    public void test4() {
        assertThrows(Exception.class,()->{Persistency.readXML("levelTest3");});
    }
    @Test
    public void test5() {
        assertThrows(Exception.class,()->{Persistency.readXML("levelTest4");});
    }
    @Test
    public void test6() {
        assertThrows(Exception.class,()->{Persistency.readXML("levelTest5");});
    }
    @Test
    public void test7() {
        assert(Persistency.readXML("level1") != null);
        Persistency.createPXML(Persistency.readXML("level1"));
    }

    @Test
    public void test8() {
        Tile[][] t = Persistency.readXML("level2");
        assertEquals(24, t.length);
        assertEquals(20, t[0].length);
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
               assert(t[i][j]!= null);
            }
        }
    }
    // @Test
    // public void test7() {
    //     Persistency.createPXML(Persistency.readXML("level1"));
    //     Tile[][] i = Persistency.readXML("level1");
    //     Tile[][] j = Persistency.readXML("levelPers");
    //     assertEquals(i,j);
    // }
}
