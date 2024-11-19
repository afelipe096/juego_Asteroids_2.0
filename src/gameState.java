import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class gameState {
    
    // Posición inicial del jugador en el centro de la pantalla
    public static final Vector2D PLAYER_START_POSITION = new Vector2D(Constants.WIDTH/2 - assets.player.getWidth()/2,
            Constants.HEIGHT/2 - assets.player.getHeight()/2);
    
    private Player player;  // Instancia del jugador
    private ArrayList<MovingObject> movingObjects = new ArrayList<>();  // Lista de objetos en movimiento (jugador, meteoritos, etc.)
    private ArrayList<Message> messages = new ArrayList<Message>();  // Lista de mensajes a mostrar en pantalla
    private int score = 0;  // Puntaje del jugador
    private int lives = 3;  // Vidas del jugador
    private int meteor;  // Número de meteoritos actuales
    private int waves = 0;  // Número de oleadas
    private Chronometer gameOverTimer;  // Cronómetro para el "Game Over"
    private boolean gameOver;  // Estado de "Game Over"

    /**
     * Constructor que inicializa el estado del juego.
     */
    public gameState() {
        player = new Player(PLAYER_START_POSITION, new Vector2D(),
                Constants.PLAYER_MAX_VEL, assets.player, this);  // Inicializa el jugador
        gameOverTimer = new Chronometer();  // Inicializa el cronómetro de "Game Over"
        gameOver = false;  // Establece el estado de "Game Over" como falso
        movingObjects.add(player);  // Añade al jugador a la lista de objetos en movimiento
        meteor = 1;  // Establece el número inicial de meteoritos
        startWave();  // Inicia la primera oleada de meteoritos
    }

    /**
     * Añade puntos al puntaje del jugador y muestra un mensaje con el valor sumado.
     * 
     * @param value   Valor de los puntos sumados.
     * @param position Posición en la pantalla para mostrar el mensaje.
     */
    public void addScore(int value, Vector2D position) {
        score += value;
        messages.add(new Message(position, true, "+" + value + " score", Color.WHITE, false, assets.fontMed));
    }

    /**
     * Divide un meteorito en varios meteoritos más pequeños y los agrega a la lista de objetos en movimiento.
     * 
     * @param meteor Meteorito que se va a dividir.
     */
    public void divideMeteor(meteor meteor) {
        Size size = meteor.getSize();
        BufferedImage[] textures = size.textures;
        Size newSize = null;
        switch (size) {
            case BIG:
                newSize = size.MED;
                break;
            case MED: 
                newSize = size.SMALL;
                break;
            case SMALL: 
                newSize = size.TINY;
                break;
            default:
                return;
        }

        // Crear nuevos meteoritos más pequeños y agregarlos a la lista
        for (int i = 0; i < size.quantity; i++) {
            movingObjects.add(new meteor(
                meteor.getPosition(),
                new Vector2D(0 , 1).setDirection(Math.random()*Math.PI*2),
                Constants.METEOR_VEL*Math.random() + 1,
                textures[(int)(Math.random()*textures.length)], this, newSize
            ));
        }
    }

    /**
     * Inicia una nueva oleada de meteoritos.
     */
    private void startWave() {
        waves++;  // Incrementa el contador de oleadas
        messages.add(new Message(new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2), false,
                "OLEADA " + waves, Color.WHITE, true, assets.fontBig));  // Muestra un mensaje de la oleada actual
    
        double x, y;
    
        // Genera meteoritos en posiciones aleatorias
        for (int i = 0; i < meteor; i++) {
            x = i % 2 == 0 ? Math.random() * Constants.WIDTH : 0;
            y = i % 2 == 0 ? 0 : Math.random() * Constants.HEIGHT;
    
            BufferedImage texture = assets.bigs[(int) (Math.random() * assets.bigs.length)];
    
            movingObjects.add(new meteor(
                    new Vector2D(x, y),
                    new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
                    Constants.METEOR_VEL * Math.random() + 1,
                    texture,
                    this,
                    Size.BIG
            ));
        }
        meteor++;  // Aumenta el número de meteoritos para la siguiente oleada
    }

    /**
     * Actualiza el estado del juego, moviendo objetos y verificando si hay meteoritos restantes.
     * Si no hay meteoritos, comienza una nueva oleada.
     */
    public void update() {
        if (gameOver) {
            gameOverTimer.update();  // Actualiza el cronómetro de "Game Over"
            System.out.println("Game Over está activo. Cronómetro corriendo...");
    
            if (gameOverTimer.isFinished()) {
                System.out.println("Cronómetro terminado. Reiniciando el juego...");
                resetGame();  // Reinicia el juego si el cronómetro terminó
            }
    
            return;
        }
    
        // Actualización normal del juego
        for (int i = 0; i < movingObjects.size(); i++) {
            MovingObject mo = movingObjects.get(i);
            mo.update();  // Actualiza cada objeto en movimiento
            if (mo.isDead()) {
                movingObjects.remove(i);  // Elimina objetos muertos de la lista
                i--;
            }
        }
    
        // Verifica si quedan meteoritos en pantalla
        for (int i = 0; i < movingObjects.size(); i++) {
            if (movingObjects.get(i) instanceof meteor)
                return;  // Si quedan meteoritos, no inicia la siguiente oleada
        }
    
        startWave();  // Si no quedan meteoritos, comienza una nueva oleada
    }

    /**
     * Dibuja el estado del juego en la pantalla, incluyendo objetos en movimiento, puntaje y vidas.
     * 
     * @param g Objeto Graphics para dibujar en la pantalla.
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    
        // Configurar el renderizado para suavizado de imágenes
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    
        // Dibuja los mensajes en pantalla
        for (int i = 0; i < messages.size(); i++) {
            messages.get(i).draw(g2d);
        }
    
        // Dibuja los objetos en movimiento (jugador, meteoritos, etc.)
        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).draw(g);
        }
    
        // Dibuja el puntaje y las vidas
        drawScore(g);
        drawLives(g);
    }

    /**
     * Dibuja el puntaje en la pantalla.
     * 
     * @param g Objeto Graphics para dibujar en la pantalla.
     */
    private void drawScore(Graphics g) {
        Vector2D pos = new Vector2D(850, 25);
        String scoreToString = Integer.toString(score);
        for (int i = 0; i < scoreToString.length(); i++) {
            g.drawImage(assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))],
                    (int) pos.getX(), (int) pos.getY(), null);
            pos.setX(pos.getX() + 20);  // Ajusta la posición para cada dígito
        }
    }

    /**
     * Dibuja las vidas del jugador en la pantalla.
     * 
     * @param g Objeto Graphics para dibujar en la pantalla.
     */
    private void drawLives(Graphics g) {
        if (lives < 1) return;  // Si no quedan vidas, no dibuja nada
        Vector2D livePosition = new Vector2D(25, 25);
        g.drawImage(assets.life, (int) livePosition.getX(), (int) livePosition.getY(), null);  // Dibuja la imagen de la vida
        g.drawImage(assets.numbers[10], (int) livePosition.getX() + 40, (int) livePosition.getY() + 5, null);
        
        String livesToString = Integer.toString(lives);
        Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());
        for (int i = 0; i < livesToString.length(); i++) {
            int number = Integer.parseInt(livesToString.substring(i, i + 1));
            if (number <= 0) break;
            g.drawImage(assets.numbers[number],
                    (int) pos.getX() + 60, (int) pos.getY() + 5, null);
            pos.setX(pos.getX() + 20);  // Ajusta la posición para cada dígito
        }
    }

    // Métodos de acceso para los objetos en movimiento, el jugador, y los mensajes
    
    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    public void setMovingObjects(ArrayList<MovingObject> movingObjects) {
        this.movingObjects = movingObjects;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Resta una vida al jugador y verifica si el juego terminó.
     * 
     * @return true si el jugador sigue con vida, false si el juego terminó.
     */
    public boolean subtractLife() {
        lives--;
        if (lives <= 0) {
            gameOver();  // Si no quedan vidas, termina el juego
            return false;
        }
        return true;
    }

    /**
     * Finaliza el juego y muestra el mensaje de "Game Over".
     */
    public void gameOver() {
        Message gameOverMsg = new Message(
                new Vector2D(Constants.WIDTH / 2 - 100, Constants.HEIGHT / 2), // Centrado
                true,
                "GAME OVER",
                Color.WHITE,
                true,
                assets.fontBig);
    
        this.messages.add(gameOverMsg);
        gameOverTimer.run(5000);  // Configura el cronómetro para 5 segundos
        gameOver = true;  // Marca el juego como terminado
    }

    /**
     * Reinicia el juego, restaurando puntaje, vidas, oleadas y meteoritos.
     */
    private void resetGame() {
        // Restablece puntaje, vidas, oleadas y meteoritos
        score = 0;
        lives = 3;
        waves = 0;
        meteor = 1;    
        movingObjects.clear();  // Limpiar objetos en movimiento
        messages.clear();  // Limpiar mensajes
        player = new Player(PLAYER_START_POSITION, new Vector2D(),
                Constants.PLAYER_MAX_VEL, assets.player, this);  // Crear nuevo jugador
        movingObjects.add(player);  // Añadir jugador a la lista
        gameOverTimer.stop();  // Detener cronómetro
        gameOver = false;  // Restablecer estado de "Game Over"
        startWave();  // Iniciar la primera oleada
    }
}
