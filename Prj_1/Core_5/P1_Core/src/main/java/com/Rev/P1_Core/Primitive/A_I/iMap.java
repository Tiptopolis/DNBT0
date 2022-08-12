package com.Rev.P1_Core.Primitive.A_I;

import com.Rev.P1_Core.Primitive.aList;
import com.Rev.P1_Core.Primitive.aMap.Entry;

public interface iMap<K, V> extends Iterable<Entry<K, V>>, iGroup {

	public void put(K key, Object val);

	public void put(K key, Object... val);

	public iCollection<V> pull(K key);

	public default V get(K key) {
		return this.pull(key).get(0);
	}

	public default Object keyOf(V value) {
		aList<K> result = new aList<K>();
		for (Entry<K, V> E : this) {
			if (E.getValue().equals(value) || E.getValue() == value)
				result.append(E.getKey());
		}

		int s = result.getSize();
		if (s > 1 && s > 0)
			return result;
		else if (s == 1)
			return result.get(0);
		else
			return null;

	}

	public boolean contains(K key, Object val);

	public default boolean containsKey(K key) {
		return this.getKeys().contains(key);
	}

	public default boolean containsValue(Object val) {
		return this.getValues().contains(val);
	}

	public iCollection getKeys();

	public iCollection getValues();

	public default Object getKeyType() {
		if (this.getKeys() != null && !this.getKeys().isEmpty())
			return this.getKeys().get(0);
		else
			return null;
	}

	public default Object getValueType() {

		if (this.getValues() != null && !this.getValues().isEmpty())
			return this.getValues().get(0);
		else
			return null;

	}

}
