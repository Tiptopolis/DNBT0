package Metatron.X_._BF;

import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aQueue;
import Metatron.Core.Utils.iCypher;

public class aBF_Script implements iFunctor.Function<aBF_Script,_Array<Integer>>{

	protected String alpabet;
	String script;

	aQueue<String> op;	
	_Array<Integer> data;// 32-Bit cells

	int dataPointer;
	int cellValue; // index

	public int cellMod = 16;
	protected boolean wrap = true;

	public static aMap<String, iFunctor> CommandNames;
	public static aMap<String, iFunctor> CommandSymbols;

	static {
		CommandNames = new aMap<String, iFunctor>();
		CommandSymbols = new aMap<String, iFunctor>();

		iFunctor.Effect<aBF_Script> incrementD = (a) -> {
			a.dataPointer++;
			a.doWrap();
			return a;
		};

		iFunctor.Effect<aBF_Script> decrementD = (a) -> {
			a.dataPointer--;
			a.doWrap();
			return a;
		};

		iFunctor.Effect<aBF_Script> incrementP = (a) -> {
			a.cellValue++;
			return a;
		};

		iFunctor.Effect<aBF_Script> decrementP = (a) -> {
			a.cellValue--;
			return a;
		};

		iFunctor.Effect<aBF_Script> put = (a) -> {
			a.data.set(a.dataPointer, a.cellValue);
			return a;
		};

		iFunctor.Effect<aBF_Script> get = (a) -> {
			a.cellValue = a.data.get(a.dataPointer);
			return a;
		};

		iFunctor.Effect<aBF_Script> loop = (a) -> {

			if (a.dataPointer == 0)
				for (int i = a.dataPointer; i < a.data.size(); i++)
					if (a.script.charAt(i) == ']') {
						a.dataPointer = i + 1;
						a.doWrap();
						return a;
					}

			return a;
		};

		iFunctor.Effect<aBF_Script> back = (a) -> {

			if (a.dataPointer == 0)
				for (int i = 0; i < a.dataPointer; i++)
					if (a.script.charAt(i) == '[') {
						a.dataPointer = i + 1;
						a.doWrap();
						return a;
					}

			return a;
		};

		CommandNames.put("IncrementPointer", incrementP);
		CommandSymbols.put(">", incrementP);
		CommandNames.put("DecrementPointer", decrementP);
		CommandSymbols.put("<", decrementP);

		CommandNames.put("IncrementCell", incrementD);
		CommandSymbols.put("+", incrementD);
		CommandNames.put("DecrementCell", decrementD);
		CommandSymbols.put("-", decrementD);

		CommandNames.put("Put", put);
		CommandSymbols.put(".", put);
		CommandNames.put("Get", get);
		CommandSymbols.put(",", get);

		CommandNames.put("Loop", loop);
		CommandSymbols.put("[", loop);
		CommandNames.put("]", back);
		CommandSymbols.put("Back", back);

		// <> iP
		// +- iD
		// . read
		// , write
		// [ jump
		// ] back
	}

	public aBF_Script() {
		this(iCypher._REX, 16000);
	}

	public aBF_Script(String alphabet, long tapeLen) {
		this.alpabet = alphabet;
		data = new _Array<Integer>();
		for (int i = 0; i < tapeLen; i++)
			data.append(0);
	}

	public String parse() {
		String s = "";
		for (Integer i : data)
			s += iCypher.decypher(this.alpabet, data.get(i).intValue());
		return s;
	}

	protected void doWrap() {
		if (this.wrap)
			this.dataPointer = this.dataPointer % this.data.size();
	}

	


	@Override
	public _Array<Integer> apply(Object... t) {
    //String or Char[]

		
		return this.data;	
	}
	
}
