package nz.ac.vuw.ecs.swen225.gp22.domain;
import java.util.stream.*;


/**
 * @author Leo Gaynor: 300437633
 * The Maze Class represents the state of the maze internally.
 * 
 * It has methods for utility and modifying the maze, as well as standard getters and setters.
 */
public class Maze {
    int treasureCount;
    int pickupCount;
    int keyCount;
    int totalTreasure;
    int totalKeys;
    public int xlen;
    public int ylen;

    private Tile[][] tiles;

    /**
     * Create a maze object from a {@code Tile[][]} object.
     * 
     * @param tiles The tiles that make up the maze.
     */
    public Maze(Tile[][] tiles){
        // generate maze from set of tiles (i.e load game or testing)
        this.tiles = tiles;
        this.pickupCount = getNumPickups();
        this.treasureCount = getNumTreasure();
        this.keyCount = getNumKey();
        this.totalKeys = keyCount +0;
        this.totalTreasure = treasureCount +0;
        this.ylen = tiles.length;
        this.xlen = tiles[0].length;
    }
    

    /**
     * Method for Ilya
     * @return Tile[][] of all tiles.
     */
    public Tile[][] getTiles() { return tiles; }


    /**
     * Get chap from the maze.
     * 
     * @return This Maze's {@code Chap} object.
     * 
     * @see {@link Chap}
     */
    public Chap getChap(){
        Tile temp = this.stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isChap()).findFirst().get();
        return (Chap)temp.getEntity();
    }

    //Pickup Methods

    /**
     * Get Tiles with pickups on them.
     * @return Tiles that have pickups on them.
     */
    public Tile[] getPickups(){
        return stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isPickup()).toArray(Tile[]::new);
    }
    /**
     * Get Tiles with Treasures on them.
     * @return Tiles that have Treasures on them.
     */
    public Tile[] getTreasure(){
        return stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isTreasure()).toArray(Tile[]::new);
    }
    /**
     * Get Tiles with Keys on them.
     * @return Tiles that have Keys on them.
     */
    public Tile[] getKeys(){
        return stream().filter(t->t.hasEntity()).filter(t->t.getEntity().isPickup() && !t.getEntity().isTreasure()).toArray(Tile[]::new);
    }

    /**
     * Get number of tiles with pickups on them.
     * @return number of tiles that have pickups on them.
     */
    public int getNumPickups(){
        return getPickups().length;
    }
    /**
     * Get number of tiles with Treasure on them.
     * @return number of tiles that have Treasure on them.
     */
    public int getNumTreasure(){
        return getTreasure().length;
    }
    /**
     * Get number of tiles with Keys on them.
     * @return number of tiles that have Keys on them.
     */
    public int getNumKey(){
        return getKeys().length;
    }


    //Utility Methods
    /**
     * Get xlen.
     * @return This maze's {@code xlen} field.
     */
    public int getXLen(){ return this.xlen; }

    /**
     * Get ylen.
     * @return This maze's {@code ylen} field.
     */
    public int getYLen(){ return this.ylen; }

    /**
     * Get the total number of keys present at the start of this maze.
     * @return The total number of keys.
     */
    public int totalKeys(){ return totalKeys; }
    /**
     * Get the total number of treasure present at the start of this maze.
     * @return The total number of treasure.
     */
    public int totalTreasure(){ return totalTreasure; }
    
    /**
     * Get the specific tile at a Point p in the maze.
     * @param p {@code Point}
     * @return The specific Tile at Point p. 
     */
    public Tile getTile(Point p){ return getTile(p.getX(), p.getY()); }
    /**
     * Get the specific tile at point (x, y) in the maze. If the x or y is outside of the maze, return a Wall Tile.
     * @param x int of the x position.
     * @param y int of the y position.
     * @return The specific tile at point (x, y)
     */
    public Tile getTile(int x, int y){ 
        if(bound(x, y)){
            return tiles[y][x];
        }
        else return Tile.wallTile(new Point(x, y));
        // throw new Error("Chap cannot move off of the map"); 
    }
    /**
     * Check that a point (x, y) is within the maze. 
     * @param x int of the x position.
     * @param y int of the y position.
     * @return the boolean evaluation of this expression.
     */
    public boolean bound(int x, int y){ return x >= 0 && x < getXLen() && y >= 0 && y < getYLen(); }

    /**
     * Set the tile at point (x, y) to {@code tile}
     * @param x int of the x position.
     * @param y int of the y position.
     * @param tile the Tile to set point (x, y) to.
     */
    public void setTile(int x, int y, Tile tile){ this.tiles[x][y] = tile; }

    /**
     * Set the Tile at Point p to {@code tile}
     * @param p the Point of the tile to set.
     * @param tile the Tile to set Point p to.
     */
    public void setTile(Point p, Tile tile){ this.tiles[p.getX()][p.getY()] = tile; }

    /**
     * Stream all Tiles in the Maze.
     * @return A {@code Stream<Tile>} of all tiles in the maze.
     */
    public Stream<Tile> stream(){
        return IntStream.range(0, tiles.length)
        .mapToObj(r -> IntStream.range(0, tiles[r].length)
        .mapToObj(c -> tiles[r][c]))
        .flatMap(x->x); 
    }
}
