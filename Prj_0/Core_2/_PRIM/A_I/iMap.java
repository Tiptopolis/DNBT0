package Core._PRIM.A_I;

import Core._PRIM.aMap.Entry;

public interface iMap<K, V> extends Iterable<K>{

	public void put(K key, Object val);

	public void put(K key, Object... val);

	public iCollection<V> pull(K key);
	
	public default V get(K key)
	{
		return this.pull(key).get(0);
	}

	public boolean isEmpty();
	
	public int getSize();
	
	public boolean contains(K key, Object val);
	
	public void clear();

	public default boolean containsKey(K key) {
		return this.getKeys().contains(key);
	}

	public default boolean containsValue(Object val) {
		return this.getValues().contains(val);
	}

	public iCollection getKeys();

	public iCollection getValues();

}
