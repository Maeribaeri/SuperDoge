package doge;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class RoyalGuard extends Enemy {

	Texture t = Main.loadTexture("royalguard", "png");
	Texture sword = Main.loadTexture("Iron_Spear", "png");
	ArrayList<Integer> validXValues = new ArrayList<Integer>();
	ArrayList<Integer> validYValues = new ArrayList<Integer>();
	private boolean isFacingRight;
	
	public RoyalGuard(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw() {
		if (!isFacingRight)
			Main.renderAPicture(t, x, x+71, y, y+112);
		else {
			t.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(1,0);	//1,0
			glVertex2i(x, y);
			glTexCoord2f(0,0);	//0,0
			glVertex2i(x+71, y);
			glTexCoord2f(0,1);	//0,1
			glVertex2i(x+71, y+112);
			glTexCoord2f(1,1);	//1,1
			glVertex2i(x, y+112);
			glEnd();
		}
	}

	@Override
	public void move() {
		int targetx = (int) PongDoge.leDoge.getX();
		int targety = (int) PongDoge.leDoge.getY();
		if (targetx < this.x) {
			this.x -= 2;
			isFacingRight = false;
		}
		else if (targetx > this.x + 64) {
			this.x += 2;
			isFacingRight = true;
		}
		if (targety < this.y) {
			this.y -= 2;
		}
		else if (targety > this.y + 64) {
			this.y += 2;
		}
	}	
	
	public PongDoge attack(PongDoge doge) {
		if (!isFacingRight) {
			sword.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2i(x, y);
			glTexCoord2f(1,0);
			glVertex2i(x-64, y);
			glTexCoord2f(1,1);
			glVertex2i(x-64, y+64);
			glTexCoord2f(0,1);
			glVertex2i(x, y+64);
			glEnd();
			if (PongDoge.intersects(x-64, x, y, y+64)) {
				doge.decrementLife();
			}
		}
		else {
			sword.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2i(x+64, y);
			glTexCoord2f(1,0);
			glVertex2i(x+128, y);
			glTexCoord2f(1,1);
			glVertex2i(x+128, y+64);
			glTexCoord2f(0,1);
			glVertex2i(x+64, y+64);
			glEnd();
			if (PongDoge.intersects(x+64, x+128, y, y+64)) {
				doge.decrementLife();
			}
		}
		return doge;
	}
	
	public static int distanceFormula(EntityInterface a, EntityInterface b) {
		return -1;
	}
}
