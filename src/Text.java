import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * La clase `Text` proporciona un método estático para dibujar texto en la pantalla
 * con opciones de color, fuente, y alineación.
 */
public class Text {
    
    /**
     * Dibuja un texto en la pantalla con la configuración de color, fuente y posición proporcionada.
     *
     * @param g El objeto `Graphics` utilizado para dibujar en la pantalla.
     * @param text El texto que se quiere dibujar.
     * @param pos La posición inicial donde se debe dibujar el texto.
     * @param center Si es `true`, centra el texto en las coordenadas proporcionadas. Si es `false`, se dibuja en la posición proporcionada sin centrado.
     * @param color El color con el que se dibujará el texto.
     * @param font La fuente que se utilizará para el texto.
     */
    public static void drawText(Graphics g, String text, Vector2D pos, boolean center, Color color, Font font) {
        
        // Establecer el color y la fuente a utilizar
        g.setColor(color);
        g.setFont(font);

        // Crear una copia de la posición para manipularla sin afectar al objeto original
        Vector2D position = new Vector2D(pos.getX(), pos.getY());

        // Si 'center' es verdadero, centrar el texto
        if(center) {
            // Obtener las métricas de la fuente para calcular el ancho y la altura del texto
            FontMetrics fm = g.getFontMetrics();
            
            // Ajustar la posición X para centrar el texto
            position.setX(position.getX() - fm.stringWidth(text) / 2);
            
            // Ajustar la posición Y para centrar el texto verticalmente
            position.setY(position.getY() - fm.getHeight() / 2);
        }

        // Dibujar el texto en la posición calculada
        g.drawString(text, (int)position.getX(), (int)position.getY());
    }
}
