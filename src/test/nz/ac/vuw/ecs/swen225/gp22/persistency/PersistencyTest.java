package test.nz.ac.vuw.ecs.swen225.gp22.persistency;

import static org.junit.Assert.assertThrows;

import org.junit.Test;


import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

public class PersistencyTest {
    @Test
    public void test1() {
       assertThrows(ArithmeticException.class, ()->{Tile[][] tiles = Persistency.readXML("level1");});
    }

    @Test
    public void test2() {
        assertThrows(Exception.class,()->{Tile[][] tiles = Persistency.readXML("levelTest1");});
    }
    @Test
    public void test3() {
        assertThrows(NegativeArraySizeException.class,()->{Tile[][] tiles = Persistency.readXML("levelTest2");});
    }
    @Test
    public void test4() {
        assertThrows(NegativeArraySizeException.class,()->{Tile[][] tiles = Persistency.readXML("levelTest3");});
    }
    @Test
    public void test5() {
        assertThrows(NegativeArraySizeException.class,()->{Tile[][] tiles = Persistency.readXML("levelTest4");});
    }
    @Test
    public void test6() {
        assertThrows(NegativeArraySizeException.class,()->{Tile[][] tiles = Persistency.readXML("levelTest5");});
    }
}
