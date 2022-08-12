package com.Rev.Core.Primitive;

public class aStack<T> extends aList<T>{

	//meh?
	
	public void push(T entry)
	{
		this.insert(entry, 0);
	}
	
	public T pop()
	{
		int last = this.getSize()-1;
		T get = this.get(last);
		this.remove(last);
		return get;
	}
	
}
