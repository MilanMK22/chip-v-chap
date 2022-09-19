package nz.ac.vuw.ecs.swen225.gp22.domain;
import java.util.stream.*;

public class Maze {
    int treasureCount;
    int pickupCount;
    int keyCount;
    int totalTreasure;
    int totalKeys;
    int xlen;
    int ylen;

    private Tile[][] tiles;

    public Maze(){
        // generate first maze
    }

    public Maze(Tile[][] tiles){
        // generate maze from set of tiles (i.e load game or testing)
        this.tiles = tiles;
        this.pickupCount = getNumPickups();
        this.treasureCount = getNumTreasure();
        this.keyCount = getNumKey();
        this.totalKeys = keyCount +0;
        this.totalTreasure = treasureCount +0;
        this.xlen = tiles.length;
        this.ylen = tiles[0].length;
    }
    



    public Point findChapLocation(){
        Tile temp = this.stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isChap()).findFirst().get();
        return temp.getLocation();
    }

    //Pickup Methods
    public Tile[] getPickups(){
        return stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isPickup()).toArray(Tile[]::new);
    }
    public Tile[] getTreasure(){
        return stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isTreasure()).toArray(Tile[]::new);
    }
    public Tile[] getKeys(){
        return stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isPickup() && !t.getEntity().isTreasure()).toArray(Tile[]::new);
    }

    public int getNumPickups(){
        return (int)stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isPickup()).count();
    }
    public int getNumTreasure(){
        return (int)stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isTreasure()).count();
    }
    public int getNumKey(){
        return (int)stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isPickup() && !t.getEntity().isTreasure()).count();
    }


    //Utility Methods

    public int totalKeys(){ return totalKeys; }
    public int totalTreasure(){ return totalTreasure; }

    public Tile getTile(int x, int y){ return tiles[x][y]; }
    public Tile getTile(Point p){ return tiles[p.getX()][p.getY()]; }

    public void setTile(int x, int y, Tile tile){ this.tiles[x][y] = tile; }
    public void setTile(Point loc, Tile tile){ this.tiles[loc.getX()][loc.getY()] = tile; }

    public Stream<Tile> stream(){
        return IntStream.range(0, tiles.length)
        .mapToObj(r -> IntStream.range(0, tiles[r].length)
        .mapToObj(c -> tiles[r][c]))
        .flatMap(x->x); 
    }
    
}
