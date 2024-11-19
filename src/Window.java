import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

// Clase principal que extiende JFrame e implementa Runnable para manejar la ejecución en hilo.
public class Window extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;	
    private Canvas canvas; // Área donde se dibuja el juego
    private Thread thread; // Hilo para ejecutar el juego
    private boolean running = false; // Estado del juego (si está corriendo o no)
    private BufferStrategy bs; // Estrategia de buffer para optimizar el renderizado
    private Graphics g; // Objeto Graphics para dibujar en el canvas
    private final int FPS = 60; // Frames por segundo
    private double TARGETTIME = 1000000000 / FPS; // Tiempo por cada frame (en nanosegundos)
    private double delta = 0; // Variable para controlar el tiempo entre frames
    private int AVERAGEFPS = FPS; // FPS promedio
    private gameState gameState; // Estado actual del juego
    private keyBoard keyBoard; // Para detectar entradas del teclado

    // Constructor de la ventana, donde se configura el JFrame y el Canvas.
    public Window() {
        setTitle("ASTEROIDS"); // Título de la ventana
        setSize(Constants.WIDTH, Constants.HEIGHT); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la ventana cuando se hace clic en "X"
        setResizable(false); // La ventana no es redimensionable
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Crear el Canvas y asignarle un KeyListener para detectar las teclas presionadas.
        canvas = new Canvas();
        keyBoard = new keyBoard();

        // Configuración del Canvas
        canvas.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setFocusable(true); // Permite que el Canvas reciba eventos de teclado

        add(canvas); // Agrega el canvas al JFrame
        canvas.addKeyListener(keyBoard); // Asocia el KeyListener al canvas
        setVisible(true); // Muestra la ventana
    }

    // Método main donde se inicia el juego.
    public static void main(String[] args) {
        new Window().start(); // Crea una nueva instancia de Window y empieza el juego
    }

    // Método para actualizar el estado del juego.
    private void update() {
        keyBoard.update(); // Actualiza el estado del teclado (presiones de teclas)
        gameState.update(); // Actualiza el estado del juego
    }

    // Método para dibujar todo en el Canvas.
    private void draw() {
        bs = canvas.getBufferStrategy();	
        if (bs == null) {
            canvas.createBufferStrategy(3); // Crea una estrategia de buffer si no existe
            return;
        }	
        g = bs.getDrawGraphics(); // Obtiene los gráficos para dibujar
        g.setColor(Color.BLACK); // Establece el color de fondo como negro
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT); // Dibuja un rectángulo que cubra todo el Canvas
        gameState.draw(g); // Dibuja el estado del juego (por ejemplo, asteroides)
        g.setColor(Color.WHITE); // Establece el color de los textos u objetos futuros

        g.dispose(); // Libera los recursos del gráfico
        bs.show(); // Muestra el contenido del buffer
    }

    // Inicializa los recursos y el estado del juego.
    private void init() {
        assets.init(); // Inicializa los recursos del juego
        gameState = new gameState(); // Crea un nuevo estado de juego
    }

    // Método que ejecuta el ciclo del juego.
    @Override
    public void run() {		
        long now = 0;
        long lastTime = System.nanoTime(); // Hora de inicio en nanosegundos
        int frames = 0; // Contador de frames
        long time = 0;

        init(); // Inicializa los recursos del juego

        // Ciclo principal del juego.
        while (running) {
            now = System.nanoTime(); // Obtiene el tiempo actual
            delta += (now - lastTime) / TARGETTIME; // Calcula el delta de tiempo
            time += (now - lastTime);
            lastTime = now; // Actualiza el último tiempo

            // Si el delta es mayor o igual a 1, actualiza y dibuja el juego.
            if (delta >= 1) {		
                update(); // Actualiza el juego
                draw(); // Dibuja el estado del juego
                delta--; // Resta el delta
                frames++; // Incrementa el contador de frames
            }

            // Cada segundo, se calcula el FPS promedio.
            if (time >= 1000000000) {
                AVERAGEFPS = frames;
                frames = 0;
                time = 0;
            }			
        }		
        stop(); // Detiene el juego cuando sale del ciclo
    }

    // Método para iniciar el juego en un nuevo hilo.
    private void start() {
        thread = new Thread(this); // Crea un nuevo hilo para ejecutar el juego
        thread.start(); // Inicia el hilo
        running = true; // Establece el estado del juego como "corriendo"
    }

    // Método para detener el juego.
    private void stop() {
        try {
            thread.join(); // Espera a que el hilo termine
            running = false; // Detiene el juego
        } catch (InterruptedException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }
}
