public class Vector2D {
    private double x, y; // Componentes del vector en los ejes X y Y

    // Constructor que recibe las componentes x e y
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Constructor por defecto, inicializa el vector en (0,0)
    public Vector2D() {
        x = 0;
        y = 0;
    }

    // Suma dos vectores
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.getX(), y + v.getY());
    }

    // Resta dos vectores
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.getX(), y - v.getY());
    }

    // Escala el vector por un valor
    public Vector2D scale(double value) {
        return new Vector2D(x * value, y * value);
    }

    // Limita el vector a una magnitud máxima
    public Vector2D limit(double value) {
        // Si la magnitud del vector es mayor que el valor, lo normaliza y escala al valor
        if (getMagnitude() > value) {
            return this.normalize().scale(value);
        }
        return this; // Si no, retorna el vector sin cambios
    }

    // Normaliza el vector, es decir, lo convierte en un vector unitario (magnitud 1)
    public Vector2D normalize() {
        double magnitude = getMagnitude();
        return new Vector2D(x / magnitude, y / magnitude);
    }

    // Devuelve la magnitud del vector
    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    // Establece la dirección del vector según un ángulo
    public Vector2D setDirection(double angle) {
        double magnitude = getMagnitude(); // Conserva la magnitud original
        return new Vector2D(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
    }

    // Devuelve el ángulo del vector con respecto al eje X (en radianes)
    public double getAngle() {
        return Math.asin(y / getMagnitude());
    }

    // Getters y setters para las componentes x e y
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
/*Esta clase es útil para representar vectores en un espacio bidimensional,
como direcciones, posiciones o velocidades en un juego o simulación. Permite
realizar operaciones comunes con vectores como suma, resta, escalado,
 normalización y limitación. */