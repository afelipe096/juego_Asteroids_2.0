import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GameObjects {
    protected BufferedImage Texture;
    protected Vector2D position;

    public GameObjects(Vector2D position, BufferedImage texture) {
        this.position = position;
        this.Texture = texture; 
    }

    public abstract void update();

    public abstract void draw(Graphics g);

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}
