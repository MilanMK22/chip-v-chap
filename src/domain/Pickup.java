package src.domain;
import java.util.Optional;

enum KEYCOLOR {
    RED,
    GREEN,
    BLUE,
}

public class Pickup {
    boolean isKey;
    boolean isTreasure;
    KEYCOLOR color; 
    
    Pickup(){

        isKey =false;
        isTreasure = true;
    }


    Pickup(KEYCOLOR color){
        this.color = color;
        isKey = true;
        isTreasure = false;
    }

    public boolean isKey(){
        return this.isKey;
    }

    public boolean isTreasure(){
        return this.isTreasure;
    }

    public Optional<KEYCOLOR> keyColor(){
        return Optional.of(color);
    }
}

