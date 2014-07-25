package doge;

import org.newdawn.slick.opengl.Texture;

public abstract class Enemy implements EntityInterface {
	
	protected int x, y;
	protected boolean isAllyOfDoge = false;
	protected Texture image;
	int hp = 10;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setReversed(boolean isAllyOfDoge) {
		this.isAllyOfDoge = isAllyOfDoge;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public boolean intersects(PongDoge other) {
		return !(other.getY()+64<this.getY()) && !(other.getX()>this.getX()+60) && !(other.getY()>this.getY()+64) && !(other.getX()-64<this.getX());
	}

	public PongDoge attack(PongDoge pongdoge) {
		return pongdoge;
	}
	
	public boolean intersects(int x1, int x2, int y1, int y2) { //hitbox detection. precondition: x2>x1, y2>y1
		return !(y2<this.getY()) && !(x1>this.getX()+60) && !(y1>this.getY()+64) && !(x2<this.getX());
	}
}
