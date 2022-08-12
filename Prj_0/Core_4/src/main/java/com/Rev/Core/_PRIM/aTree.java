package com.Rev.Core._PRIM;

public class aTree<T> extends aSet<T> {

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
