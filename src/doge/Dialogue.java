package doge;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

public class Dialogue implements EntityInterface {

	private TrueTypeFont font = new TrueTypeFont(new Font("Comic Sans MS", Font.BOLD, 12), true);
	private String text;
	private int x, y;
	
	public Dialogue() {
		x = 0;
		y = 0;
		text = "";
	}
	
	public Dialogue(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	public void draw() {
		font.drawString(x, y, text);
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public boolean intersects(PongDoge other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PongDoge attack(PongDoge doge) {
		// TODO Auto-generated method stub
		return doge;
	}

	@Override
	public void move() {
		
	}
}
