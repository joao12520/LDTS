package area51.singleton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBoardObserver extends KeyAdapter {
    private KeyBoardListener listener;

    public KeyBoardObserver() {}

    @Override
    public void keyPressed(KeyEvent e) {
        listener.storeCurrentKey(e.getKeyCode());
    }

    public void setListener(KeyBoardListener listener) {
        this.listener = listener;
    }
}
