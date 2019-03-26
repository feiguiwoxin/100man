package UI;

import java.awt.Graphics;
import java.util.ArrayList;

import Item.man;
import Item.square;

public class LayerScreen {
	private ArrayList<square> squares = new ArrayList<square>();
	private man player = new man();
	public boolean GameStat = false;//游戏状态
	
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
	
	public void init()//初始化
	{
		square.init();
		player.init();
		
		squares.add(new square(115, 210, 1));//初始给人物一个板子
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
	
	//1/6概率为0，5/12概率为1，5/12概率为2
	private int NumofSquare()//每一层板子的数量
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
	
	private int TypeofSquare()//随机产生板子类型，产生钉子概率为1/3，其他概率为2/15，0为钉子，1为普通板，2为翻板，3为弹簧，4、5为传送带
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
		int squarenum = NumofSquare();//每行几个板子
		if(squarenum == 0) return;
		
		int pos = PosofSquare(squarenum);//板子的位置
		for(int i = 0; i < squarenum; i++)
		{
			int type = TypeofSquare();
			squares.add(new square(pos + i * 110, yp, type));
		}	
	}
	
	private void paintSquare(Graphics g)//画出板子
	{
		for(int i = squares.size() - 1; i >= 0; i--)
		{
			square s = squares.get(i);
			if(s.move(GameStat) == -1)//板子上升到一定高度后消失
			{
				squares.remove(i);
				continue;
			}
			
			s.drawImg(g);
		}
	}
	
	private void paintPlayer(Graphics g)//画出人物
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
			if(touched() != null) //向左撞到板子时不能继续向左移动
			{
				player.moveright(GameStat);
			}
		}
		else if(player.direction == 2)
		{
			player.moveright(GameStat);
			if(touched() != null) //向右撞到板子时不能继续向右移动
			{
				player.moveleft(GameStat);
			}
		}
		
		player.setRunSpeed(4);
		
		player.drawImg(g);
	}
	
	private void whenTouch(square s)//总共12格生命值，踩到钉子-5生命，踩到其他+1生命，重复踩踏无效
	{
		int type = s.type;

		if(s.firstTouch())
		{
			switch(type)
			{
			case 0://钉子
				player.health = player.health -5;
				break;
			case 3://弹簧
				player.y = player.y - 25;
				break;
			case 4://传送带
				player.x -= 2 ;
				break;
			case 5://传送带
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
			if(player.x+19 >= s.x && player.x <= s.x + 69)//站在板子上可移动的距离
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
