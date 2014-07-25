package doge;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.newdawn.slick.opengl.Texture;

public class Footsoldier extends Enemy {
	
	public Footsoldier(int x, int y) {
		super(x, y);
		image = Main.loadTexture("footsoldier", "png");
	}

	@Override
	public void draw() {
		image.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2i(x, y);
		glTexCoord2f(1,0);
		glVertex2i(x+30, y);
		glTexCoord2f(1,1);
		glVertex2i(x+30, y+32);
		glTexCoord2f(0,1);
		glVertex2i(x, y+32);
		glEnd();
	}

	@Override
	public PongDoge attack(PongDoge doge) {
		return doge;
	}

	@Override
	public void move() {
		if(!isAllyOfDoge) 
			x-=2;
		else
			x+=2; 
	}

}
