package com.Rev.Core._PRIM;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core._Math.aVector;
import com.Rev.Core._PRIM.A_I.iCollection;
import com.Rev.Core._PRIM.A_I.iConnection;
import com.Rev.Core._PRIM.A_I.iNode;

public class aNode<T> implements iNode<T> {

	protected T get;
	public aMultiMap<String, aNode> connections;

	protected int primaryDepth = 0;
	protected aVector address = new aVector();

	public aNode() {
		this.get = (T) this;
		this.address = new aVector(0);
	}

	public aNode(T of) {
		this.get = of;
		this.address = new aVector(0);
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
			aConnection C = ((aConnection) this.connections.get(s));
			if (C.target == n)
				return true;
		}
		return false;

	}

	public void establishConnection(String as, aNode other, int max, Object context) {
		this.connections.put(as, new aConnection(as, other, max, context));
	}

	public void connect(String as, aNode other) {
		this.connect(as, other, null);
	}

	public void connect(String as, aNode other, Object context) {

		if (this.connections == null)
			this.connections = new aMultiMap<String, aNode>();
		if (!this.connections.containsKey(as))
			this.establishConnection(as, other, 1, context);

		iCollection<aNode> C = this.connections.get(as);

		if (C == null)
			C = new aConnection(as, other, context);

		if (C.getSize() < ((aConnection) C).max)
			this.connections.put(as, C);
		C = this.connections.get(as);

	}

	public void disconnect(String as, Object context) {
		if (this.has(as, context))
			this.connections.data.remove(as);
	}

	public boolean has(String connection) {
		if (this.connections == null || this.connections.isEmpty())
			return false;
		return this.connections.containsKey(connection.toUpperCase());
	}

	public boolean has(aConnection connection) {
		return this.connections.containsValue(connection);
	}

	public boolean has(String connection, Object context) {
		boolean has = this.has(connection);
		if (!has)
			return false;
		else {
			// aConnection P = (aConnection) this.connections.pull(connection).get(0);
			aConnection P = (aConnection) this.connections.get(connection);
			return (P.context == context);
		}

		// return false;
	}

	public boolean isEmpty() {

		return this.connections.isEmpty();
	}

	public void clear() {

		this.connections.clear();
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

		log += "Connections >[" + this.connections.getSize() + "]\n";
		// log += this.connections.toString()+"\n";
		log += "[\n";
		// log+=this.connections.toLog();
		for (int i = 0; i < this.connections.getSize(); i++) {
			String K = this.connections.get(i);
			aConnection C = (aConnection) this.connections.get(K);
			log += C.getConnection() + "\n";

		}

		log += "]";

		return log;
	}

}
