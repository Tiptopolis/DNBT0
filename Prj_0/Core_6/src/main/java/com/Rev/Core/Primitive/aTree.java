package com.Rev.Core.Primitive;

public class aTree<T> extends aSet<T> {

	//UNDER CONSTRUCTION
	public aNode<T> root;
	public aList<aBranch<T>> branches;

	public aTree() {
		this.branches = new aList<aBranch<T>>();
	}

	public void setRoot(aNode<T> n) {
		this.root = n;
	}

	protected class aBranch<T> extends aLinkedList<T> {

		


	}
}
