package Item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import Start.GameStart;

public class man {

	public int x;
	public int y;
	public int health ;
	public int runspeed ;
	public int dropspeed ;
	public int direction ;
	private static final Image Man = new ImageIcon(GameStart.class.getResource("/IMG/man.png")).getImage();	
	
	public void init()//初始化
	{
		runspeed = 3;
		dropspeed = 2;
		x = 135;
		y = 180;
		health = 12;
		direction = 0;
	}
	
	public void drawImg(Graphics g)//画出人物和血量
	{
		g.drawImage(Man, x, y, null);
		g.setFont(new Font("楷体",Font.BOLD,20));
		g.setColor(Color.YELLOW);//设置字体颜色
		g.drawString("您的生命值",20,25);
		Color c=g.getColor();
		g.setColor(Color.RED);
		g.draw3DRect(20, 35, 12*9, 10,false);//显示血槽
		g.fill3DRect(20, 35, health*9, 10, true);
		g.setColor(c);
	}
	
	public void drop(boolean played)//人物下落函数
	{
		if(!played) return;
		y += dropspeed;
	}
	
	public void setRunSpeed(int speed)//人物移动速度
	{
		runspeed = speed;
	}
	
	public void moveleft(boolean played)//向左移动
	{
		if(!played) return;
		x -= runspeed;
		if(x < 10) x = 10;
	}
	
	public void moveright(boolean played)//向右移动
	{
		if(!played) return;
		x += runspeed;
		if(x > 260) x = 260;
	}
}
