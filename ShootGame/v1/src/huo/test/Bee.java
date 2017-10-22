package huo.test;

import java.util.Random;

public class Bee extends FlyingObject implements Award {
	private int xSpeed = 1;
	private int ySpeed = 2;
	private int awardType;
	
	public Bee(){
		image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
		Random ran = new Random();
		x = ran.nextInt(ShootGame.WIDTH - this.width );
		y = -this.height;
		awardType = ran.nextInt(2);
	}
	public int getType(){
		return awardType;
	}

}
