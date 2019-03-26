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
	private ArrayList<record> records = null;//��̬����
	
	public LayerRecord()
	{
		LoadRecord();
	}

	public void drawImg(Graphics g,int level)
	{
		int pos = 0;
		int size = records.size();//��ȡ��¼�ĸ���
		boolean hasdraw = false;
		g.setFont(new Font("����",Font.BOLD,35));
		g.setColor(Color.GRAY);//����������ɫ
		g.drawString("��¼",310,230);
		g.setFont(new Font("����",Font.BOLD,20));
		for(record r:records)
		{			
			if(level >= r.level && hasdraw == false)//��ǰ�������ڼ�¼�Ĳ���
			{
				g.setColor(Color.RED);//����������ɫ����ɫ��Ϊ��ǰ���еģ�
				g.drawString("��"+(pos/2+1)+"��:", 310, 265 + pos * 20);//��ʾ������һ��
				pos++;
				g.drawString("��(" + String.valueOf(level)+")", 310, 265 + pos * 20);//��ʾ������һ��
				pos++;
				hasdraw = true;
			}
			g.setColor(Color.YELLOW);
			if(pos/2 >= size) break;
			g.drawString("��"+(pos/2+1)+"��:",  310, 265 + pos * 20);
			pos++;
			g.drawString(r.name+"("+String.valueOf(r.level)+")", 310, 265 + pos * 20);
			pos++;
		}
	}
	
	public int needRank(int level)//���ز�����͵�λ��
	{
		int pos = 0;
		for(pos = 0;pos < records.size();pos++)
		{
			if(records.get(pos).level <= level)//��ǰ�������ڼ�¼�еĲ���
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
	
	public void RankIn(String name,int level,int pos)//���ɼ�����record���飬����ԭ�����һ���ɼ�ɾ��
	{
		if(name == null)
		{
			name = "����";
		}
		records.add(pos, new record(name, level));
		records.remove(records.size() - 1);
		SaveRecord();
	}
	
	@SuppressWarnings("unchecked")
	private void LoadRecord()//��ȡ��¼
	{
		File file = new File("./rank.dat");//��ż�¼���ļ�

		ObjectInputStream os = null;

		try {
			os = new ObjectInputStream(new FileInputStream(file));
			records = (ArrayList<record>) os.readObject();//����¼�����ļ�
		} catch (Exception e) {
			records = new ArrayList<record>();
			records.add(new record("��",0));
			records.add(new record("��",0));
			records.add(new record("��",0));
			records.add(new record("��",0));
			if(file.exists())
			{
				JOptionPane.showMessageDialog(null, "��¼���ƻ������ü�¼");
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
	
	private void SaveRecord()//�����¼
	{
		if(records == null) return;
			
		File file = new File("./rank.dat");
		ObjectOutputStream os = null;
		
		try {
			os = new ObjectOutputStream(new FileOutputStream(file));
			os.writeObject(records);//����¼д���ļ�
		} catch (Exception e) {
			e.printStackTrace();//��ӡ������Ϣ
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
