package doge;

import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;

public class LightBomb {
		
	int x, y;
	long lastFrame, timeExisting;
	boolean hasExplode = false;
	private boolean firstIteration = true;
	
	public LightBomb(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Texture t) {
		if (!hasExplode) {
			Main.renderAPicture(t, x, x+32, y, y+32);
		}
		else {
			Main.renderAPicture(t, x, x+256, y, y+128);
		}
	}
	
	public void move() {
		if (!hasExplode) {
			this.y += 3;
		}
		if (firstIteration) {
			lastFrame = this.getTime();
			firstIteration = false;
		}
		timeExisting += this.getDelta();
		if (timeExisting >= 2000 && !hasExplode) {
			hasExplode = true;
		}
	}
	
	public PongDoge attack(PongDoge doge) {
		if (!hasExplode) {
			if (PongDoge.intersects(x, x+32, y, y+32)) {
				doge.decrementLife();
			}
		}
		else {
			if (PongDoge.intersects(x, x+256, y, y+128)) {
				doge.setLife(doge.getLife()-2);
			}
		}
		return doge;
	}
	
	private long getTime() {
		return (Sys.getTime()*1000 / Sys.getTimerResolution());
	}
	
	private int getDelta() {
		long currentTime = this.getTime();
		int delta = (int) (currentTime - this.lastFrame);
		this.lastFrame = this.getTime();
		return delta;
	}
}