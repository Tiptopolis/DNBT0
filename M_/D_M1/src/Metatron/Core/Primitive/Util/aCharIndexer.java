package Metatron.Core.Primitive.Util;

import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.Utils.StringUtils;

public class aCharIndexer {
//<String, aVector>, Data->Vector map
	// takes String and reports
	// (fI,C,lI)
	public aMultiMap<String, aVector<Integer>> things = new aMultiMap<String, aVector<Integer>>();

	public aCharIndexer(String s) {
		aList<String> decomp = StringUtils.toList(StringUtils.CharArray(s));
		for (String D : decomp) {
			int fI = s.indexOf(D);
			int lI = s.lastIndexOf(D);
			int C = fI - lI;
			int d = StringUtils.count(s, D);
			things.put(D, new aVector<Integer>(fI, lI, C, d));
		}
	}

}