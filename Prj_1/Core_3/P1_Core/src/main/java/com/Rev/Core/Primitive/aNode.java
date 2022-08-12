package com.Rev.Core.Primitive;

import static com.Rev.Core.AppUtils.*;

import java.util.ArrayList;

import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core.Primitive.A_I.iNode;
import com.Rev.Core.Primitive.Data.aType;
import com.Rev.Core.Primitive.aMap.Entry;

public class aNode<T> implements iNode<T>, _N<Object> {

	// poolable? maybe later lolol

	protected T get; // the data/object that this is aNode of
	public aMultiMap<String, aNode> links; // linkages, the V in aMultiMap<K,V> refers to iCollection<V> and
											// aLink::aSet<aNode>

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

		
		C.append(other);

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

		if (this.links != null)
			this.links.clear();
	}
	
	public String logLinks()
	{
		String lnx = "";
		String indent = "";
		
		for(Entry<String, iCollection<aNode>> l : this.links)
		{
			//Log(l);
			
			for(Object o : l.getValue())
			{
				//Log("["+l.getValue().indexOf(o) + "]:"+o + "  " + o.getClass());
				aLink L = (aLink)o;
				lnx+=("["+l.getValue().indexOf(o) + "]:"+L.toLog() );
			}
			//Log(">>>>>>>");
		}
		
		return lnx;
	}
	
	public String logLinksLong()
	{
		String lnx = "";
		String indent = "";
		
		for(Entry<String, iCollection<aNode>> l : this.links)
		{
			
			for(Object o : l.getValue())
			{
				aLink L = (aLink)o;
				lnx+=indent+("["+l.getValue().indexOf(o) + "]:"+L.toLog() )+"\n";
			}
		}
		
		return lnx;
	}

	@Override
	public String toString() {
		String f = "";
		if (this.get != this)
			f = "="+this.get.toString();

		String s = this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode()) + "=<"
				+ this.get.getClass().getSimpleName() + ">(" + "<" + this.get.getClass().getSimpleName() + ">" + f
				+ ")";

		return s;
	}

	public String toLog() {
		String log = this.toString() + "\n";
		int l = 0;
		if (this.links != null)
			l = this.links.getSize();
		log += "Connections >[" + l + "]\n";
		log += "{";
		if (this.links != null) {
			log += "\n";
			for (int i = 0; i < this.links.getSize(); i++) {
				String K = this.links.get(i);
				aLink C = (aLink) this.links.get(K);
				log += C.getLink() + "\n";

			}
		}

		log += "}";

		return log;
	}

}
