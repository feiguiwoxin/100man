package UI;

import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	public static final int WIDTH = 430;
	public static final int HEIGHT = 468;
	
	public GameFrame()
	{
		int s_width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int s_height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		this.setTitle("是男人就下100层");
		this.setSize(WIDTH, HEIGHT);
		this.setLocation((s_width - WIDTH)/2, (s_height - HEIGHT + 10)/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		GamePanel gp = new GamePanel();
		this.setContentPane(gp);
	}
}
