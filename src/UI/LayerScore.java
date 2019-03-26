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
		g.setFont(new Font("����",Font.BOLD,25));
		g.setColor(Color.YELLOW);//����������ɫ
		g.drawString("����",145,40);
		g.drawString("��",260,40);
		if(level < 10)
		{
			g.drawImage(Num, 226, 16, 226+22, 16+30,
						level*26, 0, (level+1)*26, 36, null);
			//��numͼƬ��ÿһλ����ӳ�䵽ͼ�ν����У���һ�д���Ŀ���������꣬��һ�д���Դ���������
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
