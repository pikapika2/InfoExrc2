package src;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/*
 * Class for the craft (the player) that extends Sprite
 * Contains a list of missiles
 * Its coordinate are managed by x and y
 * The craft can only move up or down using the arrow keys
 */
public class Craft extends Sprite {

	private final int DY = 10; // 縦の移動速度
	private final int DX = 10; // 横の移動速度
	
	private final int BURST_LIMIT = 10;  // 何フレーム毎に撃てるか
	private int burstCount;
	
	private ArrayList<Integer> pressedKeys = new ArrayList<Integer>(); // 入力されたキーのリスト

	private static Craft craft;

	private ArrayList<Missile> missiles; //list of visible missiles

	private boolean immune = false; //boolean for the immunity state, depending of the immune bonus

	private int missilestate; //defines the kind of missile the player is using

	public Craft(int x, int y){
		super(x,y);
		initCraft();
	}

	private void initCraft(){

		missiles = new ArrayList<>();
		loadImage("/" + Frame.getTheme() + "_red.png");
		getImageDimensions();

		missilestate = 0;

		Craft.craft = this;

	}
	/*
	 * Increment of the y coordinate of the craft by the number given by keyPressed()
	 * The craft is limited by the upper and lower bounds of the screen (20 and 264)
	 */
	public void move(){
		if (pressedKeys.contains(KeyEvent.VK_UP)) { y -= DY; }
		if (pressedKeys.contains(KeyEvent.VK_DOWN)) { y += DY; }
		if (y < 20) { y = 20; }
		if (y > 580) { y = 580; }
		
		if (pressedKeys.contains(KeyEvent.VK_LEFT)) { x -= DX; }
		if (pressedKeys.contains(KeyEvent.VK_RIGHT)) { x += DX; }
		if (x < 0) { x = 0; }
		if (x > 450) { x = 450; }
		
		if (burstCount > 0) {
			burstCount--;
			return;
		}
		if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
			fire();
			burstCount = BURST_LIMIT;
		}
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getMissiles(){

		return missiles;

	}

	/*
	 * While the key is pressed, increments or decrements the y coordinate of the craft
	 */
	public void keyPressed(KeyEvent e) {
		Integer key = e.getKeyCode();
		if (pressedKeys.contains(key)) { return; } 
		pressedKeys.add(key);
	}

	/*
	 * The fire method is only called on the release of the space key to avoid a flooding of missiles
	 */
	public void keyReleased(KeyEvent e) {
		Integer key = e.getKeyCode();
		pressedKeys.remove(key);
	}
	/*
	 * Add missiles to the ArrayList of missiles when the spacebar is released
	 * There is 7 different states for the missiles
	 * A switch is used to define the kind and the number of missiles to launch
	 */
	public void fire() {

		switch (missilestate) {
		case 0: //the player can't shoot missiles
			break;
			//TODO
			//ne pas faire apparaitre des demis missiles 
		case 1: //the player can only shoot 5 missiles
			if (missiles.size() < 5) { missiles.add(new Missile(x, y - height)); }
			break;

		case 2: //infinite missiles on a single row
			missiles.add(new Missile(x, y - height));
			break;

		case 3: //missiles on two rows
			if (x < 450) { missiles.add(new Missile(x + 64, y - height)); }
			missiles.add(new Missile(x, y - height));
			break;

		case 4: //missiles on three rows
			if (x < 450) { missiles.add(new Missile(x + 64, y - height)); }
			missiles.add(new Missile(x, y - height));
			missiles.add(new Missile(x - 64, y - height));
			break;

		case 5: //missiles on five rows
			missiles.add(new Missile(x - 128, y - height));
			missiles.add(new Missile(x - 64, y - height));
			missiles.add(new Missile(x, y - height));
			missiles.add(new Missile(x + 64, y - height));
			missiles.add(new Missile(x + 128, y - height));
			break;

		case 20: //piercing missiles on one row
			missiles.add(new Missile(x, y - height));
			break;
		}
	}

	/*
	 * Increment the missilestate : the craft get a better kind of missile
	 */
	public void upShoot(){
		this.missilestate++;
	}

	/*
	 * Decrement the missile state when the player is hit by an enemy
	 * The craft get a worse kind of missile
	 *
	 */
	public void downShoot(){
		this.missilestate--;
		if(this.missilestate < 0)
			missilestate = 0;
	}

	public int getShoot(){
		return this.missilestate;
	}

	public void setShoot(int upgrade){
		this.missilestate = upgrade;
	}

	public static Craft getCraft(){
		return Craft.craft;
	}

	public boolean isImmune(){
		return immune;
	}

	public void setImmune(boolean immune){
		this.immune = immune;
	}

}
