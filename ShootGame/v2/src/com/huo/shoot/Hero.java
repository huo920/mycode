package com.huo.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Hero extends FlyingObject {
	private int doubleFire;
	private int life;
	private BufferedImage[] images;
	private int index;
	public Hero(){
		image = ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
		doubleFire = 0;
		life = 3;
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
		index = 0;
	}
	public void step() {
		
		image = images[index++/10%images.length];
		
	}
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire>0){
			Bullet[] bts = new Bullet[2];
			bts[0] = new Bullet(this.x + xStep,this.y - yStep);
			bts[1] = new Bullet(this.x + 3*xStep,this.y - yStep);
			doubleFire -= 2;
			return bts;
			
		}else{
			Bullet[] bts = new Bullet[1];
			bts[0] = new Bullet(this.x + 2*xStep,this.y - yStep);
			return bts;
		}
		
	}
	@Override
	public boolean outOfBounds() {
		return false;
	}
	
	public void moveTo(int x,int y){
		this.x = x - this.width/2;
		this.y = y - this.height /2;
	}
	public void addDoubleFire() {
		doubleFire += 40;
	}
	
	public void clearDoubleFire() {
		doubleFire = 0;
	}
	
	public void addLife() {
		life++;
	}
	
	public void subLife() {
		life--;
	}
	
	public int getLife() {
		return life;
	}

}
