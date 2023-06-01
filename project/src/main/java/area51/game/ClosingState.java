package area51.game;

import area51.singleton.GuiSingleton;

public class ClosingState implements GameState {
    @Override
    public void run() {
        GuiSingleton.call().close();
    }
}
