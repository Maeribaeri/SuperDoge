package doge;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.WaveData;

public class Level {

	//REMINDER: Macbeth group on Saturday, robotics bbq sunday, bring dessert
	
	public ArrayList<Location> locationsInLevel = new ArrayList<Location>();
	boolean[] hasKey = new boolean[10];
	
	public void addLocation(Location loc) {
		locationsInLevel.add(loc);
	}
	
	public PongDoge run(PongDoge pongdoge) {
		if (pongdoge.getX() <= locationsInLevel.get(0).width && pongdoge.getX() >= 0 && pongdoge.getY() <= locationsInLevel.get(0).height && pongdoge.getY() >= 0) {
			pongdoge = locationsInLevel.get(0).actOneFrame(pongdoge, 0, 0);
		}
		else if (pongdoge.getX() < 0 && pongdoge.getX() >= -locationsInLevel.get(1).width && pongdoge.getY() <= locationsInLevel.get(1).height && pongdoge.getY() >= 0) {
			pongdoge = locationsInLevel.get(1).actOneFrame(pongdoge, -1024, 0);
			if (pongdoge.isHasKey() == true) {
				pongdoge.setHasKey(false);
				hasKey[2] = true;
			}
		}
		else if (pongdoge.getX() < locationsInLevel.get(0).width + locationsInLevel.get(2).width && pongdoge.getX() >= locationsInLevel.get(0).width && pongdoge.getY() <= 1024 && pongdoge.getY() >= -1024 && hasKey[2]) {
			pongdoge = locationsInLevel.get(2).actOneFrame(pongdoge, 1024, -1024);
			if (pongdoge.isHasKey() == true) {
				pongdoge.setHasKey(false);
				hasKey[3] = true;
			}
		}
		else if (pongdoge.getX() < locationsInLevel.get(0).width + locationsInLevel.get(2).width + locationsInLevel.get(3).width && pongdoge.getX() >= locationsInLevel.get(0).width + locationsInLevel.get(2).width && pongdoge.getY() <= 1024 && pongdoge.getY() >= 512 && hasKey[3]) {
			pongdoge = locationsInLevel.get(3).actOneFrame(pongdoge, 5120, 512);
		}
		else if (pongdoge.getX() < locationsInLevel.get(0).width + locationsInLevel.get(2).width + locationsInLevel.get(3).width + locationsInLevel.get(4).width && pongdoge.getX() >= locationsInLevel.get(0).width + locationsInLevel.get(2).width + 
			locationsInLevel.get(3).width && pongdoge.getY() <= 1024 && pongdoge.getY() >= -1024) {
			pongdoge = locationsInLevel.get(4).actOneFrame(pongdoge, 6144, -1024);
			if (pongdoge.isHasKey() == true) {
				pongdoge.setHasKey(false);
				hasKey[5] = true;
			}
		}
		else if (pongdoge.getX() < locationsInLevel.get(0).width + locationsInLevel.get(2).width + locationsInLevel.get(3).width + locationsInLevel.get(4).width + locationsInLevel.get(5).width && pongdoge.getX() >= locationsInLevel.get(0).width + locationsInLevel.get(2).width + 
			locationsInLevel.get(3).width + locationsInLevel.get(4).width && pongdoge.getY() <= 1024 && pongdoge.getY() >= 0) {
			pongdoge = locationsInLevel.get(5).actOneFrame(pongdoge, 14336, 0);
		}
		else {
			pongdoge.draw();
			pongdoge.move();
		}
		return pongdoge;
	}
}
