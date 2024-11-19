import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Message {
    private float alpha; // Nivel de transparencia del mensaje
    private String text; // Texto a mostrar
    private Vector2D position; // Posición del mensaje en la pantalla
    private Color color; // Color del texto
    private boolean center; // Determina si el texto debe estar centrado
    private boolean fade; // Si es true, el mensaje se desvanecerá con el tiempo
    private Font font; // Fuente que se usará para el texto
    private final float deltaAlpha = 0.01f; // La velocidad del desvanecimiento
    private boolean dead; // Si el mensaje está muerto (desvanecido por completo)

    // Constructor que inicializa todos los atributos del mensaje
    public Message(Vector2D position, boolean fade, String text, Color color,
                boolean center, Font font) {
        this.font = font;
        this.text = text;
        this.position = position;
        this.fade = fade;
        this.color = color;
        this.center = center;
        this.dead = false;

        // Si fade es verdadero, comenzamos con un alpha de 1 (completamente visible)
        // Si fade es falso, comenzamos con un alpha de 0 (invisible)
        if (fade)
            alpha = 1;
        else
            alpha = 0;
    }
	public void draw(Graphics2D g2d) {
		// Limitar alpha al rango válido
		alpha = Math.max(0, Math.min(1, alpha));
		
		// Usar el alpha para establecer la transparencia
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		
		// Dibujar el texto
		Text.drawText(g2d, text, position, center, color, font);
		
		// Restaurar la transparencia
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		// Actualizar posición
		position.setY(position.getY() - 1);
		
		// Actualizar alpha según fade
		if (fade) {
			alpha -= deltaAlpha;
			if (alpha <= 0) {
				alpha = 0;
				dead = true;
			}
		} else {
			alpha += deltaAlpha;
			if (alpha >= 1) {
				alpha = 1;
				fade = true; // Iniciar desvanecimiento
			}
		}
	}

    // Método para dibujar el mensaje en la pantalla

    public boolean isDead() {return dead;}

}