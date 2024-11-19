import java.awt.image.BufferedImage;

/**
 * La enumeración `Size` representa los diferentes tamaños de los meteoritos en el juego. 
 * Cada tamaño tiene una cantidad asociada de fragmentos que se crean cuando el meteorito es destruido,
 * así como las texturas correspondientes a cada tamaño de meteorito.
 */
public enum Size {

    BIG(2, assets.meds),  // Meteorito grande, genera 2 fragmentos, usa las texturas de "meds".
    MED(2, assets.smalls),  // Meteorito mediano, genera 2 fragmentos, usa las texturas de "smalls".
    SMALL(2, assets.tinies),  // Meteorito pequeño, genera 2 fragmentos, usa las texturas de "tinies".
    TINY(0, null);  // Meteorito diminuto, no genera fragmentos y no tiene texturas asociadas.

    public int quantity;  // La cantidad de fragmentos que se generan cuando un meteorito de este tamaño es destruido.
    
    public BufferedImage[] textures;  // Las texturas correspondientes a este tamaño de meteorito.

    /**
     * Constructor de la enumeración `Size`. Asigna la cantidad de fragmentos y las texturas 
     * correspondientes a cada tamaño de meteorito.
     *
     * @param quantity La cantidad de fragmentos generados al destruir el meteorito.
     * @param textures Las texturas asociadas al tamaño del meteorito.
     */
    private Size(int quantity, BufferedImage[] textures) {
        this.quantity = quantity;  // Asignar la cantidad de fragmentos.
        this.textures = textures;  // Asignar las texturas asociadas al tamaño del meteorito.
    }
}
