package nz.ac.vuw.ecs.swen225.gp22.domain;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;

public record Phase(Model model) {
    public static Phase level1(Runnable win, Runnable loss){
        try{
        Model model = new Model(new Maze(Persistency.readXML("level1"))){
            
            @Override
            public void win(){ win.run(); }
            @Override
            public void loss(){ loss.run(); }
        };
        return new Phase(model);
    }
    catch(Exception e){
        throw new IllegalArgumentException("Unnable to Load 1 Level from XML", e);
    }
        
    }

    static Phase level2(Runnable win, Runnable loss){
        try{
            Model model = new Model(new Maze(Persistency.readXML("level2"))){
                public void win(){ win.run(); }
                public void loss(){ loss.run(); }
            };
            return new Phase(model);
        }
        catch(Exception e){
            throw new IllegalArgumentException("Unnable to Load Level 2 from XML", e);
        }
    }
}
