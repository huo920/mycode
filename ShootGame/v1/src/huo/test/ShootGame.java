package huo.test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class ShootGame extends JPanel{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 654;
	
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage bee;
	public static BufferedImage airplane;
	public static BufferedImage gameover;
	public static BufferedImage bullet;
	
	static {
		try {
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Hero hero = new Hero();
	private FlyingObject[] flyings = {};
	private Bullet[] bullets = {};
	
	ShootGame(){
		flyings = new FlyingObject[2];
		flyings[0] = new Bee();
		flyings[1] = new Airplane();
		bullets = new Bullet[1];
		bullets[0] = new Bullet(150,220);
	}
	
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, null);
		paintHero(g);
		paintFlyings(g);
		paintBullets(g);
	}
	
	public void paintHero(Graphics g){
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	
	public void paintFlyings(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
	
	public void paintBullets(Graphics g){
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image, b.x, b.y, null);
		}
	}
	
	public static void main(String[] args) {
		JFrame jf = new JFrame("fly");
		ShootGame game = new ShootGame();
		jf.add(game);
		jf.setSize(WIDTH,HEIGHT);
		jf.setAlwaysOnTop(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		
	}

}
