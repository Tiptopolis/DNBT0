package Metatron.X_._BF;

import static Metatron.Core.M_Utils.*;


import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.A_I.iGroup;
import Metatron.Core.Primitive.A_I.iMap;
import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aQueue;
import Metatron.Core.Utils.iCypher;

public class aBF_Script implements iFunctor.Function<aBF_Script, _Array<Integer>> {

	protected String alpabet;
	public String script;

	public _Array<Integer> cells;// 32-Bit cells

	int dataPointer =0;
	int cellValue =0; // index

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
			a.cells.set(a.dataPointer, a.cellValue);
			return a;
		};

		iFunctor.Effect<aBF_Script> get = (a) -> {
			a.cellValue = a.cells.get(a.dataPointer);
			return a;
		};

		//not quite right, needs to jump to -matching-, not immediate next counter sub-iterator
		iFunctor.Effect<aBF_Script> loop = (a) -> {

			if (a.dataPointer == 0)
				for (int i = a.dataPointer; i < a.cells.size(); i++)
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
		this(iCypher._REX, 160);
	}

	public aBF_Script(String alphabet, int tapeLen) {
		this.alpabet = alphabet;
		this.cells = new _Array<Integer>();
		for (int i = 0; i < tapeLen; i++)
			cells.append(0);
		
	}

	public String parse() {
		String s = "";
		for (Integer i : cells)
			s += iCypher.decypher(this.alpabet, cells.get(i).intValue());
		return s;
	}

	protected void doWrap() {
		if (this.wrap)
			this.dataPointer = this.dataPointer % this.cells.size();
	}

	@Override
	public _Array<Integer> apply(Object... t) {
		// String or Char[]
		this.script = ""+t[0];
		if(this.cells==null)
			this.cells = new _Array<Integer>();
		if(validScript(this.script)) {
			for(int i =0; i < this.script.length(); i++)			
				this.CommandSymbols.get(""+this.script.charAt(i)).apply(this);
		}
		else
			this.script = "";
			
		
		return this.cells;
	}


	public boolean validScript(String s) {
		boolean v = false;

		if (iCypher.containsOnlyThese(s, this.alpabet))
			if (iCypher.containsAllThese(s, s))
				v = true;
		return v;

	}
	
	@Override
	public String toString()
	{
		
		
		
		return this.script;
	}

}
