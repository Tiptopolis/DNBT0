package com.Rev.Core.Primitive;

import java.util.Iterator;

import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core.Primitive.A_I.iMap;

public class aMap<K, V> implements iMap<K, V> {

	// index-mapped lists
	protected aSet<K> keys;
	protected aList<V> values;

	public aMap() {
		this.keys = new aSet<K>();
		this.values = new aList<V>();
	}

	public void put(K key, Object val) {

		if (!this.contains(key, val)) {
			this.keys.append(key);
			this.values.append((V) val);
		}
		
		
	}

	public void put(K key, Object... vals) {
		for (Object v : vals) {
			this.put(key, v);
		}
	}

	// public Entry get
	// public aList<
	public K get(int index) {
		return this.keys.get(index);
	}

	public aList<V> pull(K key) {
		aList<V> result = new aList<V>();
		for (int i = 0; i < this.getSize(); i++) {
			if (this.keys.get(i) == key || this.keys.get(i).equals(key)) {
				result.append(this.values.get(i));
			}
		}

		return result;
	}

	public boolean contains(K key, Object val) {
		for (int i = 0; i < this.keys.getSize(); i++) {
			if (this.keys.get(i) == key && this.values.get(i) == val)
				return true;
		}
		return false;
	}
	


	public void remove(K key) {
		int kI = -1;
		if (this.keys.contains(key))
			kI = this.keys.indexOf(key);

		if (kI >= 0) {
			this.keys.remove(kI);
			this.values.remove(kI);
		}
	}

	@Override
	public void clear() {
		this.keys.clear();
		this.values.clear();
	}

	@Override
	public boolean isEmpty() {
		return this.keys.isEmpty();
	}

	public int getSize() {
		return this.keys.getSize();
	}

	@Override
	public iCollection getKeys() {
		return this.keys;
	}

	@Override
	public iCollection getValues() {
		return this.values;
	}

	public iCollection getEntries() {
		aList<Entry<K, V>> result = new aList<Entry<K, V>>();
		for (int i = 0; i < this.getSize() - 1; i++) {
			result.append(new Entry(this.getKeys().get(i), this.getValues().get(i)));
		}

		return result;
	}

	@Override
	public String toString() {
		// String s = this.getClass().getSimpleName() + "{" + this.getSize() + "}\n";
		String s = "";
		s += "{";
		if (this.keys != null && !this.keys.isEmpty())
			for (int i = 0; i < this.keys.getSize(); i++) {
				s += "[" + this.keys.get(i) + "|" + this.values.get(i) + "]";
				if (i != this.keys.getSize() - 1)
					s += ",";
			}
		s += "}";
		return s;
	}

	public String toLog() {
		String log = this.getClass().getSimpleName() + "{" + this.keys.getSize() + "}\n";
		for (int i = 0; i < this.keys.getSize(); i++) {
			log += "[" + i + "]" + this.keys.get(i) + "|" + this.values.get(i) + "\n";
		}

		return log;
	}

	public class Entry<K, V> {

		private K key;
		private V value;

		public Entry(K key, V val) {
			this.key = key;
			this.value = val;
		}

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}

	}

	@Override
	public Iterator<K> iterator() {
		return this.keys.iterator();
	}

}
