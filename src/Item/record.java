package Item;

import java.io.Serializable;

@SuppressWarnings("serial")
public class record implements Serializable{//���л�
	public String name = null;
	public int level = 0;
	
	public record(String name,int level)//���캯��
	{
		this.name = name;
		this.level = level;
	}
}
