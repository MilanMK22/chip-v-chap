package src.app;

enum Direction {
    NONE{

    },
    UP{

    },
    DOWN{

    },
    LEFT{

    },
    RIGHT{

    };

    Direction up(){
        return UP;
    }
    Direction down(){
        return DOWN;
    }
    Direction left(){
        return LEFT;
    }
    Direction right(){
        return RIGHT;
    }
    Direction unUp(){return this;}
    Direction unRight(){return this;}
    Direction unDown(){return this;}
    Direction unLeft(){return this;}


}
