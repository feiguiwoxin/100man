package UI;

import java.awt.Color;
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
	private ArrayList<record> records = null;
	
	public LayerRecord()
	{
		LoadRecord();
	}

	public void drawImg(Graphics g,int level)
	{
		int pos = 0;
		int size = records.size();
		boolean hasdraw = false;
		for(record r:records)
		{			
			if(level >= r.level && false == hasdraw)
			{
				g.setColor(Color.RED);
				g.drawString("第"+(pos/2+1)+"名:", 310, 260 + pos * 20);
				pos++;
				g.drawString("你(" + String.valueOf(level)+")", 310, 260 + pos * 20);
				pos++;
				hasdraw = true;
				g.setColor(Color.YELLOW);
			}
			
			if(pos/2 >= size) break;
			g.drawString("第"+(pos/2+1)+"名:", 310, 260 + pos * 20);
			pos++;
			g.drawString(r.name+"("+String.valueOf(r.level)+")", 310, 260 + pos * 20);
			pos++;
		}
	}
	
	public int needRank(int level)
	{
		int pos = 0;
		for(pos = 0;pos < records.size();pos++)
		{
			if(level >= records.get(pos).level)
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
	
	public void RankIn(String name,int level,int pos)
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
	private void LoadRecord()
	{
		File file = new File("./rank.dat");

		ObjectInputStream os = null;
	
		try {
			os = new ObjectInputStream(new FileInputStream(file));
			records = (ArrayList<record>) os.readObject();
		} catch (Exception e) {
			records = new ArrayList<record>();
			records.add(new record("无名",0));
			records.add(new record("无名",0));
			records.add(new record("无名",0));
			records.add(new record("无名",0));
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
	
	private void SaveRecord()
	{
		if(records == null) return;
			
		File file = new File("./rank.dat");
		ObjectOutputStream os = null;
		
		try {
			os = new ObjectOutputStream(new FileOutputStream(file));
			os.writeObject(records);
		} catch (Exception e) {
			e.printStackTrace();
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
