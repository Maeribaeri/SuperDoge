package doge;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;

public class LightBomber extends Enemy {
	
	Texture t = Main.loadTexture("lightbomber", "png");
	private ArrayList<LightBomb> lightbombs = new ArrayList<LightBomb>();
	private long lastFrame, timeSinceLastBomb;
	private boolean firstIteration = true;
	private Texture bombTexture = Main.loadTexture("lightbomb", "png");
	private Texture explosionTexture = Main.loadTexture("smallexplosionstage1", "jpg");
	
	public LightBomber(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void draw() {
		Main.renderAPicture(t, x, x+128, y, y+128);
	}

	@Override
	public void move() {
		this.x += 6;
	}
	
	public PongDoge attack(PongDoge doge) {
		if (firstIteration) {
			lastFrame = getTime();
			firstIteration = false;
		}
		timeSinceLastBomb += getDelta();
		if (timeSinceLastBomb >= 1000) {
			timeSinceLastBomb = 0;
			lightbombs.add(new LightBomb(this.x,this.y));
		}
		for (int i=0; i<lightbombs.size(); i++) {
			lightbombs.get(i).move();
			if (lightbombs.get(i).hasExplode == false) {
				lightbombs.get(i).draw(bombTexture);
			}
			else {
				lightbombs.get(i).draw(explosionTexture);
			}
			doge = lightbombs.get(i).attack(doge);
			if (lightbombs.get(i).timeExisting > 6000) {
				lightbombs.remove(i);
				i--;
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
