import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * La clase `meteor` representa un meteorito en el juego. Los meteoritos se mueven por la pantalla
 * y pueden dividirse en fragmentos más pequeños cuando son destruidos.
 */
public class meteor extends MovingObject {

    private Size size;  // Tamaño del meteorito (pequeño, mediano, grande)

    /**
     * Constructor de la clase `meteor`. Inicializa un meteorito con su posición, velocidad,
     * textura y tamaño específicos.
     *
     * @param position La posición inicial del meteorito en el juego.
     * @param velocity La velocidad inicial del meteorito.
     * @param maxVel La velocidad máxima que puede alcanzar el meteorito.
     * @param texture La imagen que representa al meteorito.
     * @param gameState El estado actual del juego.
     * @param size El tamaño del meteorito (grande, mediano, pequeño).
     */
    public meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, gameState gameState, Size size) {
        super(position, velocity, maxVel, texture, gameState);  // Llamada al constructor de MovingObject
        this.size = size;  // Asignar el tamaño del meteorito
        this.velocity = velocity.scale(maxVel);  // Escalar la velocidad al máximo permitido
    }

    /**
     * Actualiza la posición y el comportamiento del meteorito. Hace que el meteorito se mueva
     * y se recicle si sale de la pantalla. Además, incrementa su ángulo de rotación.
     */
    @Override
    public void update() {
        position = position.add(velocity);  // Actualizar la posición según la velocidad
        
        // Reciclar el meteorito si sale de los límites de la pantalla
        if(position.getX() > Constants.WIDTH)
            position.setX(-width);
        if(position.getY() > Constants.HEIGHT)
            position.setY(-height);
        
        if(position.getX() < -width)
            position.setX(Constants.WIDTH);
        if(position.getY() < -height)
            position.setY(Constants.HEIGHT);

        angle += Constants.DELTANGLE / 2;  // Aumentar el ángulo de rotación para el meteorito
    }

    /**
     * Destruye el meteorito, dividiéndolo en fragmentos más pequeños y agregando puntos al puntaje.
     */
    @Override
    public void Destroy() {
        gameState.divideMeteor(this);  // Dividir el meteorito en fragmentos más pequeños
        gameState.addScore(Constants.METEOR_SCORE, position);  // Añadir puntos por la destrucción del meteorito
        super.Destroy();  // Llamar al método Destroy de la clase padre (MovingObject)
    }

    /**
     * Dibuja el meteorito en la pantalla. Se utiliza una transformación afín para rotarlo
     * y colocar su textura en la posición correcta.
     *
     * @param g El objeto `Graphics` utilizado para dibujar en la pantalla.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;  // Convertir a Graphics2D para mayor control

        // Crear la transformación afín para mover y rotar el meteorito
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width / 2, height / 2);  // Rotar alrededor del centro del meteorito

        g2d.drawImage(Texture, at, null);  // Dibujar la imagen del meteorito con la transformación
    }

    /**
     * Obtiene el tamaño del meteorito.
     *
     * @return El tamaño del meteorito.
     */
    public Size getSize() {
        return size;
    }
}
