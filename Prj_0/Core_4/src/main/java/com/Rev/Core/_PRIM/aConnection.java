package com.Rev.Core._PRIM;

import java.util.Iterator;

import com.Rev.Core._PRIM.A_I.iCollection;
import com.Rev.Core._PRIM.A_I.iConnection;

public class aConnection extends aSet<aNode> implements iConnection {

	public String label = "";
	protected Object context;
	public aNode target;

	protected String inSymbol = "*<";
	public String outSymbol = ">*";

	public int max = -1; // -1 means unlimited

	public aConnection(String label, aNode to) {
		super();
		this.label = label;
		this.target = to;
		this.append(to);

	}

	public aConnection(String label, aNode to, int max) {
		this(label, to);
		this.max = max;
	}

	public aConnection(String label, aNode to, Object context) {
		this(label, to);
		this.context = context;

	}

	public aConnection(String label, aNode to, int max, Object context) {
		this(label, to, max);
		this.context = context;
	}

	public Object get() {

		if (this.max == 1)
			return this.get(0);

		return this;
	}

	@Override
	public String toString() {
		return this.inSymbol + this.label + this.outSymbol;
	}

	private String sizeString() {
		if (this.max > 1)
			return "[" + this.getSize() + "/" + this.max + "]";
		else
			return "[" + this.getSize() + "]";
	}

	public String getConnection() {
		String contextStr = "<{[(" + this.context.getClass().getSimpleName() + "@"
				+ Integer.toHexString(this.context.hashCode()) + ")]}>";
		return this.toString() + this.sizeString() + " : " + this.target + " % " + contextStr;
	}

	@Override
	public String toLog() {
		String log = this.toString() + "[" + this.getSize() + "]";
		for (int i = 0; i < this.getSize(); i++) {
			log += "*_ ";
			log += this.data[i].toString();
		}
		return log;
	}
}
