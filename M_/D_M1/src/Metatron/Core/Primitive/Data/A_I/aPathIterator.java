package Metatron.Core.Primitive.Data.A_I;

import java.util.Iterator;

public class aPathIterator implements Iterator<String>{

	String base = "";
	String delimiters = "\\";
	
	protected int at = 0;
	
	public aPathIterator(String path)
	{
		this(path,"\\");
	}
	
	public aPathIterator(String path, String delChars)
	{
		this.base = path;
		this.delimiters = delChars;
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String next() {
		// TODO Auto-generated method stub
		return null;
	}

}
