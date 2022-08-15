package Metatron.X_;

import Metatron.Core.Primitive.Struct.aQueue;

public class ScriptNav {

	protected aQueue<Character> op;	

	protected long instruction = 0;
	protected long data = 0;

	public void step() {
		instruction ++;
	}
	
	

}
