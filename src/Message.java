

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;



public class Message {
	private float alpha;
	private String text;
	private Vector2D position;
	private Color color;
	private boolean center;
	private boolean fade;
	private Font font;
	private final float deltaAlpha = 0.01f;
	private boolean dead;
	
	public Message(Vector2D position, boolean fade, String text, Color color,
			boolean center, Font font) {
		this.font = font;
		this.text = text;
		this.position = position;
		this.fade = fade;
		this.color = color;
		this.center = center;
		this.dead = false;
		
		if(fade)
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
	
	
	public boolean isDead() {return dead;}

	
}