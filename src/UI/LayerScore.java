package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import Start.GameStart;

public class LayerScore {

	public static final Image Num = new ImageIcon(GameStart.class.getResource("/IMG/Num.png")).getImage();
	public int level = 0;
	
	public LayerScore()
	{
		init();
	}
	
	public void drawImg(Graphics g)
	{
		g.setFont(new Font("楷体",Font.BOLD,25));
		g.setColor(Color.YELLOW);//设置字体颜色
		g.drawString("地下",145,40);
		g.drawString("层",260,40);
		if(level < 10)
		{
			g.drawImage(Num, 226, 16, 226+22, 16+30,
						level*26, 0, (level+1)*26, 36, null);
			//将num图片的每一位数字映射到图形界面中，上一行代表目标矩阵的坐标，下一行代表源矩阵的坐标
		}
		else
		{
			g.drawImage(Num, 203, 16, 203+22, 16+30,
					level/10*26, 0, (level/10+1)*26, 36, null);
			g.drawImage(Num, 226, 16, 226+22, 16+30,
					(level%10)*26, 0, (level%10 + 1)*26, 36, null);
		}
	}
	
	public void init()
	{
		level = 0;
	}
}
