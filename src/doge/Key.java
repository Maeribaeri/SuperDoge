package doge;

import org.newdawn.slick.opengl.Texture;

public class Key implements EntityInterface {

	private int x, y;
	private Texture t = Main.loadTexture("bosskey", "png");
	boolean isVisible;
	
	public Key(int x, int y, boolean isVisible) {
		this.x = x;
		this.y = y;
		this.isVisible = isVisible;
	}
	
	@Override
	public void draw() {
		if (isVisible)
		Main.renderAPicture(t, x, x+64, y, y+64);
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public boolean intersects(PongDoge other) {
		return this.x < other.getX()+64 && this.x+64 > other.getX() && this.y < other.getY()+64 && this.y+64 > other.getY();
	}
	
	public boolean intersects(int x1, int x2, int y1, int y2) { //hitbox detection. precondition: x2>x1, y2>y1
		return !(y2<this.getY()) && !(x1>this.getX()+60) && !(y1>this.getY()+64) && !(x2<this.getX());
	}

	@Override
	public PongDoge attack(PongDoge doge) {
		if (intersects(doge) && isVisible) {
			doge.setHasKey(true);
		}
		return doge;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
}
