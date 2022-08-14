package Metatron.Core.Primitive.Struct;

import Metatron.Core.Primitive.aType;

public abstract class _Object {
	public static aDictionary<String> TYPES = new aDictionary<String>();
	
	
	
	public aDictionary<aType> types; // <<obj,context>,label>
	
	static {
		if (TYPES == null)
			TYPES = new aDictionary<String>();

	}
	
	public _Object() {
		
	}

	public void ascribe(aType t) {
		if (this.types == null)
			this.types = new aDictionary<aType>();

	}
}
