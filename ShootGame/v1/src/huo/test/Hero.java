package huo.test;

import java.awt.image.BufferedImage;


public class Hero extends FlyingObject {
	private int doubleFire ;
	private int life;
	private BufferedImage[] images;
	
	public Hero(){
		image = ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		doubleFire = 0;
		life = 3;
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
		x = 150;
		y = 400;
	}

}
