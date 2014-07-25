package doge;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

public class PongDoge implements EntityInterface {//still haven't added pong reflect ability
	
	private Texture t = Main.loadTexture("doge","png");
	public static PongDoge leDoge;
	private int life = 1000; //supposed to be 1000 initially
	public int maxLife = 1000; //can be increased
	private int score = 0;
	private int x, y = 0;
	private dogeProjectile projectile = null;
	public int enemiesKilled;
	private int direction;
	public boolean hasAttainedShikai;
	public boolean hasAttainedPankai;
	private dogeSword sword;
	private Texture swordTexture = Main.loadTexture("actual sword", "png");
	private boolean hasKey;
	
	public ArrayList<Enemy> attack(ArrayList<Enemy> enemies) {
		if (Mouse.isButtonDown(0)) {
			sword = new dogeSword(this.x+64, this.y, "normal", direction);
			sword.render();
		}
		else {
			sword = null;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_R) && hasAttainedShikai) {
			if (projectile == null) {
				projectile = new dogeProjectile(0, this.x+50, this.y+15, this.direction);
			}
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_F) && hasAttainedPankai) {
			if (projectile == null) {
				projectile = new dogeProjectile(1, this.x-25, this.y-50, this.direction);
			}
		}
		if (projectile != null) {
			projectile.move();
			projectile.render();
			if (projectile.x > projectile.originalx + 650 || projectile.x < projectile.originalx - 650 ||
				projectile.y > projectile.originaly + 650 || projectile.y < projectile.originaly - 650) {
				projectile = null;
			}
		}
		for (int i=0; i<enemies.size(); i++) {
			if (enemies.get(i) != null) {
				if (sword != null && ((enemies.get(i).intersects(sword.x-128, sword.x-64, sword.y-128, sword.y) && direction == Keyboard.KEY_W) ||
						(enemies.get(i).intersects(sword.x-128, sword.x-64, sword.y, sword.y+128) && direction == Keyboard.KEY_S) ||
						(enemies.get(i).intersects(sword.x-192, sword.x-64, sword.y, sword.y+64) && direction == Keyboard.KEY_A) ||
						(enemies.get(i).intersects(sword.x, sword.x+128, sword.y, sword.y+64) && direction == Keyboard.KEY_D))) {
					enemies.get(i).hp -= 1;
					if (enemies.get(i).hp <= 0) {
						this.life+=3;
						enemies.remove(i);
						this.incrementScore();
						i--;
					}
					sword = null;
				}
				if (projectile != null) {
					if (projectile.type == 0) {
						if (enemies.get(i).intersects(projectile.x-32, projectile.x, projectile.y, projectile.y+32)) {
							enemies.get(i).hp -= 2;
							if (enemies.get(i).hp <= 0) {
								enemies.remove(i);
								i--;
							}
							projectile = null;
							this.incrementScore();
							this.life+=3;
						}
					}
					else {
						if (enemies.get(i).intersects(projectile.x, projectile.x, projectile.y+50, projectile.y+150)) {
							enemies.get(i).hp -= 5;
							if (enemies.get(i).hp <= 0) {
								enemies.remove(i);
								i--;
							}
							i--;
							this.incrementScore();
							this.life+=3;
						}
					}
				}
			}
		}
		leDoge = this;
		return enemies;
	}
	
	public Key attack(Key key) {
		if (sword != null && key != null && ((key.intersects(sword.x-128, sword.x-64, sword.y-128, sword.y) && direction == Keyboard.KEY_W) ||
			(key.intersects(sword.x-128, sword.x-64, sword.y, sword.y+128) && direction == Keyboard.KEY_S) ||
			(key.intersects(sword.x-192, sword.x-64, sword.y, sword.y+64) && direction == Keyboard.KEY_A) ||
			(key.intersects(sword.x, sword.x+128, sword.y, sword.y+64) && direction == Keyboard.KEY_D))) {
			sword = null;
			key.isVisible = true;
		}
		return key;
	}
	
	private class dogeSword {
		int x, y, direction;
		String type;
		public dogeSword(int x, int y, String type, int direction) {
			this.direction = direction;
			this.x = x;
			this.y = y;
			this.type = type;
		}
		public void render() {
			if (type.equals("normal")) {
				if (direction == Keyboard.KEY_W) {
					swordTexture.bind();
					glBegin(GL_QUADS);
					glTexCoord2f(0,1);
					glVertex2i(x-64, y);
					glTexCoord2f(0,0);
					glVertex2i(x, y);
					glTexCoord2f(1,0);			
					glVertex2i(x, y-128);
					glTexCoord2f(1,1);
					glVertex2i(x-64, y-128);
					glEnd();
				}
				else if (direction == Keyboard.KEY_A) Main.renderAPicture(swordTexture, x-128, x-256, y, y+64);
				else if (direction == Keyboard.KEY_S) {
					swordTexture.bind();
					glBegin(GL_QUADS);
					glTexCoord2f(0,1);//0,1	
					glVertex2i(x-128, y);
					glTexCoord2f(0,0);//0,0
					glVertex2i(x-64, y);
					glTexCoord2f(1,0);//1,0
					glVertex2i(x-64, y+128);			
					glTexCoord2f(1,1);//1,1
					glVertex2i(x-128, y+128);
					glEnd();
				}
				else if (direction == Keyboard.KEY_D) Main.renderAPicture(swordTexture, x, x+128, y, y+64);
			}
		}
	}
	
	private class dogeProjectile {
		int direction;
		int type;
		Texture attack;
		int x, y, originalx, originaly = 0;
		public dogeProjectile(int type, int x, int y, int direction) {
			this.direction = direction;
			this.type = type;
			this.x = x;
			this.y = y;
			this.originalx = x;
			this.originaly = y;
			if (type == 0) {
				attack = Main.loadTexture("PacMan", "gif");
			}
			else if (type == 1) {
				attack = Main.loadTexture("AugGetsuga", "png");
			}
		}
		public void move() {
			if (type == 0) {
				if (direction == Keyboard.KEY_W) {
					y-=8;
				}
				else if (direction == Keyboard.KEY_A) {
					x-=8;
				}
				else if (direction == Keyboard.KEY_S) {
					y+=8;
				}
				else if (direction == Keyboard.KEY_D) {
					x+=8;
				}
			}
			else {
				if (direction == Keyboard.KEY_W) {
					y-=4;
				}
				else if (direction == Keyboard.KEY_A) {
					x-=4;
				}
				else if (direction == Keyboard.KEY_S) {
					y+=4;
				}
				else if (direction == Keyboard.KEY_D) {
					x+=4;
				}
			}
		}
		public void render() {
			attack.bind();
			if (type == 0) {
				if (direction == Keyboard.KEY_W) {
					Main.renderAPicture(attack, x, x+32, y, y-32);
				}
				else if (direction == Keyboard.KEY_A) {
					Main.renderAPicture(attack, x, x-32, y, y+32);
				}
				else if (direction == Keyboard.KEY_S) {
					Main.renderAPicture(attack, x, x-32, y, y-32);
				}
				else if (direction == Keyboard.KEY_D) {
					Main.renderAPicture(attack, x, x+32, y, y+32);
				}
			}
			else {
				if (direction == Keyboard.KEY_W) {
					attack.bind();
					glBegin(GL_QUADS);
					glTexCoord2f(0,1);
					glVertex2i(x-150, y+250);
					glTexCoord2f(0,0);
					glVertex2i(x+150, y+250);
					glTexCoord2f(1,0);			
					glVertex2i(x+150, y-250);
					glTexCoord2f(1,1);
					glVertex2i(x-150, y-250);
					glEnd();
				}
				else if (direction == Keyboard.KEY_A) {
					Main.renderAPicture(attack, x, x-500, y, y+300);
				}
				else if (direction == Keyboard.KEY_S) {
					attack.bind();
					glBegin(GL_QUADS);
					glTexCoord2f(1,0);	
					glVertex2i(x-100, y+400);
					glTexCoord2f(1,1);
					glVertex2i(x+200, y+400);
					glTexCoord2f(0,1);
					glVertex2i(x+200, y-100);			
					glTexCoord2f(0,0);
					glVertex2i(x-100, y-100);
					glEnd();
				}
				else if (direction == Keyboard.KEY_D) {
					Main.renderAPicture(attack, x, x+500, y, y+300);
				}
			}
		}
	}
	
	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			y-=6;
			this.setDirection(Keyboard.KEY_W);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			x-=6;
			this.setDirection(Keyboard.KEY_A);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			y+=6;
			this.setDirection(Keyboard.KEY_S);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			x+=6;
			this.setDirection(Keyboard.KEY_D);
		}
		leDoge = this;
	}
	
	public void incrementScore() {
		this.score++;
		leDoge = this;
	}
	
	public void decrementLife() {
		this.life--;
		leDoge = this;
	}
	
	@Override
	public void draw() {
		t.bind();
		if (direction == Keyboard.KEY_A) {
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2i(x, y);
			glTexCoord2f(1,0);
			glVertex2i(x-60, y);
			glTexCoord2f(1,1);
			glVertex2i(x-60, y+64);
			glTexCoord2f(0,1);
			glVertex2i(x, y+64);
			glEnd();
		}
		else if (direction == Keyboard.KEY_S) {
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2i(x, y);
			glTexCoord2f(1,0);
			glVertex2i(x-60, y);
			glTexCoord2f(1,1);
			glVertex2i(x-60, y-64);
			glTexCoord2f(0,1);
			glVertex2i(x, y-64);
			glEnd();
		}
		else {
			t.bind();
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
		leDoge = this;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public boolean intersects(PongDoge other) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public static boolean intersects(int x1, int x2, int y1, int y2) {
		return !(y2<leDoge.getY()) && !(x1>leDoge.getX()+60) && !(y1>leDoge.getY()+64) && !(x2<leDoge.getX());
	}
	
	public PongDoge attack(PongDoge doge) {
		return null;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int i) {
		this.score = i;
	}

	public void setX(int i) {
		x = i;
	}
	
	public void setY(int i) {
		y = i;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isHasKey() {
		return hasKey;
	}

	public void setHasKey(boolean hasKey) {
		this.hasKey = hasKey;
	}
}
