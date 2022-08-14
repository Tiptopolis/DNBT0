package Metatron.Core.Primitive.Struct;

import Metatron.Core.Primitive.aType;

public abstract class _Object {

	public aDictionary<aType> type; //<<obj,context>,label>
	
	public void ascribe(aType t)
	{
		if(this.type == null)
			this.type = new aDictionary<aType>();
			
			
		
	}
}
