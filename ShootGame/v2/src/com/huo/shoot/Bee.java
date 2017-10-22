package com.huo.shoot;

import java.util.Random;

public class Bee extends FlyingObject implements Award{
	private int xSpeed = 2;
	private int ySpeed = 1;
	private int awardType;
	public Bee(){
		image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
		Random ran = new Random();
		x = ran.nextInt(ShootGame.WIDTH - this.width);
		y = -this.height;
	}
	public int getType(){
		return awardType;
	}
	@Override
	public void step() {
		x += xSpeed;
		y += ySpeed;
		if(x>ShootGame.WIDTH - this.width){
			xSpeed = -1;
		}
		if(x<=0){
			xSpeed = 1;
		}
	}
	public boolean outOfBounds() {
		return y>=ShootGame.HEIGHT;
	}

}
