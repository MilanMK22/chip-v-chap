package test.nz.ac.vuw.ecs.swen225.gp22.fuzz;

import nz.ac.vuw.ecs.swen225.gp22.*;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.Assert.*;


class Fuzz{
    @Test
    public void test1(){
        List<String> possibleInput = List.of("up", "down", "left", "right");
        String randomInput = "";

        for(int x = 0; x < 4; x++){
            var randomNumber = (int)Math.random()*4;
            randomInput = possibleInput.get(randomNumber);
        }
        switch(randomInput){
            case "up":
                Chap.up();
                break;
            case "down":
                Chap.down();
                break;
            case "left":
                Chap.left();
                break;
            case "right":
                Chap.right();
                break;
        }
    }

    @Test
    public void test2(){

    }
}