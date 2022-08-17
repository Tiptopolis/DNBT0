package Metatron.Core.System.Script;

import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.Struct.aDictionary;

public class _CommandCodex extends aNode<aDictionary<iFunctor>>{

	//can put/get [SHARED] data
	
	
	
	public _CommandCodex(String label) {
		super(label, new aDictionary<iFunctor>());
	}
	
}
