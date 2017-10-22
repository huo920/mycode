package com.huo.shoot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ShootGame extends JPanel{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 654;
	
	public int score;
	public int state;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAMEOVER = 3;
	
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage bee;
	public static BufferedImage airplane;
	public static BufferedImage gameover;
	public static BufferedImage bullet;
	static{
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
	private Collection<FlyingObject> flyings = new ArrayList<FlyingObject>();
	private Collection<Bullet> bullets = new ArrayList<Bullet>();
	
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, null);
		paintHero(g);
		paintFlyings(g);
		paintBullets(g);
		paintState(g);
		paintScoreAndLife(g);
	}
	
	public void paintScoreAndLife(Graphics g){
		g.setColor(Color.BLACK);
		g.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,20));
		g.drawString("SCORE:"+score, 14, 30);
		g.drawString("LIFE:"+hero.getLife(), 14, 55);
		
	}
	
	public void paintState(Graphics g){
		switch (state) {
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAMEOVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	
	public void paintHero(Graphics g){
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	public void paintFlyings(Graphics g){
		for(FlyingObject f : flyings){
		
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
	public void paintBullets(Graphics g){
		for(Bullet b : bullets){
		
			g.drawImage(b.image, b.x, b.y, null);
		}
	}
	
	public FlyingObject nextOne(){
		Random ran = new Random();
		int type = ran.nextInt(20);
		if(type<4){
			return new Bee();
		}else{
			return new Airplane();
		}
	}
	
	
	private int enterIndex = 0;
	public void enterAction(){
		enterIndex++;
		if(enterIndex % 40 == 0){
			flyings.add(nextOne());
		}
	}
	
	public void enemyStep(){
		hero.step();
		for (FlyingObject f:flyings) {
			f.step();
		}
		for (Bullet b : bullets) {
			b.step();
		}
	}
	
	private int bulletIndex = 0;
	public void bulletAction(){
		bulletIndex++;
		if(bulletIndex % 30 == 0){
			Collection<Bullet> bts = Arrays.asList(hero.shoot());
			bullets.addAll(bts);
		}
	}
	
	public void delOutOfBounds(){
		int index = 0;
		Collection<FlyingObject> flyingLives = new ArrayList<FlyingObject>(flyings.size());
		for (FlyingObject f: flyings) {
			if(!f.outOfBounds()){
				flyingLives.add(f);
			}
		}
		flyings =flyingLives;
		
	
		Collection<Bullet> bulletLives = new ArrayList<Bullet>(bullets.size());
		for ( Bullet b :bullets) {
		
			if(!b.outOfBounds()){
				bulletLives.add(b);
				
			}
		}
		bullets = bulletLives;
	}
	
	public void boom(){
		for (Bullet bb: bullets) {
		
				Iterator<FlyingObject> it = flyings.iterator();
				while(it.hasNext()){
				FlyingObject	f =  it.next();
					if(f.bang(bb)){
						it.remove();
						if(f instanceof Bee){
							Bee b = (Bee) f;
							switch (b.getType()) {
							case Award.DOUBLE_FIRE:
								hero.addDoubleFire();
								break;
							case Award.LIFE:
								hero.addLife();
								break;
							}
						}else if(f instanceof Airplane){
							Airplane a = (Airplane) f;
							score += a.getScore(); 
						}
					}
			}
		}
		
		Iterator<FlyingObject> it = flyings.iterator();
				while(it.hasNext()){
					FlyingObject f = it.next();
				if(f.bang(hero)){
					it.remove();
				
					if(hero.getLife() <= 0){
						score = 0;
						hero = new Hero();
						flyings.clear();
						bullets.clear();
						state = GAMEOVER;
					}else {
						hero.subLife();
						hero.clearDoubleFire();
					
				}
			}
		}
		
		
	}
	
	public void start(){
		MouseAdapter l = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAMEOVER:
					state = START;
					break;
				}
			}
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE){
					state = RUNNING;
				}
			}
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING){
					state = PAUSE;
				}
			}
			public void mouseMoved(MouseEvent e) {
				if(state == RUNNING){
				hero.moveTo(e.getX(), e.getY());
				}
			}
			
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		Timer timer = new Timer();
		int inserval = 10;
		timer.schedule(new TimerTask(){
			public void run() {
				if (state == RUNNING) {
					enterAction();
					enemyStep();
					bulletAction();
					delOutOfBounds();
					boom();
				}
				repaint();
			}
			
		},inserval,inserval);
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
		game.start();
	}
}
