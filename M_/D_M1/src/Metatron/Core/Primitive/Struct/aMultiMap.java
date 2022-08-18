
package Metatron.Core.Primitive.Struct;

import static Metatron.Core._M.M_Utils.*;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.A_I.iMap;

public class aMultiMap<K, V> extends _Map<K, V> {

	// a multi-map, previously aMap
	// index-mapped lists
	// 1-many[unique] mapping
	// public aList<K> keys;
	// public aList<V> values;

	public aMultiMap() {
		this.keys = new aList<K>();
		this.values = new aList<V>();
	}

	public aMultiMap(iCollection<K> k, iCollection<V> v) {
		this.keys = k;
		this.values = v;
	}

	public aMultiMap(Class<iCollection> k, Class<iCollection> v) {
		this.keys = iCollection.newEmpty(k);
		this.values = iCollection.newEmpty(v);
	}

	public aMultiMap(AbstractMap<K, V> M) {
		super(M);
	}

	public aMultiMap(java.util.Map<K, V> M) {
		super(M);
	}

	/*@Override
	public void put(K k, Object v) {
		
		iCollection c = this.getAll(k);
		if (c == null || c.isEmpty()) {
			this.keys.append(k);
			this.values.append((V) v);
		}
		else {
			int i = this.keys.indexOf(k);
			K key = this.keys.get(i);
			this.keys.append(key);
			this.values.append((V) v);
		}
	}*/

	@Override
	public iMap<K, V> toMap() {
		return this;
	}

	@Override
	public Integer getIndexType() {

		return 0;
	}

}