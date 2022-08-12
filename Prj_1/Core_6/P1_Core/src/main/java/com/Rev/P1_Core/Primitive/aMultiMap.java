package com.Rev.P1_Core.Primitive;

import static com.Rev.P1_Core.AppUtils.*;

import java.util.HashMap;
import java.util.Iterator;

import com.Rev.P1_Core.Primitive.A_I.iCollection;
import com.Rev.P1_Core.Primitive.A_I.iMap;
import com.Rev.P1_Core.Primitive.aMap.Entry;


public class aMultiMap<K, V> implements iMap<K, iCollection<V>> {
	public aMap<K, iCollection<V>> data;

	// defaults to set-backed, so no duplicates in each section
	//HashMap m;
	public aMultiMap() {
		this.data = new aMap<K, iCollection<V>>();
	}

	@Override
	public void put(K key, Object val) {
		if (!this.containsKey(key)) {
			this.data.put(key, new aSet<V>());
			this.pull(key).append(val);
		} else {
			this.pull(key).append(val);
		}
		
		
		

	}

	public void put(K key) {
		if (!this.containsKey(key))
			this.data.put(key, new aList<V>());
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

	public K get(int index)
	{
		return (K) this.getKeys().get(index);
	}
	
	public V get(K key, int index) {
		return (V) this.pull(key).get(index);
	}

	public V get(K key, Object val) {
		aList L = (aList) this.pull(key);
		if (L.contains(val))
			return (V) L.get(L.indexOf(val));
		else
			return null;
	}

	
	
	@Override
	public void clear() {
		this.data.clear();

	}

	@Override
	public boolean isEmpty() {
		return this.data.isEmpty();
	}

	public boolean isEmpty(K key) {
		return this.pull(key).isEmpty();
	}
	
	@Override
	public int getSize()
	{
		return this.getKeys().getSize();
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
		//String s = this.getClass().getSimpleName() + "{" + this.data.getSize() + "}\n";
		String s = "";
		String indent = "    ";
		
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
		log += "[";
		for (int i = 0; i < this.data.getSize(); i++) {
			log += this.getKeys().get(i) + ":" + this.getValues().get(i);
			if (i != this.data.getSize() - 1)
				log += ",";
		}

		log += "]";
		return log;
	}

	@Override
	public Iterator<Entry<K, iCollection<V>>> iterator() {
		return this.data.iterator();			
	}

}
