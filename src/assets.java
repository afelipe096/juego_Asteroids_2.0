
import java.awt.Font;
import java.awt.image.BufferedImage;

public class assets {

    public static  BufferedImage player;
    
    public  static BufferedImage speed;//efectos

    public static BufferedImage[] numbers = new BufferedImage[11];//numeros

    //lasers
    public static BufferedImage bluLaser, greenLaser, redLaser;

    public static BufferedImage life;

    //fonts
    public static Font fontBig;
    public static Font fontMed;

    //meteoritos
    public  static BufferedImage[] bigs = new BufferedImage[4];
    public  static BufferedImage[] meds = new BufferedImage[2];
    public  static BufferedImage[] smalls = new BufferedImage[2];
    public  static BufferedImage[] tinies = new BufferedImage[2];    

    public static void init() {
        
        player = Loader.ImageLoader("resources/nave_red.png"); 
        speed = Loader.ImageLoader("resources/fire18.png");
        bluLaser = Loader.ImageLoader("resources/laserBlue01.png");
        greenLaser = Loader.ImageLoader("resources/laserGreen11.png");
        redLaser = Loader.ImageLoader("resources/laserRed01.png");
        life = Loader.ImageLoader("/resources/score/playerLife.png");
        fontBig = Loader.loadFont("/resources/Fonts/vector_future.ttf", 42);
        fontMed = Loader.loadFont("/resources/Fonts/vector_future.ttf", 20);
    
        for (int i = 0; i < bigs.length; i++) {
            bigs[i] = Loader.ImageLoader("/resources/meteor/big"+(i+1)+".png");
        }
        for (int i = 0; i < meds.length; i++) {
            meds[i] = Loader.ImageLoader("/resources/meteor/med"+(i+1)+".png");
        }
        for (int i = 0; i < smalls.length; i++) {
            smalls[i] = Loader.ImageLoader("/resources/meteor/small"+(i+1)+".png");
        }
        for (int i = 0; i < tinies.length; i++) {
            tinies[i] = Loader.ImageLoader("/resources/meteor/tiny"+(i+1)+".png");
        }
        for(int i = 0; i < numbers.length; i++)
			numbers[i] = Loader.ImageLoader("/resources/score/"+i+".png");
    }    
}
