package area51.game;

import area51.arena.Arena;

public class Game {
    private GameState state;
    private Arena arena;
    private int wave = 1;
    private int kills = 0;

    public Game() {
        this.state = new IntroducingState(this);
    }

    public void run(){
        boolean finished = false;
        while(!finished) {
            if(getState() instanceof ClosingState)
                finished = true;

            runState();
        }
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void runState() {
        state.run();
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}