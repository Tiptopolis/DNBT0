
package Metatron.Core.Primitive.Struct;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.A_I.iMap;



public class aSetMap<K, V> extends _Map<K,V> {

	//a multi-map, previously aMap
	// index-mapped lists
	// 1-many[unique] mapping
	//public aList<K> keys;
	//public aList<V> values;

	public aSetMap() {
		this.keys = new aList<K>();
		this.values = new aList<V>();
	}
	
	public aSetMap(iCollection<K> k, iCollection<V> v) {
		this.keys = k;
		this.values = v;
	}
	
	public aSetMap(Class<iCollection> k, Class<iCollection> v) {
		this.keys = iCollection.newEmpty(k);
		this.values = iCollection.newEmpty(v);
	}
	

	@Override
	public  iMap<K, V> toMap() {
		return this;
	}


	@Override
	public Integer getIndexType() {
		
		return 0;
	}





}