package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import Control.GameControl;
import Item.square;
import Start.GameStart;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	public static final Image Screen1 = new ImageIcon(GameStart.class.getResource("/IMG/screen.png")).getImage();
	private static final Image BG = new ImageIcon(GameStart.class.getResource("/IMG/background.png")).getImage();
	//private static ArrayList<Image> BGs = null;
	private LayerScreen screen = null;
	private LayerRecord record = null;
	private LayerScore score = null;
	private int step = 0;
	private Image Screen = null;
	
	class OptionAction implements ActionListener{//鼠标监听事件
		int K;
		private GamePanel gp = null;
		public OptionAction(GamePanel gp,int k)
		{
			K=k;
			this.gp = gp;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(K==1)
				gp.StartOrContinue();
			else if(K==2)
				gp.LoadGame();
			else
				gp.RecordGame();
		}
	}
	
	
	public GamePanel()
	{
		this.setSize(GameFrame.WIDTH, GameFrame.HEIGHT - 28);
		screen = new LayerScreen();
		record = new LayerRecord();
		score = new LayerScore();
		Screen = Screen1;
		setLayout(null);
		JButton b[]= {new JButton("开始/暂停"),new JButton("上次游戏"),new JButton("保存游戏")};
		this.add(b[0]);
		this.add(b[1]);
		this.add(b[2]);
		b[0].setBounds(300,70,100,30);
		b[1].setBounds(300,110,100,30);
		b[2].setBounds(300,150,100,30);
		OptionAction startAction = new OptionAction(this,1);
		OptionAction lastAction = new OptionAction(this,2);
		OptionAction saveAction = new OptionAction(this,3);
		b[0].addActionListener(startAction);
		b[1].addActionListener(lastAction);
		b[2].addActionListener(saveAction);
		
		this.addKeyListener(new GameControl(this, screen.getPlayer()));
	}	
	
	@Override
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		this.requestFocus();
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("宋体",Font.BOLD,20));		
		g.drawImage(Screen, 0, 0, null);
		g.drawImage(BG, 0, 60, GameFrame.WIDTH-140, GameFrame.HEIGHT - 28, null);//背景
		screen.drawImg(g);
		score.drawImg(g);
		record.drawImg(g, score.level);

		
	}
	
	public void StartGame()
	{
		screen.GameStat = true;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void StopGame()
	{
		screen.GameStat = false;
		this.repaint();
	}
	
	public void StartOrContinue()
	{
		if(screen.GameStat == false)
		{
			StartGame();
		}
		else
		{
			StopGame();
		}
	}
	
	public void ResetGame()
	{
		screen.GameStat = false;
		step = 0;
		screen.ClearSquare();
		screen.init();
		score.init();
		this.repaint();
	}
	
	public void RecordGame()//保存游戏
	{
		StopGame();
		ArrayList<square> squares = screen.getSquares();
		File file = new File("./record.dat");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("man,"+screen.getPlayer().x+","+screen.getPlayer().y+","+(screen.getPlayer().health)+"\r\n");
			bw.write("score,"+score.level+"\r\n");
			bw.write("step,"+step+"\r\n");
			for(square s:squares)
			{
				bw.write("square,"+s.x+","+s.y+","+s.type+","+s.gettouched()+","+s.destroytime+"\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		finally
		{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		JOptionPane.showMessageDialog(null, "Save OK~~");
	}
	
	public void LoadGame()//读取游戏
	{
		File file = new File("./record.dat");
		String line = null;
		String[] data = null;
		
		screen.GameStat = false;
		screen.ClearSquare();
		
		if(!file.exists())
		{
			JOptionPane.showMessageDialog(null, "没有存档数据");
		}
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null)//读文件，每次读一行
			{
				data = line.split(",");
				if(data[0].equals("man"))
				{
					screen.getPlayer().x = Integer.parseInt(data[1]);
					screen.getPlayer().y = Integer.parseInt(data[2]);
					screen.getPlayer().health = Integer.parseInt(data[3]);
				}
				else if(data[0].equals("score"))
				{
					score.level = Integer.parseInt(data[1]);
				}
				else if(data[0].equals("step"))
				{
					step = Integer.parseInt(data[1]);
				}
				else if(data[0].equals("square"))
				{
					square s= new square(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
					s.settouched(Integer.parseInt(data[4]));
					s.destroytime = Integer.parseInt(data[5]);
					screen.addSquare(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		square.dropspeed = 2 + score.level/30;
		this.repaint();
	}


	@Override
	public void run() {
		while(screen.isGameOk())
		{
			step ++;
			if(step * square.dropspeed % 90 == 0) {
				screen.CreatSquare(460);
				score.level = step / 120;//运行120次run函数level+1
				square.dropspeed = 1 + score.level/30;//每30层难度增加一次
			}
			
			this.repaint();
			
			if(score.level == 100)
			{
				JOptionPane.showMessageDialog(null, "恭喜你，通关了！");
				break;
			}
			
			try {
				Thread.sleep(30);//延迟更新
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(screen.GameStat != false)
		{		
			int pos = record.needRank(score.level);
			
			if(pos >= 0)
			{
				String name = JOptionPane.showInputDialog("您破纪录了，请输入您的姓名：", "无名");
				record.RankIn(name, score.level, pos);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Game Over");
			}
			
			ResetGame();
		}
	}
}
