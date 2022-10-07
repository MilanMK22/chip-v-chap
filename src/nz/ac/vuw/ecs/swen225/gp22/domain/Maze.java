package nz.ac.vuw.ecs.swen225.gp22.domain;
import java.util.stream.*;

public class Maze {
    int treasureCount;
    int pickupCount;
    int keyCount;
    int totalTreasure;
    int totalKeys;
    public int xlen;
    public int ylen;

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
    



    public Chap getChap(){
        Tile temp = this.stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isChap()).findFirst().get();
        return (Chap)temp.getEntity();
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

    public int getXLen(){ return this.xlen; }
    public int getYLen(){ return this.ylen; }

    public int totalKeys(){ return totalKeys; }
    public int totalTreasure(){ return totalTreasure; }

    public Tile getTile(Point p){ return getTile(p.getX(), p.getY()); }
    public Tile getTile(int x, int y){ 
        if(bound(x, y)){
            return tiles[y][x];
        }
        else return Tile.wallTile(new Point(x, y));
        // throw new Error("Chap cannot move off of the map"); 
    }

    public boolean bound(Point p){ return bound(p.getX(), p.getY()); }
    public boolean bound(int x, int y){ return x >= 0 && x <= xlen && y >= 0 && y <= ylen; }

    public void setTile(int x, int y, Tile tile){ this.tiles[x][y] = tile; }
    public void setTile(Point loc, Tile tile){ this.tiles[loc.getX()][loc.getY()] = tile; }

    public Stream<Tile> stream(){
        return IntStream.range(0, tiles.length)
        .mapToObj(r -> IntStream.range(0, tiles[r].length)
        .mapToObj(c -> tiles[r][c]))
        .flatMap(x->x); 
    }
    
}
