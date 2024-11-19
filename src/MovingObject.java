import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class MovingObject extends GameObjects {

    protected Vector2D velocity; // La velocidad del objeto en el espacio
    protected AffineTransform at; // Transformación afín para el objeto (para rotación y posicionamiento)
    protected double angle; // Ángulo de rotación del objeto
    protected double maxVel; // Velocidad máxima del objeto
    protected int width; // Ancho del objeto (de la imagen)
    protected int height; // Alto del objeto (de la imagen)
    protected gameState gameState; // El estado del juego
    protected boolean Dead; // Indica si el objeto está muerto

    // Constructor de la clase
    public MovingObject(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, gameState gameState) {
        super(position, texture); // Llamada al constructor de la clase base 'GameObjects'
        this.velocity = velocity;
        this.maxVel = maxVel;
        this.gameState = gameState;
        width = texture.getWidth(); // Establece el ancho del objeto con el de la textura
        height = texture.getHeight(); // Establece la altura del objeto con la de la textura
        angle = 0; // Inicializa el ángulo de rotación a 0
        Dead = false; // Inicializa el estado de muerte del objeto en 'false'
    }

    // Método para verificar si el objeto colisiona con otros objetos en movimiento
    protected void collidesWith() {
        ArrayList<MovingObject> movingObjects = gameState.getMovingObjects(); // Obtiene la lista de objetos en movimiento
        for (int i = 0; i < movingObjects.size(); i++) {
            MovingObject m = movingObjects.get(i); // Itera sobre los objetos en movimiento
            if (m.equals(this)) // Si el objeto es el mismo que el actual, omítelo
                continue;
            // Calcula la distancia entre los centros de los dos objetos
            double distance = m.getCenter().subtract(getCenter()).getMagnitude();

            // Si la distancia entre los objetos es menor que la suma de sus radios, hay colisión
            if (distance < m.width / 2 + width / 2 && movingObjects.contains(this)) {
                objectCollision(m, this); // Llama a la función de manejo de colisiones
            }
        }
    }

    // Método para manejar las colisiones entre dos objetos
    private void objectCollision(MovingObject a, MovingObject b) {
        // Si alguno de los objetos es un 'Player' en proceso de aparición, no se hace nada
        if (a instanceof Player && ((Player) a).isSpawning()) {
            return;
        }
        if (b instanceof Player && ((Player) b).isSpawning()) {
            return;
        }
        // Si no son meteoros, destruye ambos objetos al colisionar
        if (!(a instanceof meteor && b instanceof meteor)) {
            a.Destroy();
            b.Destroy();
        }
    }

    // Método para destruir el objeto (poner su estado de muerte a 'true')
    protected void Destroy() {
        Dead = true; // Marca el objeto como muerto
    }

    // Método para obtener el centro del objeto
    protected Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + height / 2); // Calcula el centro basado en la posición y el tamaño
    }

    // Método para verificar si el objeto está muerto
    public boolean isDead() {
        return Dead; // Devuelve el estado de muerte del objeto
    }
}
