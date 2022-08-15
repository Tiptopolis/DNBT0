package Metatron.X_._BF;

import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.A_I.iEntry;
import Metatron.Core.Primitive.Struct.aQueue;
import Metatron.X_.ScriptNav;

public class Nav_BF {

	

	
	public Nav_BF(aBF_Script s)
	{
		
	}
	

	
	public enum BF_OP implements iEntry<String,iFunctor>
	{
		Increment_D(">",(a)->{return null;});

		String sym = "";
		iFunctor fn;
		
		private BF_OP(String symbol, iFunctor.Function fn)
		{
			
		}
		
		
		@Override
		public String getKey() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public iFunctor getValue() {
			
			
			return null;
		}
		
	}
	
}
