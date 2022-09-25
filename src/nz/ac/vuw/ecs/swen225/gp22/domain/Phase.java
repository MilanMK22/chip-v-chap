package nz.ac.vuw.ecs.swen225.gp22.domain;


public record Phase(Model model) {
    static Phase level1(Runnable win, Runnable loss){

        Model model = new Model(new Maze()){

            private void win(){
                win.run();
            }
            private void loss(){
                loss.run();
            }
        };
        return new Phase(model);
    }

    static Phase level2(Runnable win, Runnable loss){

        Model model = new Model(new Maze()){
            private void win() {win.run();}
            private void loss() {loss.run();}
        };
        return new Phase(model);
    }


    
    
}
