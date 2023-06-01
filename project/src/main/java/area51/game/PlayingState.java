package area51.game;

import area51.arena.Arena;
import area51.arena.engine.*;
import area51.singleton.CycleSingleton;
import area51.singleton.GuiSingleton;
import area51.singleton.Key;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayingState implements GameState {
    private Game game;
    private Arena arena;
    private long latestCyclePlayed = 0;
    private ArrayList<Engine> engines = new ArrayList<>();
    private int nrAliensToKill;

    public PlayingState(Game game) {
        this.game = game;
        this.arena = game.getArena();
    }

    @Override
    public void run() {
        buildEngines();
        saveNrAliensToKill();
        GuiSingleton.call().addKeyBoardListener();

        while (true) {
            wait_for_new_cycle();
            if (GuiSingleton.call().getLatestCurrentKey() == Key.Q) {
                GuiSingleton.call().clearLatestCurrentKey();
                game.setState(new EndingState(game));
                break;
            }

            executeEngines();

            if(heroKilled()) {
                game.setKills(game.getKills() + nrAliensToKill - arena.nrAliensToKill());
                game.setState(new EndingState(game));
                break;
            }

            if(arena.aliensKilled()){
                game.setWave(game.getWave() + 1);
                game.setKills(game.getKills() + nrAliensToKill);
                game.setState(new StartingState(game));
                break;
            }
        }

        GuiSingleton.call().removeKeyBoardListener();
    }

    private void saveNrAliensToKill(){
        nrAliensToKill = arena.nrAliensToKill();
    }

    private void buildEngines(){
        engines.add(new MovementEngine(arena));
        engines.add(new AttackEngine(arena));
        engines.add(new DrawEngine(arena));
        engines.add(new LifeEngine(arena));
    }

    @SuppressWarnings("CatchAndPrintStackTrace")
    private void wait_for_new_cycle(){
        CycleSingleton cycle = CycleSingleton.call();
        while (cycle.getCycle() == latestCyclePlayed){
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        latestCyclePlayed = cycle.getCycle();
    }

    private void executeEngines(){
        for(Engine e: engines)
            e.execute();
    }

    private boolean heroKilled() {
        return arena.getHero() == null;
    }
}
