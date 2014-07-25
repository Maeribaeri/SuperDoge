package doge;

import org.newdawn.slick.opengl.Texture;

public class Train extends Prop {

	private boolean moveUp = false;
	private Prop dispenser = new Prop(this.x, this.y, 150, 200, Main.loadTexture("dispenser", "png"), true, true, 0, 0, 0);
	private int originalX;
	
	public Train(int x, int y, int width, int height, Texture t) {
		super(x, y, width, height, t, true, true, 0, 0, 0);
		originalX = x;
	}

	public PongDoge attack(PongDoge doge) {
		if (!this.intersects(doge)) {
			doge.decrementLife();
			doge.setX((int)doge.getX()-5);
		}
		else {
			doge.setX((int)doge.getX()+2);
		}
		if (dispenser.intersects(doge) && doge.getLife()<=doge.maxLife*1.5) {
			doge.setLife(doge.getLife()+5);
		}
		if (this.x >= originalX + 7500) {
			doge.setHasKey(true);
		}
		return doge;
	}

	@Override
	public void move() {
		if (this.x <= originalX + 8000) {
			this.x += 2;
			dispenser.x += 2;
		}
		if (moveUp && this.y>256) {
			this.y -= 1;
			dispenser.y -= 1;
			moveUp = false;
		}
		else {
			moveUp = true;
		}
	}
	
	public void draw() {
		super.draw();
		Main.renderAPicture(dispenser.t, dispenser.x, dispenser.x+dispenser.width, dispenser.y, dispenser.y+dispenser.height);
	}
}
