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
	
	public void init()//��ʼ��
	{
		runspeed = 3;
		dropspeed = 2;
		x = 135;
		y = 180;
		health = 12;
		direction = 0;
	}
	
	public void drawImg(Graphics g)//���������Ѫ��
	{
		g.drawImage(Man, x, y, null);
		g.setFont(new Font("����",Font.BOLD,20));
		g.setColor(Color.YELLOW);//����������ɫ
		g.drawString("��������ֵ",20,25);
		Color c=g.getColor();
		g.setColor(Color.RED);
		g.draw3DRect(20, 35, 12*9, 10,false);//��ʾѪ��
		g.fill3DRect(20, 35, health*9, 10, true);
		g.setColor(c);
	}
	
	public void drop(boolean played)//�������亯��
	{
		if(!played) return;
		y += dropspeed;
	}
	
	public void setRunSpeed(int speed)//�����ƶ��ٶ�
	{
		runspeed = speed;
	}
	
	public void moveleft(boolean played)//�����ƶ�
	{
		if(!played) return;
		x -= runspeed;
		if(x < 10) x = 10;
	}
	
	public void moveright(boolean played)//�����ƶ�
	{
		if(!played) return;
		x += runspeed;
		if(x > 260) x = 260;
	}
}
