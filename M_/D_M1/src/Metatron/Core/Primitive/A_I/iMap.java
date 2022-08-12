
package Metatron.Core.Primitive.A_I;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct._Map.Entry;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.Utils.StringUtils;

public interface iMap<K, V> extends iIndex<Entry<K, V>> {

	public void put(K key, Object val);

	public void put(K key, Object... val);

	public iCollection<V> getAll(K key);

	public default V get(K key) {
		if (this.getAll(key) != null)
			return this.getAll(key).get(0);
		else
			return null;
	}

	public default void set(K key, V val) {
		iCollection<K> keys = this.getKeys();
		// setAll
		aList<Integer> indices = new aList<Integer>();
		for (K k : keys)
			if (k == key || k.equals(k))
				indices.append(keys.indexOf(k));

		for (Integer i : indices)
			this.getValues().setAt(i, val);

	}

	public default Object keyOf(V value) {
		aList<K> result = new aList<K>();
		for (Entry<K, V> E : this) {
			if (E.getValue().equals(value) || E.getValue() == value)
				result.append(E.getKey());
		}

		int s = result.size();
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

	public default boolean containsKeys(K... keys) {
		for (K k : keys) {
			if (!this.containsKey(k))
				return false;
		}
		return true;
	}

	public default boolean containsValue(Object val) {
		return this.getValues().contains(val);
	}

	public default boolean containsValues(Object... vals) {
		for (Object k : vals) {
			if (!this.containsValue(k))
				return false;
		}
		return true;
	}

	@Override
	public default void remove(Integer at) {
		this.getKeys().remove(at);
		this.getValues().remove(at);
	}

	public default void remove(K k, V v) {
		for (Entry E : this) {
			int i = this.indexOf(E);
			if (E.getKey() == k || E.getKey().equals(k))
				if (E.getValue() == v || E.getValue().equals(v))
					this.remove(i);
		}
	}

	public default Object take(K index) {

		Object out = null;
		aSet<Integer> ind = new aSet<Integer>();
		aSet<_Map.Entry> found = new aSet<_Map.Entry>();
		iCollection<_Map.Entry> c = this.getEntries();

		// scan
		for (int i = 0; i < this.size(); i++) {
			_Map.Entry E = c.get(i);
			if (E.getKey() == index || E.getKey().equals(index)) {
				ind.append(i);
				found.append(E);
			}
		}

		for (Entry E : found)
			this.remove(E);

		if (found.size() == 1)
			out = found.get(0);
		else
			out = found;

		return out;

	}

	public default _Map.Entry<K, V> take(K k, V v) {
		_Map.Entry<K, V> out = null;
		aSet<Integer> ind = new aSet<Integer>();
		aSet<_Map.Entry> found = new aSet<_Map.Entry>();
		iCollection<_Map.Entry> c = this.getEntries();

		// scan
		for (int i = 0; i < this.size(); i++) {
			_Map.Entry E = c.get(i);
			if (E.getKey() == k || E.getKey().equals(k))
				if (E.getValue() == v || E.getValue().equals(v))
					found.append(E);

		}

		for (Entry e : found)
			this.remove(e);

		if (found.size() == 1)
			out = found.get(0);

		return out;
	}

	public default void transfer(iMap<K, V> to, K k, V v) {
		if (!this.contains(k, v))
			return;

		_Map.Entry found = this.take(k, v);
		to.put(k, v);

	}

	@Override
	public default Integer indexOf(Object k) {
		return this.getKeys().indexOf(k);
	}

	public default K getKey(int index) {
		return this.getKeys().get(index);
	}

	public iCollection<K> getKeys();

	public default V getValue(int i) {
		return (V) this.getValues().get(i);
	}

	public iCollection getValues();

	public default _Map.Entry<K, V> getEntry(int i) {
		return this.getEntries().get(i);
	}

	public default <E extends Entry<K, V>> iCollection<E> getEntries() {
		aSet<Entry<K, V>> result = new aSet<Entry<K, V>>();
		iCollection<K> K = this.getKeys();
		iCollection<V> V = this.getValues();

		for (int i = 0; i < this.size(); i++) {
			Entry<K, V> e = new Entry(K.get(i), V.get(i));
			result.append(e);
		}

		return (iCollection<E>) result;

	}

	public default <X, Y, E extends _Map.Entry<X, Y>> E newEntry(X key, Y val) {
		return (E) new _Map.Entry<X, Y>(key, val);
	}

	public default void forEach(Consumer<? super _Map.Entry<K, V>> action) {
		this.getEntries().forEach(action);
	}


	public static Comparator<_Map.Entry> keyComparator() {
		return new Comparator<_Map.Entry>() {

			@Override
			public int compare(Entry o1, Entry o2) {
				Object K1 = o1.getKey();
				Object K2 = o2.getKey();

				boolean compatible = K1.getClass().isAssignableFrom(K2.getClass())
						|| K2.getClass().isAssignableFrom(K1.getClass());

				if (!compatible)
					return Integer.MIN_VALUE;

				else if (K1 instanceof Comparable && K2 instanceof Comparable) {
					Comparable A = (Comparable) K1;
					Comparable B = (Comparable) K2;

					return A.compareTo(B);

				}

				return 0;
			}
		};
	}

	public static Comparator<_Map.Entry> keyComparator(Comparator c) {
		return new Comparator<_Map.Entry>() {

			@Override
			public int compare(Entry o1, Entry o2) {
				Object K1 = o1.getKey();
				Object K2 = o2.getKey();

				boolean compatible = K1.getClass().isAssignableFrom(K2.getClass())
						|| K2.getClass().isAssignableFrom(K1.getClass());

				if (!compatible)
					return Integer.MIN_VALUE;

				else if (K1 instanceof Comparable && K2 instanceof Comparable) {
					Comparable A = (Comparable) K1;
					Comparable B = (Comparable) K2;

					// return A.compareTo(B);
					return c.compare(A, B);
				}

				return 0;
			}
		};
	}

	public static Comparator<_Map.Entry> valueComparator(Comparator c) {
		return new Comparator<_Map.Entry>() {

			@Override
			public int compare(Entry o1, Entry o2) {
				Object K1 = o1.getValue();
				Object K2 = o2.getValue();

				boolean compatible = K1.getClass().isAssignableFrom(K2.getClass())
						|| K2.getClass().isAssignableFrom(K1.getClass());

				if (!compatible)
					return Integer.MIN_VALUE;

				else if (K1 instanceof Comparable && K2 instanceof Comparable) {
					Comparable A = (Comparable) K1;
					Comparable B = (Comparable) K2;

					// return A.compareTo(B);
					return c.compare(A, B);
				}

				return 0;
			}
		};
	}

	public static Comparator<_Map.Entry> valueComparator() {
		return new Comparator<_Map.Entry>() {

			@Override
			public int compare(Entry o1, Entry o2) {
				Object K1 = o1.getValue();
				Object K2 = o2.getValue();

				boolean compatible = K1.getClass().isAssignableFrom(K2.getClass())
						|| K2.getClass().isAssignableFrom(K1.getClass());

				if (!compatible)
					return Integer.MIN_VALUE;

				else if (K1 instanceof Comparable && K2 instanceof Comparable) {
					Comparable A = (Comparable) K1;
					Comparable B = (Comparable) K2;

					return A.compareTo(B);

				}

				return 0;
			}
		};
	}

	public static <K, V> aMultiMap<String, Entry<K, V>> find(iMap<K, V> map, String... terms) {
		aMultiMap<String, Entry<K, V>> out = new aMultiMap<String, Entry<K, V>>();
		for (Entry<K, V> E : map)
			for (int i = 0; i < terms.length; i++) {
				String s = terms[i];
				if (StringUtils.containsWord("" + E.getKey(), s))
					out.put(s, E);
			}
		return out;
	}

	public static <K, V> aMultiMap<K, V> search(iMap<K, V> map, String... terms) {
		aMultiMap<K, V> out = new aMultiMap<K, V>();
		for (Entry<K, V> E : map)
			for (String s : terms) {
				if (StringUtils.containsWord("" + E.getKey(), s))
					out.put(E.getKey(), E.getValue());
			}
		return out;
	}

	public default iMap sort() {
		return iMap.Sort(this);
	}

	public static iMap Sort(iMap m) {
		return Sort(m, keyComparator());
	}

	public default iMap sort(Comparator c) {
		return iMap.Sort(this, c);
	}

	public static iMap Sort(iMap m, Comparator c) {
		iCollection<aMultiMap.Entry> entries = m.getEntries();

		entries.sort(c);

		m.clear();

		for (int i = 0; i < entries.size(); i++) {
			aMultiMap.Entry E = entries.get(i);
			m.put(E.getKey(), E.getValue());
		}

		return m;
	}

	public default iMap<K, V> filterKey(Predicate<K>... by) {
		for (Predicate<K> P : by) {
			for (Entry<K, V> e : this)
				if (P.test(e.getKey()))
					this.remove(e);
		}
		return this;
	}

	public default iMap<K, V> filterValue(Predicate<V>... by) {
		for (Predicate<V> P : by) {
			for (Entry<K, V> e : this)
				if (P.test(e.getValue()))
					this.remove(e);
		}
		return this;
	}

	public String toLog();
}