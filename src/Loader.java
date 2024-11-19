import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Loader {
    
    // Método estático que carga una imagen desde el path proporcionado.
    public static BufferedImage ImageLoader(String path) {
        try {
            // Lee la imagen desde el recurso especificado en el path y la retorna como BufferedImage.
            return ImageIO.read(Loader.class.getResource(path));
        } catch (IOException e) {
            // Si ocurre un error al leer la imagen, se imprime el error.
            e.printStackTrace();
        }
        // Si hay un error, se retorna null.
        return null;
    }

    // Método estático que carga una fuente TrueType (.ttf) desde el path proporcionado y la redimensiona.
    public static Font loadFont(String path, int size) {
        try {
            // Carga la fuente desde el recurso especificado y la redimensiona al tamaño especificado.
            return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            // Si ocurre un error al cargar la fuente, se imprime el error.
            e.printStackTrace();
            // Si hay un error, se retorna null.
            return null;
        }
    }
}
