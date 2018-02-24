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
		
		if(e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT)
		{
			e.consume();
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
