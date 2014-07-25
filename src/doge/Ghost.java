package doge;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

public class Ghost extends Enemy {

	public Ghost(int x, int y) {
		super(x, y);
		image = Main.loadTexture("ghost","gif");
	}

	@Override
	public void draw() {
		image.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2i(x, y);
		glTexCoord2f(1,0);
		glVertex2i(x+15, y);
		glTexCoord2f(1,1);
		glVertex2i(x+15, y+16);
		glTexCoord2f(0,1);
		glVertex2i(x, y+16);
		glEnd();
	}

	@Override
	public PongDoge attack(PongDoge doge) {
		return doge;
	}

	@Override
	public void move() {
		if(!isAllyOfDoge)
			x-=3;
		else 
			x+=3;
	}	

}
