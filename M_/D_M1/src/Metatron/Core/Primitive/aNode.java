package Metatron.Core.Primitive;

import static Metatron.Core._M.M_Utils.*;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.A_I.iCycle;
import Metatron.Core.Primitive.A_I.iDisposable;
import Metatron.Core.Primitive.A_I.iGroup;
import Metatron.Core.Primitive.A_I.iMap;
import Metatron.Core.Primitive.A_I.iNode;
import Metatron.Core.Primitive.A_I.iToken;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct._Map.Entry;
import Metatron.Core.System.Event.iEvent;
import Metatron.Core.System.Event.iEventHandler;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Utils.StringUtils;

public class aNode<T> extends aToken<T> implements iNode<T>, iEventHandler {

	public static aNode NULL;
	static {
		NULL = new aNode("null", Void.TYPE);
	}

	public String label;

	// dictionary & links
	public aMap<String, aDictionary> data; // Data, Inner, Outter

	public aMap<String, Object> shared;	

	public aDictionary<aLink> links;

	//////
	public Supplier<Object> Get = () -> {
		return this.get();
	};
	public Function<String, Object> GetA = (label) -> {
		return this.get(label);
	};
	public Consumer<T> Set = (T t) -> {
		this.set(t);
	};
	public BiConsumer<String, Object> Put = (String s, Object o) -> {
		this.set(s, o);
	};

	public aNode() {
		this.value = (T) Void.class;
		this.shared = new aMap<String, Object>();
		this.data = new aMap<String, aDictionary>();
		this.type = value.getClass();
	}

	public aNode(T value) {
		this.value = value;
		this.shared = new aMap<String, Object>();
		this.data = new aMap<String, aDictionary>();
		this.type = value.getClass();
	}

	public aNode(String label, T value) {
		this(value);
		this.label = label;
	}

	@Override
	public String type() {
		if (instanceOf(aNode.class).test(this.value))
			return ((aNode) this.value).toToken();
		if (instanceOf(iToken.class).test(this.value))
			return "<" + ((iToken) this.get()).type() + ">";
		if (instanceOf(iCollection.class).test(this.value)) {
			iCollection C = ((iCollection) this.get());
			return "(" + C.getClass().getSimpleName() + "[" + C.size() + "]{<" + C.getComponentType().getSimpleName()
					+ ">})";
		} else
			return "<" + this.value.getClass().getSimpleName() + ">";
	}

	@Override
	public <String> void label(String label) {
		this.label = (java.lang.String) label;
	}

	@Override
	public String label() {
		if (this.label == null)
			return this.hashId();
		else
			return this.label;
	}

	@Override
	public T get() {
		return this.value;
	}

	public Object get(String name) {
		return this.shared.get(name);
	}

	public aDictionary getData(String name) {
		return this.data.get(name);
	}

	@Override
	public void set(T to) {
		this.value = to;
	}

	public void set(String k, Object v) {

		if (!this.shared.containsKey(k)) {
			_Map.Entry<String, Object> E = new _Map.Entry<String, Object>(k, v);
			this.shared.put(E);

		} else
			this.shared.set(k, v);
	}

	public void dispose() {
		this.label = null;
		this.Get = null;
		this.Set = null;
		this.Put = null;

		if (this.value instanceof iCycle)
			((iCycle) this.value).terminate();
		if (this.value instanceof iDisposable)
			((iDisposable) this.value).dispose();
		this.value = null;
		if (this.links != null) {
			for (Entry<Entry<Object, java.lang.String>, aLink> L : this.links)
				L.getValue().dispose();
			this.links.clear();
		}
		if (this.data != null)
			this.data.clear();
		if (this.shared != null)
			this.shared.clear();
	}

	public boolean is(String what) {
		for (Entry<String, Object> E : this.shared) {
			if (StringUtils.isFormOf(E.getKey(), what)) {
				if (instanceOf(Boolean.class).test(E.getValue()))
					return (boolean) E.getValue();
				if (instanceOf(aNode.class).test(E.getValue())) {
					aNode N = (aNode) E.getValue();
					if (instanceOf(Boolean.class).test(N.get()))
						return (boolean) N.get();
				}
			}
		}
		return false;
	}

	public boolean is(Class... c) {
		boolean cls = true;
		boolean t = true;
		for (Class C : c) {
			if (!instanceOf(C).test(this.get().getClass()))
				cls = false;
			if (!this.type.equals(C))
				t = false;

		}
		return cls || t || instanceOf(c).test(this);
	}

	public boolean is(aNode n) {

		if (n == this || this.get() == n || n.get() == this)
			return true;
		
		if(n instanceof _Map.Entry)
		{
			_Map.Entry E = (_Map.Entry)n;
			if(instanceOf(E.getValue().getClass()).test(this.get()))
				return true;
		}

		return this.equals(n);
	}

	public boolean has(String what) {

		if (this.links != null && !this.links.isEmpty())
			for (Object E : this.links) {

				if (E.equals(what) || E.equals(what.toLowerCase()) || E.equals(what.toUpperCase()))
					return true;

				aMultiMap.Entry e = (aMultiMap.Entry) E;

				boolean a = e.getKey().equals(what) || e.getKey().equals(what.toUpperCase())
						|| e.getKey().equals(what.toLowerCase());
				boolean b = e.getValue().equals(what) || e.getValue().equals(what.toUpperCase())
						|| e.getValue().equals(what.toLowerCase());
				if (a || b)
					return true;
			}

		for (Entry<String, Object> E : this.shared) {
			if (StringUtils.isFormOf(E.getKey(), what))
				return true;
		}
		for (Entry<String, aDictionary> E : this.data)
			if (StringUtils.isFormOf(E.getKey(), what))
				return true;

		return false;

	}

	public boolean hasA(String what) {
		if (this.links != null && !this.links.isEmpty())
			for (Object E : this.links) {

				if (StringUtils.isFormOf("" + E, what))
					return true;

				aMultiMap.Entry e = (aMultiMap.Entry) E;

				boolean a = e.getKey().equals(what) || e.getKey().equals(what.toUpperCase())
						|| e.getKey().equals(what.toLowerCase());
				boolean b = e.getValue().equals(what) || e.getValue().equals(what.toUpperCase())
						|| e.getValue().equals(what.toLowerCase());
				if (a || b)
					return true;
			}

		boolean b = false;
		for (Entry<String, Object> E : this.shared) {
			if (StringUtils.isFormOf(E.getKey(), what))
				if (E.getValue() instanceof aNode) {
					aNode N = (aNode) E.getValue();
					if (!N.equals(aNode.NULL) && N != aNode.NULL && !N.equals(aValue.EMPTY) && N != aValue.EMPTY)
						b = true;
					if (b)
						return b;
				}
		}

		for (Entry<String, aDictionary> E : this.data)
			if (StringUtils.isFormOf(E.getKey(), what))
				return true;

		return false;
	}

	public boolean hasA(Class c) {
		for (Entry E : this.data)
			if (instanceOf(c).test(E.getValue()))
				return true;

		return false;
	}

	public boolean has(Class... c) {
		for (Entry E : this.data)
			if (instanceOf(c).test(E.getValue()))
				return true;

		return false;
	}

	public boolean has(Object context) {
		if (this.links != null || !this.links.isEmpty())
			for (Entry<Object, String> E : this.links.getKeys()) {
				if (E.getKey() == context || E.getKey().equals(context))
					return true;
			}
		return false;
	}

	public boolean has(Object context, String as) {

		if (this.shared.contains(as, context))
			return true;

		if (this.links == null || this.links.isEmpty())
			return false;

		for (Entry<Object, String> E : this.links.getKeys()) {
			if ((E.getKey() == context || E.getKey().equals(context))
					&& (E.getValue().equals(as) || E.getValue().toLowerCase().equals(as.toLowerCase())))
				return true;
		}
		return false;
	}

	public boolean has(Entry<Object, String> key) {

		if (this.shared.contains(key.getValue(), key.getKey()))
			return true;

		if (this.links == null || this.links.isEmpty())
			return false;

		return this.links.containsKey(key);
	}

	public boolean has(aNode target) {

		if (this.shared.containsValue(target))
			return true;

		if (this.links == null || this.links.isEmpty())
			return false;

		return this.links.containsValue(target);
	}

	@Override
	public boolean handle(iEvent o) {
		return false;
	}

	@Override
	public boolean handle(String o) {
		if (this.label != null)
			Log(this.getClass().getSimpleName() + "@" + this.label + ">>=" + o);
		else
			Log(this.getClass().getSimpleName() + "@" + this.hashCode() + ">>=" + o);

		return false;
	}

	private static void _LINKS_() {

	}

	// [source->target]
	public aLink link(Object context, String as, aNode target) {
		return this.link(context, as, target, 0);
	}

	// [source <-> target]
	public aLink link(Object context, String as, String to, aNode target) {
		return this.link(context, as, to, target, 0, 0);
	}

	// [source->target] @ phase
	public aLink link(Object context, String as, aNode target, Number phase) {

		if (this.links == null)
			this.links = new aDictionary<aLink>();

		aLink L = null;
		Entry<Object, String> E = null;
		if (!this.links.containsKey(context, as)) {

			L = new aLink(this, as, target, context, phase); // phase goes here, <LL> :/
			E = new Entry<Object, String>(context, as);

			this.links.put(context, as, L);

		} else {
			E = new Entry<Object, String>(context, as);
			L = (aLink) ((Object) this.links.get(E));
			L.append(phase, target);
		}
		return L;
	}

	public aLink link(Object context, String as, String to, aNode target, Number phaseAs, Number phaseTo) {

		target.link(context, to, this, phaseTo);

		aLink L = this.link(context, as, target, phaseAs);
		return L;
	}

	public aLink link(aLink link) {
		if (this.links == null)
			this.links = new aDictionary<aLink>();

		this.links.put(new Entry(this, link.label), link);
		return link;
	}

	public aList<aMultiMap.Entry> getLinks() {

		aList<aMultiMap.Entry> out = new aList<aMultiMap.Entry>();
		for (Object o : this.links)
			out.append((aMultiMap.Entry) o);

		return out;
	}

	public aList<aNode> getLinked(String as) {

		aList<aNode> res = new aList<aNode>();

		if (this.links != null && !this.links.isEmpty())
			for (Object E : this.links) {

				aMultiMap.Entry e = (aMultiMap.Entry) E;

				boolean a = e.getKey().equals(as) || e.getKey().equals(as.toUpperCase())
						|| e.getKey().equals(as.toLowerCase());
				boolean b = e.getValue().equals(as) || e.getValue().equals(as.toUpperCase())
						|| e.getValue().equals(as.toLowerCase());
				if (a || b)
					res.append(e);
			}
		if (res.isEmpty())
			return null;

		return res;
	}

	public boolean hasLink(String what) {
		if (this.links != null && !this.links.isEmpty())
			for (Object E : this.links) {

				if (E.equals(what) || E.equals(what.toLowerCase()) || E.equals(what.toUpperCase()))
					return true;

				aMultiMap.Entry e = (aMultiMap.Entry) E;

				boolean a = e.getKey().equals(what) || e.getKey().equals(what.toUpperCase())
						|| e.getKey().equals(what.toLowerCase());
				boolean b = e.getValue().equals(what) || e.getValue().equals(what.toUpperCase())
						|| e.getValue().equals(what.toLowerCase());
				if (a || b)
					return true;
			}
		return false;
	}

	public void disconnect(aNode target) {
		aList<Entry<Object, String>> toDispose = new aList<Entry<Object, String>>();

		for (Entry E : this.links.getEntries()) {
			Entry<Object, String> K = (Entry<Object, String>) E.getKey();
			aLink V = (aLink) E.getValue();

			if (V.contains(target))
				V.remove(V.indexOf(target));
			if (V.isEmpty())
				toDispose.append(K);
		}
		for (Entry<Object, String> D : toDispose) {
			this.links.remove(D);
		}
		if (this.links.isEmpty())
			this.links = null;
	}

	public void disconnect(Object context) {
		aList<Entry<Object, String>> toDispose = new aList<Entry<Object, String>>();

		for (Entry E : this.links.getEntries()) {
			Entry<Object, String> K = (Entry<Object, String>) E.getKey();
			aLink V = (aLink) E.getValue();
			if (K.getKey() == context || K.getKey().equals(context)) {
				V.clear();
				toDispose.append(K);
			}

		}
		for (Entry<Object, String> D : toDispose) {
			this.links.remove(D);
		}
		if (this.links.isEmpty())
			this.links = null;
	}

	public void disconnect(Object context, String as) {

		aList<Entry<Object, String>> toDispose = new aList<Entry<Object, String>>();

		for (Entry E : this.links.getEntries()) {
			Entry<Object, String> K = (Entry<Object, String>) E.getKey();
			aLink V = (aLink) E.getValue();

			if ((V.context == context || V.context.equals(context)) && V.label.equals(as)) {
				V.clear();
			}
			if (V.isEmpty())
				toDispose.append(K);

		}
		for (Entry<Object, String> D : toDispose) {
			this.links.remove(D);
		}
		if (this.links.isEmpty()) {
			this.links = null;
		}

	}

	public aLink getLink(Object context, String label) {

		if (this.links == null)
			this.links = new aDictionary<aLink>();

		Object L = this.links.get(context, label);

		if (L != null) {
			if (L instanceof aLink)
				return (aLink) L;
			else if (L instanceof iCollection && ((iCollection) L).size() == 1)
				if (((iCollection) L).get(0) instanceof aLink)
					return (aLink) ((iCollection) L).get(0);

		}
		return null;
	}

	public aMultiMap<String, Object> search(String... terms) {
		return iMap.search(this.shared, terms);
	}

	public void takeFrom(aNode from, String at) {
		// original form lol
		// this.shared.put((Entry<String, Object>) otherNode.shared.take("-o-"));
		this.shared.put((Entry<String, Object>) from.shared.take(at));

	}

	@Override
	public int compareTo(Object other) {

		// if(other instanceof )

		return 0;
	}

	public aNode fromTrivial(iGroup from) {
		if (from.size() == 1) {
			Object fI = from.get(from.firstIndex());
			if (fI instanceof aNode)
				return (aNode) fI;
			else
				return new aNode(fI);
		} else
			return null;
	}

	public iGroup toTrivial() {
		return new aSet<aNode>(this);
	}

	public String toTag() {
		String tag = "";

		if (this.get() instanceof Class)
			tag = ((Class) this.get()).getSimpleName();
		else
			tag = this.get().getClass().getSimpleName();

		tag = "<" + tag + ">";
		return tag;
	}

	public String toToken() {
		String tag = "";

		if (instanceOf(iCollection.class).test(this.value)) {
			iCollection C = ((iCollection) this.get());
			String T = this.get().getClass().getSimpleName();
			if (this.label != null)
				tag = "[<{" + this.label + ">}]\n";
			return tag + "(" + C.getClass().getSimpleName() + "[" + C.size() + "]" + C + ")";
		}

		if (this.get() instanceof Class)
			tag = ((Class) this.get()).getSimpleName();
		else
			tag = this.get().getClass().getSimpleName();
		tag = "<" + tag + ">";

		if (this.label != null)
			tag = "[" + this.label + "] = " + tag;

		return tag;
	}

	public String toNodeTag() {
		String value = "";
		if (this.get() != this)
			value = "" + this.get();

		String c = "";
		if (this.get() instanceof iCollection || this.get() instanceof iMap || this.get() instanceof iGroup)
			c += "@" + this.get().hashCode();

		String v = "{(" + value + "):(" + this.toTag() + c + ")}]";
		return v;
	}

	public String toPropTag() {
		String a = "" + this.label;
		String b = "[(" + this.value + "):<" + this.value.getClass().getSimpleName() + ">]";
		if (!a.equals("") && !a.equals("null") && !a.equals(" "))
			return a + " = " + b;
		else
			return b;
	}

	public String toPropIndexTag() {
		String a = "" + this.label;
		String b = "[(" + this.value + "):<" + this.value.getClass().getSimpleName() + ">]";
		if (!a.equals("") && !a.equals("null") && !a.equals(" "))
			return a + " = " + b;
		else
			return " = " + b;
	}

	@Override
	public String toString() {
		
		return this.toToken();
	}

	public String toLog() {
		String log = this.toString() + "\n";
		if (this.links != null)
			for (Object o : this.links) {
				Entry e = (Entry) o;
				log += " " + e.getKey() + "\n";
				aLink l = (aLink) e.getValue();
				log += l.toLog() + "\n";
			}
		if (this.shared != null) {
			log += "  [SHARED]{\n";
			log += "     " + this.shared.toLog();
		}
		log += "  }\n";
		return log;
	}

}
