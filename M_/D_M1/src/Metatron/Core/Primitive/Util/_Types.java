package Metatron.Core.Primitive.Util;

import Metatron.Core.uChumpEngine;
import Metatron.Core.Primitive.aType;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aList;

public class _Types {

	
	
	public static aDictionary<aType> ALL;
	
	
	public static aType register(aType... t)
	{
		if(ALL == null)
			ALL = new aDictionary<aType>();
			
			ALL.put("uCE", ">>=", t);
			return null;
	}
	
}
