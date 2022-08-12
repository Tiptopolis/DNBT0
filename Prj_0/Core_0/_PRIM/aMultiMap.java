package Core._PRIM;

public class aMultiMap<K, V> implements iMap<K, iCollection<V>> {
	public aMap<K, iCollection<V>> data;

	// defaults to set-backed, so no duplicates in each section

	public aMultiMap() {
		this.data = new aMap<K, iCollection<V>>();
	}

	@Override
	public void put(K key, Object val) {
		if (!this.data.containsKey(key)) {
			this.data.put(key, new aSet<V>());
			this.pull(key).add(val);
		} else {
			this.pull(key).add(val);
		}

	}

	@Override
	public void put(K key, Object... val) {
		for (int i = 0; i < val.length - 1; i++) {
			this.put(key, val[i]);
		}

	}

	@Override
	public iCollection pull(K key) {
		if (this.data.containsKey(key))
			return this.data.pull(key).get(0);

		return null;
	}

	@Override
	public boolean contains(K key, Object val) {
		if (this.containsKey(key)) {
			return this.data.pull(key).contains(val);
		}
		return false;
	}

	@Override
	public iCollection getKeys() {
		return this.data.getKeys();
	}

	@Override
	public iCollection getValues() {
		return this.data.getValues();
	}

	@Override
	public String toString() {
		String s = this.getClass().getSimpleName() + "{" + this.data.getSize() + "}\n";

		s += "[";
		for (int i = 0; i < this.data.getSize(); i++) {
			s += this.getKeys().get(i) + ":" + this.getValues().get(i);
			if (i != this.data.getSize() - 1)
				s += ",";
		}

		s += "]";
		return s;
	}

	public String toLog() {
		String log = this.getClass().getSimpleName() + "{" + this.data.keys.getSize() + "}\n";

		return log;
	}

}
