package huo.test;

import java.util.Random;

public class Bullet extends FlyingObject {
	private int speed = 3;
	
	public Bullet(int x,int y){
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		Random ran = new Random();
		this.x = x;
		this.y = y;
	}

}
