package Item;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import Start.GameStart;

public class man {

	public int x;
	public int y;
	public int health = 2;
	public int runspeed = 3;
	public int dropspeed = 2;
	public int direction = 0;
	private static final Image Man = new ImageIcon(GameStart.class.getResource("/IMG/man.png")).getImage();	
	
	public void init()
	{
		runspeed = 3;
		dropspeed = 2;
		x = 135;
		y = 120;
		health = 2;
		direction = 0;
	}
	
	public void drawImg(Graphics g)
	{
		g.drawImage(Man, x, y, null);
	}
	
	public void drop(boolean played)
	{
		if(!played) return;
		y += dropspeed;
	}
	
	public void setRunSpeed(int speed)
	{
		runspeed = speed;
	}
	
	public void moveleft(boolean played)
	{
		if(!played) return;
		x -= runspeed;
		if(x < 10) x = 10;
	}
	
	public void moveright(boolean played)
	{
		if(!played) return;
		x += runspeed;
		if(x > 260) x = 260;
	}
}
