import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Laser extends MovingObject {

    /**
     * Constructor de la clase Laser.
     * 
     * @param position  La posición inicial del láser.
     * @param velocity  La velocidad del láser.
     * @param maxVel   La velocidad máxima del láser.
     * @param angle    El ángulo de dirección del láser.
     * @param texture  La textura que se usará para representar al láser.
     * @param gameState El estado actual del juego.
     */
    public Laser(Vector2D position, Vector2D velocity, double maxVel, double angle, BufferedImage texture, gameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.angle = angle;
        this.velocity = velocity.scale(maxVel); // Escala la velocidad por el valor máximo para controlar la velocidad del láser
    }

    /**
     * Actualiza la posición del láser y verifica si ha colisionado con algún objeto.
     * También verifica si el láser sale de los límites de la pantalla y lo destruye si es necesario.
     */
    @Override
    public void update() {
        position = position.add(velocity); // Actualiza la posición del láser
        // Si el láser sale de los límites de la pantalla, se destruye
        if(position.getX() < 0 || position.getX() > Constants.WIDTH ||
           position.getY() < 0 || position.getY() > Constants.HEIGHT){
            Destroy();
        }
        collidesWith(); // Verifica si el láser colisiona con otros objetos
    }

    /**
     * Dibuja el láser en la pantalla, aplicando rotación y posición.
     * 
     * @param g El objeto Graphics utilizado para dibujar.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        // Establece la transformación para la posición y rotación del láser
        at = AffineTransform.getTranslateInstance(position.getX() - width/2, position.getY());
        at.rotate(angle, width/2, 0); // Rota el láser según el ángulo
        // Dibuja la imagen del láser en la posición y ángulo especificados
        g2d.drawImage(Texture, at, null);
    }

    /**
     * Obtiene el centro del láser para la detección de colisiones.
     * 
     * @return Un objeto Vector2D que representa el centro del láser.
     */
    @Override
    public Vector2D getCenter(){
        // Retorna el centro del láser, útil para la detección de colisiones
        return new Vector2D(position.getX() + width/2, position.getY() + width/2);
    }
}
