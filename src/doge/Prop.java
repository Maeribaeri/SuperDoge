package doge;

import org.newdawn.slick.opengl.Texture;


public class Prop implements EntityInterface {

	public boolean damagesDoge;
	public boolean moveable;
	Texture t;
	int x, y, width, height, damageAmount, moveAmountX, moveAmountY;
	
	public Prop (int x, int y, int width, int height, Texture t, boolean damagesDoge, boolean moveable, int damageAmount, int moveAmountX, int moveAmountY) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.t = t;
		this.damagesDoge = damagesDoge;
		this.moveable = moveable;
		this.damageAmount = damageAmount;
		this.moveAmountX = moveAmountX;
		this.moveAmountY = moveAmountY;
	}
	
	@Override
	public void draw() {
		Main.renderAPicture(t, x, x+width, y, y+height);
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
		return this.x < other.getX()+width && this.x+width > other.getX() && this.y < other.getY()+height && this.y+height > other.getY();
	}

	@Override
	public PongDoge attack(PongDoge doge) {
		if (damagesDoge && this.intersects(doge)) {
			doge.setLife(doge.getLife()-damageAmount);
		}
		return doge;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}
