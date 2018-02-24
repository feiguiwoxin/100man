package Control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import UI.GamePanel;

public class OptionControl extends MouseAdapter{
	private GamePanel gp = null;
	
	public OptionControl(GamePanel gp)
	{
		this.gp = gp;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getPoint().x >= 300 && e.getPoint().x <= 400 
			&& e.getPoint().y >= 70 && e.getPoint().y <= 100)
		{
			gp.StartOrContinue();
		}
		else if(e.getPoint().x >= 300 && e.getPoint().x <= 400 
				&& e.getPoint().y >= 110 && e.getPoint().y <= 140)
		{
			gp.LoadGame();
		}
		else if(e.getPoint().x >= 300 && e.getPoint().x <= 400 
				&& e.getPoint().y >= 150 && e.getPoint().y <= 180)
		{
			gp.RecordGame();
		}
	}
}
