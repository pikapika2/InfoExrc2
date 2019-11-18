package src;

/*
 * This is the class for the enemy aliens, which extends the Sprite class.
 * The x and y coordinates define the position of a specific alien on the board
 */
public class Alien extends Sprite{

	private static int speed; //speed of the alien, in pixels per frame
	private int mirr = 5;//2019_1105 ネタです。
	public Alien(int x, int y){
		super(x,y);
		initAlien();
	}

	private void initAlien(){

		loadImage("/" + Frame.getTheme() + "_alien.png");
		getImageDimensions();

		loadSoundName("magicarpe.wav");
	}
	/*
	 * Increment of the x coordinate of aliens for going from right to left
	 * when an alien touches the left bound of the screen, it is no more visible
	 */
	public void move(){

		//if(x+width<0)
			//vis = false;

		if(y-800 > height)
			vis = false;//2019_1104

//敵が跳ね返るようになる
		/*
			x += mirr;
		if (x > 430 || x < 0)
			mirr *= -1;
			//x -= speed;
*/
		y += speed;//2019_11_04

	}

	public static int getSpeed() {
		return speed;
	}

	public static void setSpeed(int speed) {
		Alien.speed = speed;
	}

}
