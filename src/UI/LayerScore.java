package UI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class LayerScore {

	public static final Image Num = new ImageIcon("IMG/Num.png").getImage();
	public int level = 0;
	
	public LayerScore()
	{
		init();
	}
	
	public void drawImg(Graphics g)
	{
		if(level < 10)
		{
			g.drawImage(Num, 369, 16, 369+22, 16+30,
						level*26, 0, (level+1)*26, 36, null);
		}
		else
		{
			g.drawImage(Num, 358, 16, 358+22, 16+30,
					level/10*26, 0, (level/10+1)*26, 36, null);
			g.drawImage(Num, 381, 16, 381+22, 16+30,
					(level%10)*26, 0, (level%10 + 1)*26, 36, null);
		}
	}
	
	public void init()
	{
		level = 0;
	}
}
