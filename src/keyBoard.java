import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyBoard implements KeyListener {

    private boolean[] key = new boolean[256]; // Array para almacenar el estado (presionado/no presionado) de cada tecla
    public static boolean UP, LEFT, RIGHT, SHOOT; // Variables públicas que indican si las teclas específicas están presionadas

    // Constructor que inicializa las teclas de dirección como no presionadas
    public keyBoard() {
        UP = false;
        LEFT = false;
        RIGHT = false;
        SHOOT = false; // Se añade SHOOT a false en el constructor
    }

    // Método que actualiza el estado de las teclas basándose en el array 'key'
    public void update() {
        // Actualiza las variables de las teclas según su estado en el array 'key'
        UP = key[KeyEvent.VK_UP]; // Tecla de arriba
        LEFT = key[KeyEvent.VK_LEFT]; // Tecla de izquierda
        RIGHT = key[KeyEvent.VK_RIGHT]; // Tecla de derecha
        SHOOT = key[KeyEvent.VK_SPACE]; // Barra espaciadora (para disparar)
    }

    // Método llamado cuando una tecla es presionada
    @Override
    public void keyPressed(KeyEvent e) {
        key[e.getKeyCode()] = true; // Marca la tecla como presionada en el array 'key'
    }

    // Método llamado cuando una tecla es liberada
    @Override
    public void keyReleased(KeyEvent e) {
        key[e.getKeyCode()] = false; // Marca la tecla como no presionada en el array 'key'
    }

    // Método que no se utiliza, pero se requiere por la interfaz KeyListener
    @Override
    public void keyTyped(KeyEvent e) {}
}
