package com.Rev.Core.Primitive;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core.Primitive.A_I.iNode;
import com.Rev.Core._Math.aVector;

public class aNode<T> implements iNode<T> {

	//poolable? maybe later lolol
	
	protected T get; // the data/object that this is aNode of
	public aMultiMap<String, aNode> links; //linkages, the V in aMultiMap<K,V> refers to iCollection<V> and aLink::aSet<aNode>

	public aNode() {
		this.get = (T) this;
	}

	public aNode(T of) {
		this.get = of;
	}

	@Override
	public T get() {
		return this.get;
	}

	@Override
	public void set(T to) {
		this.get = to;

	}

	public boolean isConnected(String s, aNode n) {

		boolean Has = this.has(s);
		if (Has) {
			aLink C = ((aLink) this.links.get(s));
			if (C.target == n)
				return true;
		}
		return false;

	}

	public void link(String as, aNode other, Object context, int max) {
		this.links.put(as, new aLink(as, other, max, context));
	}

	public void link(String as, aNode other) {
		this.link(as, other, null);
	}

	public void link(String as, aNode other, Object context) {

		if (this.links == null)
			this.links = new aMultiMap<String, aNode>();
		if (!this.links.containsKey(as))
			this.link(as, other, context, 1);

		iCollection<aNode> C = this.links.get(as);

		if (C == null)
			C = new aLink(as, other, context);

		if (C.getSize() < ((aLink) C).max)
			this.links.put(as, C);
		C = this.links.get(as);

	}

	public void disconnect(String as, Object context) {
		if (this.has(as, context))
			this.links.data.remove(as);
	}

	public boolean has(String connection) {
		if (this.links == null || this.links.isEmpty())
			return false;
		return this.links.containsKey(connection.toUpperCase());
	}

	public boolean has(aLink connection) {
		return this.links.containsValue(connection);
	}

	public boolean has(String connection, Object context) {
		boolean has = this.has(connection);
		if (!has)
			return false;
		else {
			aLink P = (aLink) this.links.get(connection);
			return (P.context == context);
		}
	}

	public boolean isEmpty() {

		return this.links.isEmpty();
	}

	public void clear() {

		this.links.clear();
	}

	@Override
	public String toString() {
		String f = "";
		if (this.get != this)
			f = this.get.toString();

		String s = this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode()) + "=<"
				+ this.get.getClass().getSimpleName() + ">(" + "<" + this.get.getClass().getSimpleName() + ">" + f
				+ ")";

		return s;
	}

	public String toLog() {
		String log = this.toString() + "\n";

		log += "Connections >[" + this.links.getSize() + "]\n";
		// log += this.connections.toString()+"\n";
		log += "[\n";
		// log+=this.connections.toLog();
		for (int i = 0; i < this.links.getSize(); i++) {
			String K = this.links.get(i);
			aLink C = (aLink) this.links.get(K);
			log += C.getLink() + "\n";

		}

		log += "]";

		return log;
	}

}
