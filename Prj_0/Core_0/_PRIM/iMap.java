package Core._PRIM;

public interface iMap<K, V> {

	public void put(K key, Object val);

	public void put(K key, Object... val);

	public iCollection pull(K key);

	public boolean contains(K key, Object val);

	public default boolean containsKey(K key) {
		return this.getKeys().contains(key);
	}

	public default boolean containsValue(Object val) {
		return this.getValues().contains(val);
	}

	public iCollection getKeys();

	public iCollection getValues();

}
