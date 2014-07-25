package doge;

public interface EntityInterface {
	public void draw();
	public double getX();
	public double getY();
	public boolean intersects(PongDoge other);
	public PongDoge attack(PongDoge doge);
	public void move();
}
