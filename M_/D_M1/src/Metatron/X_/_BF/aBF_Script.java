package Metatron.X_._BF;

import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aQueue;
import Metatron.Core.Utils.iCypher;

public class aBF_Script {
	
	
	protected String alpabet;

	aQueue<String> op;
	_Array<Integer> out;// 32-Bit cells

	long data;
	long instruction; // index

	public int cellMod = 16;
	protected boolean wrap = true;
	
	public static aMap<String,iFunctor> Commands;
	public static aMap<String, iFunctor> CommandMap;
	
	static {
		Commands = new aMap<String,iFunctor>();
		CommandMap = new aMap<String,iFunctor>();
		
		iFunctor.Effect<aBF_Script> incrementD = (a)-> {a.data++; return a;};
		iFunctor.Effect<aBF_Script> decrementD = (a)-> {a.data--; return a;};
		Commands.put("IncrementData",incrementD);
		Commands.put("DecrementData",decrementD);
	}

	public aBF_Script() {
		this(iCypher._REX,16000);
	}

	public aBF_Script(String alphabet, long tapeLen) {
		this.alpabet = alphabet;
		out = new _Array<Integer>();
		for (int i = 0; i < tapeLen; i++)
			out.append(0);
	}

	public String parse() {
		String s = "";
		for (Integer i : out)
			s += iCypher.decypher(this.alpabet, out.get(i).intValue());
		return s;
	}

}
