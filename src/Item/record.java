package Item;

import java.io.Serializable;

@SuppressWarnings("serial")
public class record implements Serializable{
	public String name = null;
	public int level = 0;
	
	public record(String name,int level)
	{
		this.name = name;
		this.level = level;
	}
}
