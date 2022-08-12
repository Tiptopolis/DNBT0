package Metatron.Core.Primitive.Data;

import static Metatron.Core.M_Utils.*;

import java.util.function.Function;

import Metatron.Core.Primitive.aLink;
import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.Struct.aLinkedSet;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.Primitive.Struct._Map.Entry;

public class aModel extends aNode<aSet<aValue>> {

	// 'aVector' of aValue
	public Function<String, aValue> get = (a) -> {
		return this.get(a);
	};

	public aModel(String label, aValue... vals) {
		super(label, new aSet<aValue>());
		this.get().appendAll(vals);
		for (aValue v : vals)
			this.shared.put(v.label, v);
	}

	public aValue get(String key) {
		for (aValue v : this.get()) {

			if (v.getKey().equals(key) || v.getKey().toUpperCase().equals(key.toUpperCase()))
				return v;
		}

		return null;
	}

	public Object getValue(String key) {
		for (aValue v : this.get())
			if (v.label.equals(key) || v.label.toUpperCase().endsWith(key.toUpperCase()))
				return v.get();

		return null;
	}

	@Override
	public String toString() {

		return this.toToken();
	}

	public String toLog() {
		String log = "<" + this.label + "> {\n";
		if (this.links != null)
			for (Object o : this.links) {
				Entry e = (Entry) o;
				log += " " + e.getKey() + "\n";
				aLink l = (aLink) e.getValue();
				log += l.toLog() + "\n";
			}
		if (this.shared != null) {
			log += "  [SHARED]{\n";
			log += "   " + this.shared.toLog()+"  }\n";
		}
		log += "}\n";
		return log;
	}

}
