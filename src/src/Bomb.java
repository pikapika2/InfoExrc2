package src;

public class Bomb extends Sprite {

	private static int speed;
	
	public Bomb(int x, int y) {
		super(x,y);
		initLife();
	}
	
	public void initLife() {
		loadImage("/bomb.png");
		getImageDimensions();
	}
	public static void explode() {
		Sound.play("explode.wav");
	}
	
	public void move() {
		
		if (x + width < 0) { vis = false; }
		x -= speed;
	}

	public static int getSpeed() {
		return speed;
	}

	public static void setSpeed(int speed) {
		Bomb.speed = speed;
	}
}
