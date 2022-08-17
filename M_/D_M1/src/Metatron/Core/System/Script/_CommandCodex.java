package Metatron.Core.System.Script;

import static Metatron.Core.M_Utils.*;


import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct._Map.Entry;

public class _CommandCodex extends aNode<aDictionary<iFunctor>> {

	// can put/get [SHARED] data

	public _CommandCodex(String label) {
		super(label, new aDictionary<iFunctor>());
	}

	
	
	
	public iFunctor getCommand(Object sym) {
		for (Entry<_Map.Entry<Object, String>, iFunctor> E : this.get()) 			
			if (E.getKey().getKey() == sym || E.getKey().getKey().equals(sym) || sym.equals(E.getKey().getKey())) {
				Log(" x  " + E.getKey().getKey() + "  " + sym + " : ");
				return E.getValue();
			}
		
		return null;
	}
}
