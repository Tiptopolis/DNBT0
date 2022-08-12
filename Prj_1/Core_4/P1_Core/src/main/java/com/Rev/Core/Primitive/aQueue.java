package com.Rev.Core.Primitive;

public class aQueue<T> extends aSet<T> {

	// meh?

	public void push(T entry) {
		this.append(entry);
	}

	public T pop() {
		int last = this.getSize() - 1;
		T get = this.get(last);
		this.remove(last);
		return get;
	}

}
