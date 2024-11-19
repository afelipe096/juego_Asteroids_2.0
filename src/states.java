import java.awt.Graphics;

public abstract class states {
	
    // Variable estática que almacena el estado actual del juego.
	private static states currentState = null;

    // Método estático que devuelve el estado actual del juego.
	public static states getCurrentState() {
        return currentState;
    }

    // Método estático que cambia el estado actual del juego al nuevo estado proporcionado.
	public static void changeState(states newState) {
        currentState = newState;
    }

    // Método abstracto para actualizar el estado del juego (debe ser implementado en las subclases).
	public abstract void update();

    // Método abstracto para dibujar el estado del juego en la pantalla (debe ser implementado en las subclases).
	public abstract void draw(Graphics g);
	
}
