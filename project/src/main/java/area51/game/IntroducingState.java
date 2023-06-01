package area51.game;

import area51.singleton.GuiSingleton;
import area51.singleton.Key;

public class IntroducingState implements GameState {
    private Game game;

    public IntroducingState(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        generateScreen();
        showMessage();
        Key key = GuiSingleton.call().getYesNoInput();
        alterStateByKey(key);
    }

    private void generateScreen(){
        GuiSingleton.call().open();
    }

    private void showMessage(){
        GuiSingleton screen = GuiSingleton.call();
        screen.prepareGraphics();

        // A Temporary Message
        screen.setBackground("#09b014");
        screen.drawString(10, 3, "AREA 51 - LDTS G0603");
        screen.setBackground("#000");
        screen.drawString(10, 8, "DARE TO TRY? - Y / N");

        screen.drawString(8, 13, "- ARROWS/WASD FOR MOVING");
        screen.drawString(9, 15, "- SPACE FOR ATTACKING");
        screen.drawString(8, 17, "- C FOR CHANGING WEAPON");
        screen.drawString(9, 19, "- Q/ESC FOR LOSERS...");

        screen.setBackground("#31c6d6");
        screen.drawString(11, 24, "Game developed by:");
        screen.drawString(5, 25, "Bruno C., Joao F. and Tiago L.");
        screen.drawString(19, 27, "V1");

        screen.sendGraphics();
    }

    private void alterStateByKey(Key key){
        if (key == Key.Y)
            game.setState(new StartingState(game));
        else
            game.setState(new ClosingState());
    }
}
