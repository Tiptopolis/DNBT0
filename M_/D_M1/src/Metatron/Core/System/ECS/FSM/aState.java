package Metatron.Core.System.ECS.FSM;

import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.aType;
import Metatron.Core.Primitive.iFunctor;

public class aState extends aType implements iFunctor.Predicate {

	public aState(String typeName) {
		super(typeName);
	}

	@Override
	public boolean test(Object t) {
		// test an object to check if its in this particular state
		if (this.instances.contains(t))
			return true;
		
		
		
		return false;
	}

}
