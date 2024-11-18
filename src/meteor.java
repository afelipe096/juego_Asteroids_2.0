import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;



public class meteor extends MovingObject{

	private Size size;
	
	public meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, gameState gameState, Size size) {
		super(position, velocity, maxVel, texture, gameState);
		this.size = size;
		this.velocity = velocity.scale(maxVel);
		
	}

	@Override
	public void update() {
		position = position.add(velocity);
		
		if(position.getX() > Constants.WIDTH)
			position.setX(-width);
		if(position.getY() > Constants.HEIGHT)
			position.setY(-height);
		
		if(position.getX() < -width)
			position.setX(Constants.WIDTH);
		if(position.getY() < -height)
			position.setY(Constants.HEIGHT);
		
		angle += Constants.DELTANGLE/2;
		
	}
	
	@Override
	public void Destroy(){
		gameState.divideMeteor(this);
		super.Destroy();
	}
	
	
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		
		at.rotate(angle, width/2, height/2);
		
		g2d.drawImage(Texture, at, null);
	}

	public Size getSize(){
		return size;
	}
	
	
}