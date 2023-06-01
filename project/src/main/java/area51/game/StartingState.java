package area51.game;

import area51.arena.Arena;
import area51.arena.builder.ElementsBuilder;
import area51.arena.builder.ElementsDirector;
import area51.singleton.CycleSingleton;

public class StartingState implements GameState {
    private Game game;

    public StartingState(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        generateArena();
        buildWave();
        setEpoch();
        game.setState(new PlayingState(game));
    }

    private void generateArena(){
        Arena arena = new Arena();
        game.setArena(arena);
    }

    private void buildWave(){
        ElementsBuilder builder = new ElementsBuilder(game.getArena());
        ElementsDirector director = new ElementsDirector(game.getArena(), builder);
        director.generateWave(game.getWave());
    }

    private void setEpoch(){
        CycleSingleton.call().setEpoch(System.currentTimeMillis());
    }
}
