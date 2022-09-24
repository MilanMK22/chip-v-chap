package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.concurrent.ExecutionException;

import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;

public record Phase(Model model) {
    static Phase level1(Runnable win, Runnable loss){
        try{
        Model model = new Model(new Maze(Persistency.readXML("1"))){

            private void win(){
                win.run();
            }
            private void loss(){
                loss.run();
            }
        };
        return new Phase(model);
    }
    catch(Exception e){
        throw new IllegalArgumentException("Unnable to Load 1 Level from XML", e);
    }
        
    }

    static Phase level2(Runnable win, Runnable loss){
        try{
            Model model = new Model(new Maze(Persistency.readXML("1"))){
    
                private void win(){
                    win.run();
                }
                private void loss(){
                    loss.run();
                }
            };
            return new Phase(model);
        }
        catch(Exception e){
            throw new IllegalArgumentException("Unnable to Load Level 2 from XML", e);
        }
    }

    
    
}
