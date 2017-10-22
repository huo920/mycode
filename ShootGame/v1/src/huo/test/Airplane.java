package huo.test;

import java.util.Random;

public class Airplane extends FlyingObject implements Enemy{
	private int speed = 2;
	
	public Airplane(){
		image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		Random ran = new Random();
		x = ran.nextInt(ShootGame.WIDTH - this.width );
		y = -this.height;
	}
	public int getScore(){
		return 5;
	}
	

}
