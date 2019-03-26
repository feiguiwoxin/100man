package Control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Item.man;
import UI.GamePanel;

public class GameControl extends KeyAdapter{
	private man player = null;
	private int arrow = -1;
	private GamePanel gp = null;
	
	public GameControl(GamePanel gp,man player)
	{
		this.gp = gp;
		this.player = player;
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			gp.StartOrContinue();
			return;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S)//按S保存
		{
			gp.RecordGame();
			return;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_L)//按L返回上次游戏
		{
			gp.LoadGame();
			return;
		}
		
		if(e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT)
		{
			e.consume();//销毁实例
		}
		else
		{
			arrow = e.getKeyCode();
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				player.direction = 1;
			}
			else
			{
				player.direction = 2;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == arrow)
		{
			arrow = -1;
			player.direction = 0;
		}
	}
}
