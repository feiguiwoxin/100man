package UI;

import java.awt.Graphics;
import java.util.ArrayList;

import Item.man;
import Item.square;

public class LayerScreen {
	private ArrayList<square> squares = new ArrayList<square>();
	private man player = new man();
	public boolean GameStat = false;//��Ϸ״̬
	
	LayerScreen()
	{
		init();
	}
	
	public man getPlayer()
	{
		return player;
	}
	
	public ArrayList<square> getSquares()
	{
		return squares;
	}
	
	public void init()//��ʼ��
	{
		square.init();
		player.init();
		
		squares.add(new square(115, 210, 1));//��ʼ������һ������
		for(int i = 300; i < 420; i = i + 70)
		{
			CreatSquare(i);
		}
	}
	
	public void drawImg(Graphics g)
	{
		paintSquare(g);
		paintPlayer(g);
	}	
	
	//1/6����Ϊ0��5/12����Ϊ1��5/12����Ϊ2
	private int NumofSquare()//ÿһ����ӵ�����
	{
		int num = (int)(12*Math.random());
		if(num == 0)
		{
			return 0;
		}
		else if(num <= 5)
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}
	
	private int TypeofSquare()//��������������ͣ��������Ӹ���Ϊ1/3����������Ϊ2/15��0Ϊ���ӣ�1Ϊ��ͨ�壬2Ϊ���壬3Ϊ���ɣ�4��5Ϊ���ʹ�
	{
		int type = (int)(15*Math.random());
		if(type<5)
			return 0;
		else if(type<7)
			return 1;
		else if(type<9)
			return 2;
		else if(type<11)
			return 3;
		else if(type<13)
			return 4;
		else 
			return 5;
	}
	
	private int PosofSquare(int num)
	{
		if(num == 1)
		{
			return 15 + (int)(185*Math.random());
		}
		else
		{
			return 15 + (int)(75*Math.random());
		}
	}
	
	public void CreatSquare(int yp)
	{
		int squarenum = NumofSquare();//ÿ�м�������
		if(squarenum == 0) return;
		
		int pos = PosofSquare(squarenum);//���ӵ�λ��
		for(int i = 0; i < squarenum; i++)
		{
			int type = TypeofSquare();
			squares.add(new square(pos + i * 110, yp, type));
		}	
	}
	
	private void paintSquare(Graphics g)//��������
	{
		for(int i = squares.size() - 1; i >= 0; i--)
		{
			square s = squares.get(i);
			if(s.move(GameStat) == -1)//����������һ���߶Ⱥ���ʧ
			{
				squares.remove(i);
				continue;
			}
			
			s.drawImg(g);
		}
	}
	
	private void paintPlayer(Graphics g)//��������
	{
		if (player == null) return;
		square s = null;
		
		player.drop(GameStat);
		if((s = touched()) != null)
		{
			player.y = s.y - 30;
			whenTouch(s);
		}
		
		if(player.direction == 1)
		{
			player.moveleft(GameStat);
			if(touched() != null) //����ײ������ʱ���ܼ��������ƶ�
			{
				player.moveright(GameStat);
			}
		}
		else if(player.direction == 2)
		{
			player.moveright(GameStat);
			if(touched() != null) //����ײ������ʱ���ܼ��������ƶ�
			{
				player.moveleft(GameStat);
			}
		}
		
		player.setRunSpeed(4);
		
		player.drawImg(g);
	}
	
	private void whenTouch(square s)//�ܹ�12������ֵ���ȵ�����-5�������ȵ�����+1�������ظ���̤��Ч
	{
		int type = s.type;

		if(s.firstTouch())
		{
			switch(type)
			{
			case 0://����
				player.health = player.health -5;
				break;
			case 3://����
				player.y = player.y - 25;
				break;
			case 4://���ʹ�
				player.x -= 2 ;
				break;
			case 5://���ʹ�
				player.x += 2 ;
				break;
			}
		}
		
		if(s.firstblood())
		{
			if(player.health < 12)
				player.health ++;
		}
	}
	
	public square touched()
	{
		for(square s:squares)
		{
			if(player.x+19 >= s.x && player.x <= s.x + 69)//վ�ڰ����Ͽ��ƶ��ľ���
			{
				if(player.y+29 >= s.y && player.y <= s.y + 15)
				{													
					return s;
				}		
			}
		}		
		return null;
	}
	
	

	public boolean isGameOk()
	{
		if(player.y < 80 || player.y > 450 || player.health <= 0 || this.GameStat == false)	
		{
			return false;
		}	
		return true;
	}
	
	public void ClearSquare()
	{	
		squares.clear();
	}
	
	public void addSquare(square s)
	{
		squares.add(s);
	}
}
