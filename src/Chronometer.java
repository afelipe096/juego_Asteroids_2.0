public class Chronometer {
    private long delta, lastTime; // 'delta' lleva el registro del tiempo transcurrido, 'lastTime' es el último tiempo registrado
    private long time; // Tiempo objetivo para el cronómetro
    private boolean running; // Indica si el cronómetro está corriendo

    // Constructor que inicializa el cronómetro
    public Chronometer() {
        delta = 0; // Inicializa el tiempo transcurrido a 0
        lastTime = System.currentTimeMillis(); // Guarda el tiempo actual en milisegundos
        running = false; // Inicializa el cronómetro como detenido
    }

    // Inicia el cronómetro con un tiempo objetivo
    public void run(long time) {
        running = true; // Inicia el cronómetro
        this.time = time; // Asigna el tiempo objetivo
    }

    // Actualiza el cronómetro, incrementando el 'delta' con el tiempo transcurrido
    public void update() {
        if (running) { // Si el cronómetro está corriendo
            delta += System.currentTimeMillis() - lastTime; // Calcula el tiempo transcurrido desde la última actualización
            if (delta >= time) { // Si el tiempo transcurrido supera el tiempo objetivo
                running = false; // Detiene el cronómetro
                delta = 0; // Reinicia el tiempo transcurrido
            }
        }
        lastTime = System.currentTimeMillis(); // Actualiza el último tiempo registrado
    }

    // Devuelve si el cronómetro está corriendo
    public boolean isRunning() {
        return running;
    }

    // Detiene el cronómetro y reinicia el tiempo transcurrido
    public void stop() {
        running = false; // Detiene el cronómetro
        delta = 0; // Reinicia el tiempo transcurrido
    }

    // Devuelve si el cronómetro ha terminado (no está corriendo y el tiempo transcurrido fue reiniciado)
    public boolean isFinished() {
        return !running && delta == 0; // El cronómetro ha terminado si no está corriendo y el tiempo fue reseteado
    }
}
