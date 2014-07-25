package doge;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.TrueTypeFont;

public class Main {

	public static final int width = 900;
	public static final int height = 600;
	
	private boolean isInitialized;
	private Level[] levels = new Level[10];
	private boolean[] levelsCompleted = new boolean[10];
	public String state = "Intro";
	Texture currentTexture;
	private TrueTypeFont font;
	private long lastFrame;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private long timeSinceLastEnemy;
	private int survivalStartTime;
	
	public void run() throws UnsupportedAudioFileException, IOException {
	    try {
	    	Display.setTitle("The Adventures of SuperDoge");
	    	Display.setInitialBackground(0.0f, 0.0f, 0.125f);
		    Display.setDisplayMode(new DisplayMode(width,height)); //width, height
		    Display.create();
		} catch (LWJGLException e) {
		    e.printStackTrace();
		    System.exit(0);
		}
	    
	    // init OpenGL here
	    glMatrixMode(GL_PROJECTION);//enters gl into matrix mode
	    glLoadIdentity();
	    glOrtho(0,640,480,0,1,-1);//sets orientation/dimensions
	    glMatrixMode(GL_MODELVIEW);    
	    glEnable(GL_TEXTURE_2D);
	    GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    
        lastFrame = getTime();
        
        PongDoge pongdoge = new PongDoge();
        
    	Font awtFont = new Font("Comic Sans MS", Font.BOLD, 50);
		font = new TrueTypeFont(awtFont, true);
		
		float translatex = 0;
		float translatey = 0;
		
		Texture intro1 = loadTexture("bigdoge","jpg");
		Texture intro2 = loadTexture("title","png");
		
		initializeSinglePlayerStage();
        
	    while (!Display.isCloseRequested()) {
	    	
	    	glTranslatef(translatex, translatey, 0);
	    	
	    	glClear(GL_COLOR_BUFFER_BIT);
	    	
	    	if (state.equals("Intro")) {
	    		renderAPicture(intro1, 25, 250, 25, 306-25);
	    		renderAPicture(intro2, 0, 1100, 375, 525);
	    		if (Mouse.isButtonDown(0)) {
	    			state = "Main Menu";
	    		}
	    	}
	    	
	    	else if (state.equals("Main Menu")) {
	    		font.drawString(100.0f, 50.0f, "1 Player");
	    		font.drawString(380.0f, 50.0f, "2 Player");
	    		font.drawString(240.0f, 150.0f, "Survival");
	    		int x = Mouse.getX();
	    		int y = height - Mouse.getY();
	    		if (Mouse.isButtonDown(0) && x>100 && x<300 && y>50 && y<200) {
	    			state = "1 Player";
	    			System.out.println(state);
	    		}
	    		else if (Mouse.isButtonDown(0) && x>600 && x<800 && y>50 && y<200) {
	    			state = "2 Player";
	    			System.out.println(state);
	    		}
	    		else if (Mouse.isButtonDown(0) && x>350 && x<550 && y>150 && y<300) {
	    			pongdoge = new PongDoge();
	    			lastFrame = getTime();
	    			state = "Survival";
	    			System.out.println(state);
	    			survivalStartTime = (int) getTime();
	    		}
	    	}
	    	
	    	else if (state.equals("1 Player")) {
	    		Font awtFont2 = new Font("Comic Sans Ms", Font.BOLD, 18);
	    		TrueTypeFont font2 = new TrueTypeFont(awtFont2, true);
	    		font2.drawString(0.0f, 0.0f, "Once upon a time, in Equestria, the land of many magic,");
	    		font2.drawString(0.0f, 50.0f, "There was a soul reaper named Doge.");
	    		font2.drawString(0.0f, 100.0f, "Doge was special as there were no other soul reapers in Equestria.");
	    		font2.drawString(0.0f, 150.0f, "One day, Celestia found the bag of Tirek and turned much evil.");
	    		font2.drawString(0.0f, 200.0f, "She brainwashed Equestria and decided to invade Earth.");
	    		font2.drawString(0.0f, 250.0f, "And Doge was stuck in the middle of it all.");
	    		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) this.state = "singleplayerplay";
	    	}
	    	
	    	else if (state.equals("singleplayerplay")) {
	    		System.out.println(pongdoge.getX() + " " + pongdoge.getY());
	    		if (pongdoge.getLife() <= 0) {
	    			state = "Game Over";
	    		}
	    		if (levelsCompleted[0] == false) {
	    			if (!isInitialized) {
	    				pongdoge.setX(300);
    					pongdoge.setY(225);
    					isInitialized = true;
	    			}
	    			pongdoge = levels[0].run(pongdoge);
	    		} 
	    	}
	    	
	    	else if (state.equals("Survival")) {
	    		int intervalBetweenSpawns = 2000;
	    		if ((int)getTime() - survivalStartTime > 40000) {
	    			intervalBetweenSpawns = 1000;
	    			font.drawString(450.0f, 400.0f, "Level 5");
	    		}
	    		else if ((int)getTime() - survivalStartTime > 30000) {
	    			intervalBetweenSpawns = 1250;
	    			font.drawString(450.0f, 400.0f, "Level 4");
	    		}
	    		else if ((int)getTime() - survivalStartTime > 20000) {
	    			intervalBetweenSpawns = 1500;
	    			font.drawString(450.0f, 400.0f, "Level 3");		
	    		}
	    		else if ((int)getTime() - survivalStartTime > 10000) {
	    			intervalBetweenSpawns = 1750;
	    			font.drawString(450.0f, 400.0f, "Level 2");
	    		}
	    		else {
	    			font.drawString(450.0f, 400.0f, "Level 1");
	    		}
	    		pongdoge.draw();
	    		pongdoge.move();
	    		enemies = pongdoge.attack(enemies);
	    		int delta = getDelta(); // time since last frame, ms
	    		this.timeSinceLastEnemy += delta;
	    		if (timeSinceLastEnemy > intervalBetweenSpawns) {
	    			System.out.println("Enemy approaching...");
	    			Random r = new Random();
	    			int selection = r.nextInt(7);
	    			if (selection == 0) {
	    				enemies.add(new Grimmjow(890, r.nextInt(426))); 
	    			}
	    			else if (selection == 1) {
	    				enemies.add(new Ghost(650, r.nextInt(480)));
	    			}
	    			else if (selection == 2) {
	    				enemies.add(new Troll(650, r.nextInt(480)));
	    			}
	    			else if (selection == 3) {
	    				enemies.add(new Wolf(r.nextInt(650), 0));
	    			}
	    			else {
	    				int height = r.nextInt(400);
	    				enemies.add(new Footsoldier(890, height));
	    				enemies.add(new Footsoldier(890, height-32));
	    				enemies.add(new Footsoldier(890, height+32));
	    			}
	    			timeSinceLastEnemy = 0;
	    		}
	    		for (int i=0; i<enemies.size(); i++) {
	    			enemies.get(i).draw();
	    			enemies.get(i).move();
	    			if (enemies.get(i) instanceof Ghost && PongDoge.intersects((int)enemies.get(i).getX(), (int)enemies.get(i).getX()-16, (int)enemies.get(i).getY(), (int)enemies.get(i).getY()+16)
	    				&& Keyboard.isKeyDown(Keyboard.KEY_SPACE) && enemies.get(i).isAllyOfDoge == false) {
	    				enemies.get(i).setReversed(true);
	    				pongdoge.setScore(pongdoge.getScore()-1);
	    			}
	    			else if (enemies.get(i) instanceof Footsoldier && PongDoge.intersects((int)enemies.get(i).getX(), (int)enemies.get(i).getX()-32, (int)enemies.get(i).getY(), (int)enemies.get(i).getY()+32)
	    					&& Keyboard.isKeyDown(Keyboard.KEY_SPACE) && enemies.get(i).isAllyOfDoge == false) {
	    				enemies.get(i).setReversed(true);
	    				pongdoge.setScore(pongdoge.getScore()-1);
	    			}
	    			pongdoge = enemies.get(i).attack(pongdoge);
	    			if (enemies.get(i).getX() > width || enemies.get(i).getY() > height || enemies.get(i).getX() < 0 || enemies.get(i).getY() < 0) {
	    				if (enemies.get(i).getX() < 0) {
	    					if (enemies.get(i) instanceof Troll) {
	    						this.state = "Game Over";
	    					}
	    					for (int j=0; j<50; j++) {
	    						pongdoge.decrementLife();
	    					}
	    				}
	    				else if (enemies.get(i).getX() > width) {
	    					pongdoge.incrementScore();
	    				}
	    				else if (enemies.get(i).getY() > height && enemies.get(i) instanceof Wolf) {
	    					for (int j=0; j<50; j++) {
	    						pongdoge.decrementLife();
	    					}
	    				}
	    				enemies.remove(enemies.get(i));
	    				i--; //to offset ArrayList removal index screw-upping
	    			}
	    		}
	    		font.drawString(100.0f, 50.0f, "HP: "+pongdoge.getLife());
	    		font.drawString(380.0f, 50.0f, "Score: "+pongdoge.getScore());
	    		if (pongdoge.getLife() <= 0 || pongdoge.getScore() < 0) {
	    			state = "Game Over";
	    		}
	    	}
	    	
	    	else if (state.equals("Game Over")) {
	    		font.drawString(100.0f, 50.0f, "Game Over");
	    		font.drawString(380.0f, 50.0f, "Score: "+pongdoge.getScore());
	    		if (Mouse.isButtonDown(1)) {
	    			state = "Main Menu";
	    		}
	    	}
	    	
	    	Display.update();
	    	Display.sync(60);
	    }
	    AL.destroy();
	    Display.destroy();
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
	
	public void initializeSinglePlayerStage() throws UnsupportedAudioFileException, IOException {
		levels = AutoInitialization.initiliaze(levels);
	}
	
	public static Texture loadTexture(String name, String type){
		try {
			return TextureLoader.getTexture(type, new FileInputStream(new File("C:\\Users\\Wenfeng\\Desktop\\eclipse\\Java\\Doge Adventures\\src\\res\\"+name+"."+type)));
		} catch (FileNotFoundException e) {
			System.out.println("A");
		} catch (IOException e) {
			System.out.println("B");
		}
		return null;
	 }
	
	public static void renderAPicture(Texture t, int x1, int x2, int y1, int y2) {
		t.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2i(x1, y1);
		glTexCoord2f(1,0);
		glVertex2i(x2, y1);
		glTexCoord2f(1,1);
		glVertex2i(x2, y2);
		glTexCoord2f(0,1);
		glVertex2i(x1, y2);
		glEnd();
	}
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		Main m = new Main();
		m.run();
	}
	
	//for (int i=0; i<list.size(); i+=2) {
		//list.add(i+1, list.get(i)*list.get(i));
	//}
	
	//for (int i=0; i<list.size(); i++) {
		//if (list.get(i) % 2 == 0) {
			//list.remove(i);
			//i--;
		//}
	//}
}
