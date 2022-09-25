package test.nz.ac.vuw.ecs.swen225.gp22.fuzz;

import nz.ac.vuw.ecs.swen225.gp22.*;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.app.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.Assert.*;

import java.awt.Component;
import java.awt.event.KeyEvent;

class Fuzz{
    @Test
    public void test1(){
        int[] array = {0,1,2,3};
        
        
        
        Level level = Level.level1(null, null, array);
        
        List<String> possibleInput = List.of("up", "down", "left", "right");
        String randomInput = "";
        
        for(int x = 0; x < 4; x++){
            var randomNumber = (int)Math.random()*4;
            randomInput = possibleInput.get(randomNumber);
            
            switch(randomInput){
                case "up":
                    KeyEvent upkey = new KeyEvent(null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  0,'Z');
                    level.c().keyPressed(upkey);
                    level.c().keyReleased(upkey);
                    break;
                case "down":
                    KeyEvent downkey = new KeyEvent(null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  1,'Z');
                    level.c().keyPressed(downkey);
                    level.c().keyReleased(downkey);
                    break;
                case "left":
                    KeyEvent leftkey = new KeyEvent(null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  2,'Z');
                    level.c().keyPressed(leftkey);
                    level.c().keyReleased(leftkey);
                    break;
                case "right":
                    KeyEvent rightkey = new KeyEvent(null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  3,'Z');
                    level.c().keyPressed(rightkey);
                    level.c().keyReleased(rightkey);
                    break;
            }
        }
    }
    
    @Test
    public void test2(){
        
    }
}