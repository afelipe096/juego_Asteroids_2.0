import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends MovingObject {

    private Vector2D heading; // Dirección en la que el jugador está mirando
    private Vector2D acceleration; // Aceleración del jugador
    private boolean accelerating = false; // Si el jugador está acelerando
    private Chronometer fireRate; // Controla el tiempo entre disparos
    private boolean spawning, visible; // Control de aparición y visibilidad del jugador
    private Chronometer spawnTime, flickerTime; // Cronómetros para gestionar el tiempo de aparición y el parpadeo de visibilidad

    // Constructor del jugador
    public Player(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, gameState gameState) {
        super(position, velocity, maxVel, texture, gameState); // Llama al constructor de MovingObject
        heading = new Vector2D(0, 1); // Inicializa la dirección hacia abajo (en el eje Y positivo)
        acceleration = new Vector2D(); // Inicializa la aceleración a cero
        fireRate = new Chronometer(); // Inicializa el cronómetro de la tasa de fuego
        spawnTime = new Chronometer(); // Inicializa el cronómetro de tiempo de aparición
        flickerTime = new Chronometer(); // Inicializa el cronómetro de tiempo de parpadeo
    }

    @Override
    public void update() {

        // Si el tiempo de aparición no está corriendo, el jugador ya no está apareciendo
        if (!spawnTime.isRunning()) {
            spawning = false;
            visible = true; // Hace visible al jugador cuando termina de aparecer
        }

        // Si el jugador está en estado de aparición (spawning)
        if (spawning) {
            if (!flickerTime.isRunning()) {
                flickerTime.run(Constants.FLICKER_TIME); // Inicia el parpadeo
                visible = !visible; // Cambia la visibilidad del jugador (parpadea)
            }
        }

        // Disparo
        if (keyBoard.SHOOT && !fireRate.isRunning() && !spawning) {
            gameState.getMovingObjects().add(0, new Laser(
                    getCenter().add(heading.scale(width)), // El punto de disparo está en la parte frontal del jugador
                    heading,
                    Constants.LASER_VEL,
                    angle,
                    assets.redLaser,
                    gameState
            ));
            fireRate.run(Constants.FIRERATE); // Inicia el cronómetro para la tasa de fuego
        }

        // Control de rotación
        if (keyBoard.RIGHT)
            angle += Constants.DELTANGLE; // Gira hacia la derecha
        if (keyBoard.LEFT)
            angle -= Constants.DELTANGLE; // Gira hacia la izquierda

        // Aceleración
        if (keyBoard.UP) {
            acceleration = heading.scale(Constants.ACC); // La aceleración va en la dirección del "heading"
            accelerating = true;
        } else {
            if (velocity.getMagnitude() != 0)
                acceleration = (velocity.scale(-1).normalize()).scale(Constants.ACC / 2); // Desaceleración cuando no se acelera
            accelerating = false;
        }

        // Actualiza la velocidad y la limita a la velocidad máxima
        velocity = velocity.add(acceleration);
        velocity = velocity.limit(maxVel);

        // Actualiza la dirección del jugador (orientación)
        heading = heading.setDirection(angle - Math.PI / 2);
        position = position.add(velocity); // Actualiza la posición del jugador

        // Control de límites del mapa (pantalla)
        if (position.getX() > Constants.WIDTH)
            position.setX(0); // Se teletransporta al lado opuesto si sale de la pantalla por la derecha
        if (position.getY() > Constants.HEIGHT)
            position.setY(0); // Lo mismo pero por la parte inferior
        if (position.getX() < -width)
            position.setX(Constants.WIDTH); // Por la izquierda
        if (position.getY() < -height)
            position.setY(Constants.HEIGHT); // Por la parte superior

        // Actualiza los cronómetros
        fireRate.update();
        spawnTime.update();
        flickerTime.update();

        // Verifica colisiones (probablemente en la clase base)
        collidesWith();
    }

    @Override
    public void Destroy() {
        spawning = true; // Inicia el proceso de aparición
        spawnTime.run(Constants.SPAWNING_TIME); // Inicia el cronómetro de tiempo de aparición
        resetValues(); // Resetea los valores del jugador
        gameState.subtractLife(); // Resta una vida al jugador
    }

    // Resetea los valores del jugador (posiciones, velocidad)
    private void resetValues() {
        angle = 0;
        velocity = new Vector2D();
        position = gameState.PLAYER_START_POSITION; // Restablece la posición inicial del jugador
    }

    @Override
    public void draw(Graphics g) {

        if (!visible)
            return; // Si el jugador no es visible, no se dibuja

        Graphics2D g2d = (Graphics2D) g;

        // Crea una transformación para mover y rotar la imagen del jugador
        AffineTransform at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width / 2, height / 2); // Rota alrededor del centro del jugador

        // Dibuja la imagen del jugador (texture)
        g2d.drawImage(Texture, at, null);
    }

    public boolean isSpawning() {
        return spawning; // Devuelve si el jugador está en proceso de aparición
    }
}
