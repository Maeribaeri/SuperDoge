package doge;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.newdawn.slick.opengl.Texture;

public class Grimmjow extends Enemy {

	private int timesMoved = 0;
	Texture sword;
	
	public Grimmjow(int x, int y) {
		super(x, y);
		image = Main.loadTexture("grimmjow", "jpg");
		sword = Main.loadTexture("actual sword", "png");
	}

	@Override
	public void draw() {
		image.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2i(x, y);
		glTexCoord2f(1,0);
		glVertex2i(x+60, y);
		glTexCoord2f(1,1);
		glVertex2i(x+60, y+64);
		glTexCoord2f(0,1);
		glVertex2i(x, y+64);
		glEnd();
	}
	
	public PongDoge attack(PongDoge pongdoge) {
		if (timesMoved > 50) {
			sword.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2i(x, y);
			glTexCoord2f(1,0);
			glVertex2i(x-128, y);
			glTexCoord2f(1,1);
			glVertex2i(x-128, y+64);
			glTexCoord2f(0,1);
			glVertex2i(x, y+64);
			glEnd();
			if (PongDoge.intersects(x-128, x, y, y+64)) {
				pongdoge.decrementLife();
			}
		}
		return pongdoge;
	}
	
	@Override
	public void move() {
		if (!isAllyOfDoge)
			x-=6;
		else
			x+=6;
		
		timesMoved++;
	}
}
