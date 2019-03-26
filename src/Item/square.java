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
	private boolean touched2 = false;
	
	public square(int x,int y,int type)//���캯��
	{
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public static void init()//��ʼ�������ٶ�
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
		
		if(touched && type == 2)//ͣ��ʱ��destroytime==5ʱ��ʧ
		{
			destroytime ++;
			if(destroytime >= 5) y=0;
		}
	
		y -= dropspeed;
		if(y < 90) return -1;//��Ŵ�������y==90ʱ��ʧ
		return 0;
	}
	
	public boolean firstTouch()//��һ����������
	{
		if(touched)
			return false;
		
		if(type == 0 || type == 2)//0Ϊ���ӣ�2Ϊ����
			touched = true;
		
		return true;
	}
	
	public boolean firstblood()//û��������
	{
		if(touched2)
			return false;
		if(type != 0)
			touched2 = true;
		else 
			return false;
		return true;
	}
	
	public void drawImg(Graphics g)
	{
		if(y+height > 460) return;//���ӵ�������С��460ʱ����ʾ����
		g.drawImage(Square, x, y, x+width, y+height,				
					0, type*height, width, (type+1)*height,null);
		return;
	}
}
