package Metatron.X_;

import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aQueue;

public class ScriptNav {

	protected aQueue<String> ob;
	protected aMap<String, iFunctor> op;

	protected long instruction = 0;
	protected long data = 0;
	
	public ScriptNav()
	{
		this.ob = new aQueue<String>();
		this.op = new aMap<String,iFunctor>();
	}

	public void step() {
		instruction ++;
	}
	
	

}
