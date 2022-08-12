package com.Rev.P1_Core.Primitive;

public class aStack<T> extends aSet<T> {

	// meh?

	public void push(T entry) {
		this.insert(entry, 0);
	}

	public T pop() {
		return this.take(0);
	}

}
