package com.huo.shoot;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected BufferedImage image;
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	public abstract void step();
	public abstract boolean outOfBounds();

	public boolean bang(FlyingObject f) {
		int x = this.x + this.width / 2;
		int y = this.y + this.height / 2;
		int x1 = f.x - this.width / 2;
		int x2 = f.x + f.width + this.width / 2;
		int y1 = f.y - this.height / 2;
		int y2 = f.y + f.height + this.height / 2;
		return x > x1 && x < x2 && y > y1 && y < y2;
	}

}
