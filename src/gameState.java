import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class gameState {

    private Player player;
    private ArrayList<MovingObject> movingObjects = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private int score = 0;
    private int lives = 3;
    private  int meteor ;
    private int waves = 1;

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

    public void addScore(int value, Vector2D position) {
		score += value;
		messages.add(new Message(position, true, "+"+value+" score", Color.WHITE, false, assets.fontMed, this));
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

    private void startWave(){
		
		messages.add(new Message(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2), false,
				"WAVE "+waves, Color.WHITE, true, assets.fontBig, this));
		
		double x, y;
		
		for(int i = 0; i < meteor; i++){
			x = i % 2 == 0 ? Math.random()*Constants.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random()*Constants.HEIGHT;
			
			BufferedImage texture = assets.bigs[(int)(Math.random()*assets.bigs.length)];
			
			movingObjects.add(new meteor(
					new Vector2D(x, y),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_VEL*Math.random() + 1,
					texture,
					this,
					Size.BIG
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
        
        for(int i = 0; i < messages.size(); i++)
			messages.get(i).draw(g2d);
        // Dibujar todos los objetos
        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).draw(g);
        }

        drawScore(g);
        drawLives(g);
        Text.drawText(g, "WAVE"+ waves,new Vector2D(Constants.WIDTH/2,Constants.HEIGHT/2),true, Color.WHITE, assets.fontBig);
    }


    private void drawScore(Graphics g){
        Vector2D pos = new Vector2D(850,25);
        String scoreToString = Integer.toString(score);
        for (int i = 0; i < scoreToString.length(); i++) {
            g.drawImage(assets.numbers[Integer.parseInt(scoreToString.substring(i, i +1))],
            (int)pos.getX(), (int)pos.getY(),null);
            pos.setX(pos.getX() + 20);
        }
    }

    private void drawLives(Graphics g){
		
		Vector2D livePosition = new Vector2D(25, 25);
		
		g.drawImage(assets.life, (int)livePosition.getX(), (int)livePosition.getY(), null);
		
		g.drawImage(assets.numbers[10], (int)livePosition.getX() + 40,
				(int)livePosition.getY() + 5, null);
		
		String livesToString = Integer.toString(lives);
		
		Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());
		
		for(int i = 0; i < livesToString.length(); i ++)
		{
			int number = Integer.parseInt(livesToString.substring(i, i+1));
			
			if(number <= 0)
				break;
			g.drawImage(assets.numbers[number],
					(int)pos.getX() + 60, (int)pos.getY() + 5, null);
			pos.setX(pos.getX() + 20);
		}
		
	}

    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    public void setMovingObjects(ArrayList<MovingObject> movingObjects) {
        this.movingObjects = movingObjects;
    }

    public ArrayList<Message> getMessages() {
		return messages;
	}

    public Player getPlayer() {
		return player;
	}
	
	public void subtractLife() {lives --;}
}
