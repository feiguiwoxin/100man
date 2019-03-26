package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Item.record;

public class LayerRecord {
	private ArrayList<record> records = null;//动态数组
	
	public LayerRecord()
	{
		LoadRecord();
	}

	public void drawImg(Graphics g,int level)
	{
		int pos = 0;
		int size = records.size();//获取记录的个数
		boolean hasdraw = false;
		g.setFont(new Font("楷体",Font.BOLD,35));
		g.setColor(Color.GRAY);//设置字体颜色
		g.drawString("记录",310,230);
		g.setFont(new Font("楷体",Font.BOLD,20));
		for(record r:records)
		{			
			if(level >= r.level && hasdraw == false)//当前层数大于记录的层数
			{
				g.setColor(Color.RED);//设置字体颜色（红色则为当前进行的）
				g.drawString("第"+(pos/2+1)+"名:", 310, 265 + pos * 20);//显示排名的一行
				pos++;
				g.drawString("你(" + String.valueOf(level)+")", 310, 265 + pos * 20);//显示层数的一行
				pos++;
				hasdraw = true;
			}
			g.setColor(Color.YELLOW);
			if(pos/2 >= size) break;
			g.drawString("第"+(pos/2+1)+"名:",  310, 265 + pos * 20);
			pos++;
			g.drawString(r.name+"("+String.valueOf(r.level)+")", 310, 265 + pos * 20);
			pos++;
		}
	}
	
	public int needRank(int level)//返回层数最低的位置
	{
		int pos = 0;
		for(pos = 0;pos < records.size();pos++)
		{
			if(records.get(pos).level <= level)//当前层数大于记录中的层数
			{
				break;
			}
		}
		
		if(pos < records.size())
		{
			return pos;
		}
		return -1;
	}
	
	public void RankIn(String name,int level,int pos)//将成绩存入record数组，并将原本最后一个成绩删除
	{
		if(name == null)
		{
			name = "无名";
		}
		records.add(pos, new record(name, level));
		records.remove(records.size() - 1);
		SaveRecord();
	}
	
	@SuppressWarnings("unchecked")
	private void LoadRecord()//读取记录
	{
		File file = new File("./rank.dat");//存放纪录的文件

		ObjectInputStream os = null;

		try {
			os = new ObjectInputStream(new FileInputStream(file));
			records = (ArrayList<record>) os.readObject();//将记录读出文件
		} catch (Exception e) {
			records = new ArrayList<record>();
			records.add(new record("无",0));
			records.add(new record("无",0));
			records.add(new record("无",0));
			records.add(new record("无",0));
			if(file.exists())
			{
				JOptionPane.showMessageDialog(null, "记录被破坏，重置记录");
			}
		}
		finally
		{
			try {
				os.close();
			} catch (Exception e) {
			}
		}
	}
	
	private void SaveRecord()//保存记录
	{
		if(records == null) return;
			
		File file = new File("./rank.dat");
		ObjectOutputStream os = null;
		
		try {
			os = new ObjectOutputStream(new FileOutputStream(file));
			os.writeObject(records);//将记录写入文件
		} catch (Exception e) {
			e.printStackTrace();//打印错误信息
		}
		finally
		{
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
