package UI;

import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import Item.man;
import Item.square;

public class LayerScreen {
	private CopyOnWriteArrayList<square> squares = new CopyOnWriteArrayList<square>();
	private man player = new man();
	public boolean GameStat = false;
	
	public LayerScreen()
	{
		init();
	}
	
	public man getPlayer()
	{
		return player;
	}
	
	public CopyOnWriteArrayList<square> getSquares()
	{
		return squares;
	}
	
	public void init()
	{
		square.init();
		player.init();
		
		squares.add(new square(115, 150, 2));
		for(int i = 240; i <= 420; i = i + 90)
		{
			CreatSquare(i);
		}
	}
	
	public void drawImg(Graphics g)
	{
		paintSquare(g);
		paintPlayer(g);
	}	
	
	public void CreatSquare(int yp)
	{
		int squarenum = NumofSquare();
		if(0 == squarenum) return;
		
		int pos = PosofSquare(squarenum);
		for(int i = 0; i < squarenum; i++)
		{
			int type = TypeofSquare();
			squares.add(new square(pos + i * 110, yp, type));
		}
	}
	
	private void paintSquare(Graphics g)
	{
		for(int i = squares.size() - 1; i >= 0; i--)
		{
			square s = squares.get(i);
			if(-1 == s.move(GameStat))
			{
				squares.remove(i);
				continue;
			}
			
			s.drawImg(g);
		}
	}
	
	private void paintPlayer(Graphics g)
	{
		if (player == null) return;
		square s = null;
		
		player.drop(GameStat);
		if(null != (s = touched()))
		{
			player.y = s.y - 30;
			whenTouch(s);
		}
		
		if(1 == player.direction)
		{
			player.moveleft(GameStat);
			if(null != touched()) 
			{
				player.moveright(GameStat);
			}
		}
		else if(2 == player.direction)
		{
			player.moveright(GameStat);
			if(null != touched())
			{
				player.moveleft(GameStat);
			}
		}
		
		player.setRunSpeed(4);
		
		player.drawImg(g);
	}
	
	private void whenTouch(square s)
	{
		int type = s.type;
		
		if(type != 3)
		{
			player.health = 2;
		}
		
		if(s.fristTouch())
		{
			switch(type)
			{
			case 0:
				player.setRunSpeed(2);
				break;
			case 1:
				player.y = player.y - 45;
				break;
			case 3:
				player.health = player.health -1;
				break;
			case 4:
				s.destorySelfOndelay();
				break;
			}
		}
	}
	
	public square touched()
	{
		for(square s:squares)
		{
			if(player.x+19 >= s.x && player.x <= s.x + 69)
			{
				if(player.y+29 >= s.y && player.y <= s.y + 15)
				{													
					return s;
				}		
			}
		}		
		return null;
	}
	
	
	//1/6概率为0，5/12概率为1，5/12概率为2
	private int NumofSquare()
	{
		int num = (int)(10*Math.random());
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
	
	private int TypeofSquare()
	{
		int type = (int)(9*Math.random());
		return type/2;
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
	
	public boolean isGameOk()
	{
		if(player.y < 60 || player.y > 390 || player.health == 0 || false == this.GameStat)	
		{
			return false;
		}	
		return true;
	}
	
	public void ClearSquare()
	{	
		for(int i = squares.size() - 1; i >= 0; i--)
		{
			squares.remove(i);
		}	
	}
	
	public void addSquare(square s)
	{
		squares.add(s);
	}
}
