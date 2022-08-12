package Core._PRIM;

public class aMap<K, V> implements iMap<K, V> {

	// index-linked lists
	protected aList<K> keys;
	protected aList<V> values;

	public aMap() {
		this.keys = new aList<K>();
		this.values = new aList<V>();
	}

	public void put(K key, Object val) {

		if (!this.contains(key, val)) {
			this.keys.add(key);
			this.values.add((V) val);
		}
	}

	public void put(K key, Object... vals) {
		for (Object v : vals) {
			this.put(key, v);
		}
	}

	// public Entry get
	// public aList<
	public V get(int index) {
		return this.values.get(index);
	}

	public aList<V> pull(K key) {
		aList<V> result = new aList<V>();
		for (int i = 0; i < this.getSize(); i++) {
			if (this.keys.get(i) == key || this.keys.get(i).equals(key)) {
				result.add(this.values.get(i));
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

	@Override
	public String toString() {
		//String s = this.getClass().getSimpleName() + "{" + this.getSize() + "}\n";
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

	}

}
