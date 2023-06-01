package area51.singleton;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class GuiSingleton implements KeyBoardListener {
    private TerminalScreen screen;
    private TextGraphics textGraphics;
    private final int width = 40;
    private final int height = 30;
    private Key latestCurrentKey = null;
    private final KeyBoardObserver keyBoardObserver = new KeyBoardObserver();

    public static GuiSingleton guiSingleton;

    // Test Only
    public static void injectSingleton(GuiSingleton singleton) {
        guiSingleton = singleton;
    }

    public static GuiSingleton call() {
        if (guiSingleton == null)
            guiSingleton = new GuiSingleton();
        return guiSingleton;
    }

    @SuppressWarnings("CatchAndPrintStackTrace")
    public void open() {
        if (this.screen != null)
            return;

        try {
            DefaultTerminalFactory factory = setFontAndTerminalFactory();
            Terminal terminal = factory.createTerminal();

            this.screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
            addCloseScreenListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CatchAndPrintStackTrace")
    public void close() {
        try {
            screen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCloseScreenListener(){
        ((AWTTerminalFrame) screen.getTerminal()).addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                ((AWTTerminalFrame) screen.getTerminal()).dispose();
                System.exit(0);
            }
        });
    }

    public void addKeyBoardListener() {
        keyBoardObserver.setListener(this);
        ((AWTTerminalFrame) screen.getTerminal()).getComponent(0).addKeyListener(keyBoardObserver);
    }

    @SuppressWarnings("CatchAndPrintStackTrace")
    public void removeKeyBoardListener() {
        ((AWTTerminalFrame) screen.getTerminal()).getComponent(0).removeKeyListener(keyBoardObserver);
        try {
            // clear input buffer
            while(screen.pollInput() != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CatchAndPrintStackTrace")
    private DefaultTerminalFactory setFontAndTerminalFactory() {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        try {
            URL resource = getClass().getClassLoader().getResource("square.ttf");
            File fontFile = new File(resource.toURI());
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            Font loadedFont = font.deriveFont(Font.PLAIN, 25);
            AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);
            factory.setTerminalEmulatorFontConfiguration(fontConfig);
            factory.setForceAWTOverSwing(true);
            factory.setInitialTerminalSize(new TerminalSize(width, height));
        } catch (IOException | FontFormatException | URISyntaxException e) {
            e.printStackTrace();
        }

        return factory;
    }

    // ============
    // GRAPHICS METHODS
    // ============

    public void prepareGraphics() {
        screen.clear();
        this.textGraphics = screen.newTextGraphics();
        this.textGraphics.enableModifiers(SGR.BOLD);
    }

    public void setBackground(String colorString) {
        textGraphics.setBackgroundColor(TextColor.Factory.fromString(colorString));
    }

    public void setForeground(String colorString) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString(colorString));
    }

    public void drawString(int x, int y, String aString) {
        textGraphics.putString(x, y, aString);
    }

    public void drawChar(int x, int y, String backgroundColor, String foregroundColor, Character character) {
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        drawString(x, y, character.toString());
    }

    @SuppressWarnings("CatchAndPrintStackTrace")
    public void sendGraphics() {
        try {
            this.screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ============
    // KEYBOARD METHODS
    // ============
    public Key getYesNoInput() {
        ArrayList<Key> validKeys = new ArrayList<>();
        validKeys.add(Key.Q);
        validKeys.add(Key.Y);
        validKeys.add(Key.N);

        return getValidKey(validKeys);
    }

    @SuppressWarnings("CatchAndPrintStackTrace")
    public Key getValidKey(ArrayList<Key> acceptedKeys) {
        Key validKey = null;

        try {
            while (validKey == null) {
                KeyStroke keyStroke = screen.readInput();
                Key key = getKeyFromKeyStroke(keyStroke);

                for (Key k : acceptedKeys) {
                    if (k.equals(key)) {
                        validKey = key;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return validKey;
    }

    @Override
    public void storeCurrentKey(int keyCode) {
        Key validKey = getKeyFromKeyEvent(keyCode);
        if(validKey != null)
            latestCurrentKey = validKey;
    }

    public Key getLatestCurrentKey(){
        return latestCurrentKey;
    }

    public void clearLatestCurrentKey() { latestCurrentKey = null; }

    private Key getKeyFromKeyEvent(int keyCode){
        Key key = null;
        switch (keyCode){
            case KeyEvent.VK_Q, KeyEvent.VK_ESCAPE -> key = Key.Q;
            case KeyEvent.VK_Y -> key = Key.Y;
            case KeyEvent.VK_N -> key = Key.N;
            case KeyEvent.VK_UP, KeyEvent.VK_W -> key = Key.UP;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> key = Key.RIGHT;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> key = Key.DOWN;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> key = Key.LEFT;
            case KeyEvent.VK_SPACE -> key = Key.SPACE;
            case KeyEvent.VK_C -> key = Key.C;
        }
        return key;
    }

    private Key getKeyFromKeyStroke(KeyStroke key) {
        // assume closing of window as 'q' (quit)
        if (key.getKeyType() == KeyType.EOF)
            return Key.Q;

        // assume escape as same as 'q' (quit)
        if (key.getKeyType() == KeyType.Escape)
            return Key.Q;

        if (key.getKeyType() == KeyType.Character) {
            Key aKey = null;
            switch (key.getCharacter()) {
                case 'q' -> aKey = Key.Q;
                case 'y' -> aKey = Key.Y;
                case 'n' -> aKey = Key.N;
                case 'w' -> aKey = Key.UP;
                case 's' -> aKey = Key.DOWN;
                case 'd' -> aKey = Key.RIGHT;
                case 'a' -> aKey = Key.LEFT;
                case ' ' -> aKey = Key.SPACE;
            }
            return aKey;
        }

        if (key.getKeyType() == KeyType.ArrowUp)
            return Key.UP;

        if (key.getKeyType() == KeyType.ArrowRight)
            return Key.RIGHT;

        if (key.getKeyType() == KeyType.ArrowDown)
            return Key.DOWN;

        if (key.getKeyType() == KeyType.ArrowLeft)
            return Key.LEFT;

        return null;
    }
}
