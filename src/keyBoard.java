import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyBoard implements KeyListener {

    private boolean[] key = new boolean[256];

    public static boolean UP, LEFT, RIGHT, SHOOT;

    public keyBoard() {
        UP = false;
        LEFT = false;
        RIGHT = false;
    }

    public void update() {
        UP = key[KeyEvent.VK_UP];
        LEFT = key[KeyEvent.VK_LEFT];
        RIGHT = key[KeyEvent.VK_RIGHT];
        SHOOT = key[KeyEvent.VK_SPACE];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        key[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        key[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
