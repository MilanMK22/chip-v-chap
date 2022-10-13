package nz.ac.vuw.ecs.swen225.gp22.domain;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;


/**
 * @author Leo Gaynor: 300437633
 * <p>
 * The Phase record is used to Statically generate the required Maze and 
 * track what the program should do on a Win or Loss.
 * 
 * It also gives access to the Model.
 */
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

    public static Phase level2(Runnable win, Runnable loss){
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

    public static Phase levelSave(Runnable win, Runnable loss){
        try{
        Model model = new Model(new Maze(Persistency.readXML("levelPers"))){
           
            
            @Override
            public void win(){ win.run(); }
            @Override
            public void loss(){ loss.run(); }
        };
        model.chap().setInventory(Persistency.getSavedInventory());
        return new Phase(model);
    }
    catch(Exception e){
        throw new IllegalArgumentException("Unnable to Load saved Level from XML", e);
    }
        
    }
}
