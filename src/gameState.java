import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class gameState {

    private Player player;
    private ArrayList<MovingObject> movingObjects = new ArrayList<>();
    private  int meteor ;

    public gameState() {
        player = new Player(
            new Vector2D(100, 500), 
            new Vector2D(), 
            7, 
            assets.player, 
            this
        );
        movingObjects.add(player);

        meteor = 1;

        startWave();
    }

    public void  divideMeteor(meteor meteor){
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

        for (int i = 0; i < size.quantity; i++) {
            movingObjects.add(new meteor(
                meteor.getPosition(),
                new Vector2D(0 , 1).setDirection(Math.random()*Math.PI*2),
                Constants.METEOR_VEL*Math.random() + 1,
                textures[(int)(Math.random()*textures.length)],this,newSize
            ));
        }

    }

    public  void  startWave(){
        double x,y;
        for (int i = 0; i < meteor; i++) {
            x = i % 2 == 0 ? Math.random()*Constants.WIDTH : 0;
            y = i % 2 == 0 ? 0 : Math.random()*Constants.HEIGHT;

            BufferedImage texture = assets.bigs[(int)(Math.random()*assets.bigs.length)];
            movingObjects.add(new meteor(
                new  Vector2D(x, y),
                new Vector2D(0 , 1).setDirection(Math.random()*Math.PI*2),
                Constants.METEOR_VEL*Math.random() + 1,
                texture,this,Size.BIG
            ));
        }
        meteor ++;

    }

    public void update() {
        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).update();
        }
        for (int i = 0; i < movingObjects.size(); i++) {
            if (movingObjects.get(i) instanceof meteor) {
                return;
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Configurar el renderizado para suavizado de imágenes
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Dibujar todos los objetos
        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).draw(g);
        }
    }

    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    public void setMovingObjects(ArrayList<MovingObject> movingObjects) {
        this.movingObjects = movingObjects;
    }
}
