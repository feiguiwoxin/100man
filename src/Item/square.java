package Item;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import Start.GameStart;

public class square {
	
	public int x;
	public int y;
	public int type;
	public int destroytime = 0;
	public static int dropspeed = 1;
	public static final int width = 70;
	public static final int height = 16;
	public static final Image Square = new ImageIcon(GameStart.class.getResource("/IMG/Square.png")).getImage();
	private boolean touched = false;
	
	public square(int x,int y,int type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public static void init()
	{
		dropspeed = 1;
	}
	
	public int gettouched()
	{
		if(touched) return 1;
		return 0;
	}
	
	public void settouched(int stat)
	{
		if(stat == 1)
		{
			touched = true;
		}
		else
		{
			touched = false;
		}
	}	
	
	public int move(boolean played)
	{
		if(!played) return 0;
		
		if(touched && type == 4)
		{
			destroytime ++;
			if(destroytime >= 30) y=0;
		}
	
		y -= dropspeed;
		if(y < 60) return -1;
		return 0;
	}
	
	public boolean fristTouch()
	{
		if(touched)
		{
			return false;
		}
		
		if(type != 1 && type != 0)
		{
			touched = true;
		}
		
		return true;
	}
	
	public void drawImg(Graphics g)
	{
		if(y+height > 420) return;
		g.drawImage(Square, x, y, x+width, y+height,				
					0, type*height, width, (type+1)*height,null);
		return;
	}
}
