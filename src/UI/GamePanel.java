package UI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Control.GameControl;
import Control.OptionControl;
import Item.square;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	public static final Image Screen_S = new ImageIcon("IMG/Screen_S.png").getImage();
	public static final Image Screen_P = new ImageIcon("IMG/Screen_P.png").getImage();
	public static final Image BG = new ImageIcon("IMG/BG.jpg").getImage();
	private LayerScreen screen = null;
	private LayerRecord record = null;
	private LayerScore score = null;
	private int step = 0;
	private Image Screen = null;
	
	public GamePanel()
	{
		this.setSize(GameFrame.WIDTH, GameFrame.HEIGHT - 32);
		screen = new LayerScreen();
		record = new LayerRecord();
		score = new LayerScore();
		Screen = Screen_S;
		
		this.addMouseListener(new OptionControl(this));
		this.addKeyListener(new GameControl(this, screen.getPlayer()));
	}	
	
	@Override
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		this.requestFocus();
		
		g.drawImage(BG, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT - 32, null);
		g.drawImage(Screen, 0, 0, null);
		screen.drawImg(g);
		score.drawImg(g);
		record.drawImg(g);
	}
	
	public void StartGame()
	{
		screen.GameStat = true;
		Screen = Screen_P;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void StopGame()
	{
		screen.GameStat = false;
		Screen = Screen_S;
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
		Screen = Screen_S;
		step = 0;
		screen.ClearSquare();
		screen.init();
		score.init();
		this.repaint();
	}
	
	public void RecordGame()
	{
		StopGame();
		CopyOnWriteArrayList<square> squares = screen.getSquares();
		File file = new File("IMG/record.dat");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("man,"+screen.getPlayer().x+","+screen.getPlayer().y+","+screen.getPlayer().health+"\r\n");
			bw.write("score,"+score.level+"\r\n");
			bw.write("step,"+step+"\r\n");
			for(square s:squares)
			{
				bw.write("square,"+s.x+","+s.y+","+s.type+","+s.gettouched()+"\r\n");
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
		StartGame();
	}
	
	public void LoadGame()
	{
		File file = new File("IMG/record.dat");
		String line = null;
		String[] data = null;
		
		screen.GameStat = false;
		Screen = Screen_S;
		screen.ClearSquare();
		
		if(!file.exists())
		{
			JOptionPane.showMessageDialog(null, "没有存档数据");
		}
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null)
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
		
		square.dropspeed = 1 + score.level/30;
		this.repaint();
	}


	@Override
	public void run() {
		while(screen.isGameOk())
		{
			step ++;
			if(step * square.dropspeed % 90 == 0)
			{
				screen.CreatSquare(420);
				score.level = step / 120;
				square.dropspeed = 1 + score.level/30;
			}
			
			this.repaint();
			
			if(score.level == 99)
			{
				JOptionPane.showMessageDialog(null, "通关了，膜拜大神~~");
				ResetGame();
				break;
			}
			
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(screen.GameStat != false)
		{
			JOptionPane.showConfirmDialog(null, "Game Over", "游戏提示", JOptionPane.CLOSED_OPTION);
			ResetGame();
		}
	}
}
