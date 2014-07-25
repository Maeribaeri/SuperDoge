package doge;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.Music;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.WaveData;
import org.newdawn.slick.opengl.Texture;

public class Location {

	public Texture currentBackground;
	public int width, height;
	private int translatex, translatey;
	private Dialogue[] dialogues;
	private Prop[] props;
	private Key key;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private TrueTypeFont font = new TrueTypeFont(new Font("Comic Sans MS", Font.BOLD, 12), true);
	private long lastFrame;
	private long timeSinceLastEnemy = 0;
	private Enemy enemy;
	private int enemyLimit;
	private int enemiesSpawned = 0;
	public Audio oggEffect;
	
	public Location(Texture background, int width, int height, Dialogue[] dialogues, Prop[] props, Key key, Enemy enemy, int enemyLimit, Audio oggEffect) {
		this.currentBackground = background;
		this.width = width;
		this.height = height;
		this.dialogues = dialogues;
		this.props = props;
		this.key = key;
		this.enemies.add(enemy);
		this.enemy = enemy;
		this.enemyLimit = enemyLimit;
		this.oggEffect = oggEffect;
	}
	
	public PongDoge actOneFrame(PongDoge pongdoge, int x, int y) {
		if (oggEffect != null) {
			oggEffect.playAsMusic(1.0f, 1.0f, true);
		}
		Random r = new Random();
		if (timeSinceLastEnemy > 1000 && enemiesSpawned <= enemyLimit && enemy != null && enemy instanceof RoyalGuard) {
			System.out.println("A");
			enemiesSpawned++;
			timeSinceLastEnemy = 0;
			enemies.add(new RoyalGuard(((int)pongdoge.getX()-200)+r.nextInt(400), ((int)pongdoge.getY()-200)+r.nextInt(400)));
		}
		else if (timeSinceLastEnemy > 1000 && enemiesSpawned <= enemyLimit && enemy != null && enemy instanceof LightBomber) {
			System.out.println("B");
			enemiesSpawned++;
			timeSinceLastEnemy = 0;
			enemies.add(new LightBomber((int)pongdoge.getX()-500, ((int)pongdoge.getY()-200)-r.nextInt(400)));
		}
		if (enemiesSpawned > enemyLimit && enemies != null && enemies.size() == 0) {
			pongdoge.setHasKey(true);
		}
		timeSinceLastEnemy += getDelta();
		glTranslatef(translatex, translatey, 0);
		currentBackground.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2i(x, y);
		glTexCoord2f(1,0);
		glVertex2i(x+width, y);
		glTexCoord2f(1,1);
		glVertex2i(x+width, y+height);
		glTexCoord2f(0,1);
		glVertex2i(x, y+height);
		glEnd();
		translatex = 0;
		translatey = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			translatey+=6;		    			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			translatex+=6;		    			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			translatey-=6;		    			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			translatex-=6;
		}
		pongdoge.move();
		if (key != null) {
			key = pongdoge.attack(key);
		}
		if (dialogues != null) {
			for (int i=0; i<dialogues.length; i++) {
				if (dialogues[i] != null) {
					dialogues[i].draw();
				}
			}
		}
		if (props != null) {
			for (int i=0; i<props.length; i++) {
				if (props[i] != null) {
					props[i].draw();
				}
				if (props[i] instanceof Train) {
					translatex = -2;
					pongdoge = props[i].attack(pongdoge);
					props[i].move();
				}
			}
		}
		if (key != null) {
			key.draw();
			pongdoge = key.attack(pongdoge);
		}
		if (enemies != null) {
			for (int i=0; i<enemies.size(); i++) {
				if (enemies.get(i) != null) {
					enemies.get(i).draw();
					enemies.get(i).move();
					pongdoge = enemies.get(i).attack(pongdoge);
				}
			}
			enemies = pongdoge.attack(enemies);
		}
		font.drawString((float)pongdoge.getX(), (float)pongdoge.getY()-25, "HP: " + pongdoge.getLife());
		font.drawString((float)pongdoge.getX()+100, (float)pongdoge.getY()+100, "Score: " + pongdoge.getScore());
		pongdoge.draw();
		return pongdoge;
	}
	
	private long getTime() {
		return (Sys.getTime()*1000 / Sys.getTimerResolution());
	}
	
	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
}

//int[] a = {1,2,3};
//a = new int[] {4,5,6};
//Strings are immutable: manipulating them creates a new copy, so have to return to change in method
//Primitives are passed by value: have to return
//Other objects (etc. PacMan) update in new method as long as you don't allocate new one; same with arrays
//Child class will only inherit no-parameters-passed constructors
