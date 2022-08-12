package com.Rev.Core.Primitive;



public class aLink extends aSet<aNode>{

	//defines a connection TO aNode, under what context
	
	public String label = "";
	protected Object context;
	public aNode target;

	protected String inSymbol = "*<";
	public String outSymbol = ">*";

	public int max = -1; // -1 means unlimited

	public aLink(String label, aNode to) {
		super();
		this.label = label;
		this.target = to;
		this.append(to);

	}

	//establish a link to aNode with specified max connections, no context constraints
	public aLink(String label, aNode to, int max) {
		this(label, to);
		this.max = max;
	}

	//establish a link to aNode with context constraints, no max limit
	public aLink(String label, aNode to, Object context) {
		this(label, to);
		this.context = context;

	}

	//establish a link to aNode with specified max connectionsand context constraints
	public aLink(String label, aNode to, int max, Object context) {
		this(label, to, max);
		this.context = context;
	}

	//returns what this is a Node of
	public Object get() {

		if (this.max == 1)
			return this.get(0);

		return this;
	}



	//how many connections of this type exist
	private String sizeString() {
		if (this.max > 1)
			return "[" + this.getSize() + "/" + this.max + "]";
		else
			return "[" + this.getSize() + "]";
	}

	public String getLink() {
		String contextStr = "<{[(" + this.context.getClass().getSimpleName() + "@"
				+ Integer.toHexString(this.context.hashCode()) + ")]}>";
		return this.toString() + this.sizeString() + " : " + this.target + " % " + contextStr;
	}
	
	@Override
	public String toString() {
		return this.inSymbol + this.label + this.outSymbol;
	}

	@Override
	public String toLog() {
		String log = this.toString() + "[" + this.getSize() + "]{";
		for (int i = 0; i < this.getSize(); i++) {
			log += "["+i+"|o>";
			log += this.data[i].toString()+"|";
		}
		log+="}";
		return log;
	}
}
