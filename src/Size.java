import java.awt.image.BufferedImage;



public enum Size {
	
	BIG(2, assets.meds), MED(2, assets.smalls), SMALL(2, assets.tinies), TINY(0, null);
	
	public int quantity;
	
	public BufferedImage[] textures;
	
	private Size(int quantity, BufferedImage[] textures){
		this.quantity = quantity;
		this.textures = textures;
	}
	
}