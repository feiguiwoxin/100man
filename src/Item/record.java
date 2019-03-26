package Item;

import java.io.Serializable;

@SuppressWarnings("serial")
public class record implements Serializable{//序列化
	public String name = null;
	public int level = 0;
	
	public record(String name,int level)//构造函数
	{
		this.name = name;
		this.level = level;
	}
}
