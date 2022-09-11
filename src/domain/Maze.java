package src.domain;

public class Maze {

    private static Tile[][] tiles;

    Maze(){
        // generate first maze
    }

    Maze(Tile[][] tiles){
        // generate maze from set of tiles (i.e load game or testing)
        this.tiles = tiles;
    }

    Tile getTile(int x, int y){
        return tiles[x][y];
    }
    Tile getTile(Point p){
        return tiles[p.getX()][p.getY()];
    }

    void setTile(int x, int y, Tile tile){
        this.tiles[x][y] = tile;
    }
    
}
