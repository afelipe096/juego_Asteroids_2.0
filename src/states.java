
import java.awt.Graphics;

public abstract class states {
	
	private static states currentState = null;
	
	public static states getCurrentState() {return currentState;}
	public static void changeState(states newState) {
		currentState = newState;
	}
	
	
	public abstract void update();
	public abstract void draw(Graphics g);
	
}