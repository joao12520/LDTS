package area51.game;

import area51.singleton.GuiSingleton;
import area51.singleton.Key;

public class EndingState implements GameState {
    private Game game;

    public EndingState(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        showMessage();
        Key key = GuiSingleton.call().getYesNoInput();
        alterStateByKey(key);
    }

    private void showMessage() {
        GuiSingleton screen = GuiSingleton.call();
        screen.prepareGraphics();

        if(game.getWave() >= 5) {
            winnerMessage(screen);
        } else {
            loserMessage(screen);
        }

        screen.setBackground("#000");
        screen.drawString(7, 11, "DARE TO TRY AGAIN? - Y / N");
        screen.drawString(10, 16, "- WAVES SURVIVED: " + game.getWave());
        screen.drawString(10, 18, "- ALIENS KILLED: " + game.getKills());

        screen.sendGraphics();
    }

    private void loserMessage(GuiSingleton screen) {
        screen.setBackground("#ff0000");
        screen.drawString(10,3,"YOU CAN DO BETTER...");
    }

    private void winnerMessage(GuiSingleton screen) {
        screen.setBackground("#09b014");
        screen.drawString(10, 3, "BORN TO KILL, RAMBO!");
    }

    private void alterStateByKey(Key key){
        if (key == Key.Y)
            restartGame();
        else
            game.setState(new ClosingState());
    }

    private void restartGame(){
        game.setWave(1);
        game.setKills(0);
        game.setState(new StartingState(game));
    }
}

