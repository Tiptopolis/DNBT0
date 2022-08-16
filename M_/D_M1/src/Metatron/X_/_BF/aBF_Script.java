package Metatron.X_._BF;

import static Metatron.Core.M_Utils.*;

import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.A_I.iGroup;
import Metatron.Core.Primitive.A_I.iMap;
import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct._Map.Entry;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aQueue;
import Metatron.Core.Utils.iCypher;

public class aBF_Script implements iFunctor.Function<aBF_Script, _Array<Integer>> {

	protected String alphabet;
	public String script;

	public _Array<Integer> cells;// 32-Bit cells

	int dataPointer = 0;
	int cellValue = 0; // index

	public int cellMod = 16;
	protected boolean wrap = true;

	public static aMap<String, _Map.Entry<String, iFunctor>> CommandNames;
	public static aMap<String, iFunctor> CommandSymbols;

	static {
		CommandNames = new aMap<String, _Map.Entry<String, iFunctor>>();
		CommandSymbols = new aMap<String, iFunctor>();

		iFunctor.Effect<aBF_Script> incrementP = (a) -> {

			a.dataPointer++;
			a.doWrap();
			a.cellValue = a.cells.get(a.dataPointer);
			return a;
		};

		iFunctor.Effect<aBF_Script> decrementP = (a) -> {
			a.dataPointer--;
			a.doWrap();
			a.cellValue = a.cells.get(a.dataPointer);
			return a;
		};

		iFunctor.Effect<aBF_Script> incrementD = (a) -> {
			a.cellValue++;
			a.cells.setAt(a.dataPointer, a.cellValue);
			return a;
		};

		iFunctor.Effect<aBF_Script> decrementD = (a) -> {
			a.cellValue--;
			a.cells.setAt(a.dataPointer, a.cellValue);
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

		iFunctor.Effect<aBF_Script> loop = (a) -> {

			if (a.cellValue == 0) {
				int n = a.rMatch(a.dataPointer);
				Log(a.cellValue + " >> " + n);
				a.dataPointer = n + 1;
				a.cellValue = a.cells.get(a.dataPointer);
			} else
				incrementP.apply(a);

			return a;
		};

		iFunctor.Effect<aBF_Script> back = (a) -> {

			if (a.cellValue != 0) {
				int n = a.lMatch(a.dataPointer);
				Log(a.cellValue + " << " + n);
				a.dataPointer = n + 1;
				a.cellValue = a.cells.get(a.dataPointer);
			} else
				incrementP.apply(a);

			return a;
		};

		Entry<String, iFunctor> E;

		E = new _Map.Entry<String, iFunctor>(">", incrementP);
		CommandSymbols.put(E);
		CommandNames.put("IncrementPointer", E);

		E = new _Map.Entry<String, iFunctor>("<", decrementP);
		CommandSymbols.put(E);
		CommandNames.put("DecrementPointer", E);

		E = new _Map.Entry<String, iFunctor>("+", incrementD);
		CommandSymbols.put(E);
		CommandNames.put("IncrementCell", E);

		E = new _Map.Entry<String, iFunctor>("-", decrementD);
		CommandSymbols.put(E);
		CommandNames.put("DecrementCell", E);

		E = new _Map.Entry<String, iFunctor>(".", put);
		CommandSymbols.put(E);
		CommandNames.put("Put", E);

		E = new _Map.Entry<String, iFunctor>(",", get);
		CommandSymbols.put(E);
		CommandNames.put("Get", E);

		E = new _Map.Entry<String, iFunctor>("[", loop);
		CommandSymbols.put(E);
		CommandNames.put("Loop", E);

		E = new _Map.Entry<String, iFunctor>("]", back);
		CommandSymbols.put(E);
		CommandNames.put("Back", E);

		// <> iP
		// +- iD
		// . read
		// , write
		// [ jump
		// ] back
	}

	public aBF_Script() {
		this("><+-.,[]", 160);
	}

	public aBF_Script(String alphabet, int tapeLen) {
		this.alphabet = alphabet;
		this.cells = new _Array<Integer>();
		for (int i = 0; i < tapeLen; i++)
			cells.append(0);

	}

	public String parse() {
		String s = "";
		for (Integer i : cells)
			s += iCypher.decypher(this.alphabet, cells.get(i).intValue());
		return s;
	}

	protected void doWrap() {
		if (this.wrap) {
			if (this.dataPointer >= this.cells.size())
				this.dataPointer = this.dataPointer % this.cells.size();
			else if (!this.wrap)
				if (this.dataPointer >= this.cells.size())
					this.cells.append(0);
		}

		this.cellValue = this.cells.get(this.dataPointer);

	}

	public _Map.Entry<String, iFunctor> getCommand(String sym) {
		for (_Map.Entry<String, iFunctor> E : this.CommandSymbols)
			if (E.getKey().equals(sym) || E.getKey() == sym)
				return E;

		return null;
	}

	public _Map.Entry<String, iFunctor> getCommand(char... sym) {
		String s = "";
		for (char c : sym)
			s += "" + c;
		return getCommand(s);
	}

	@Override
	public _Array<Integer> apply(Object... t) {
		// String or Char[]
		this.script = "" + t[0];
		if (this.cells == null)
			this.cells = new _Array<Integer>();
		if (validScript(this.script))
			for (int i = 0; i < this.script.length(); i++) {

				String c = "" + this.script.charAt(i);

				/*
				 * for (_Map.Entry<String, iFunctor> E : this.CommandSymbols) if
				 * (E.getKey().equals(c) || E.getKey() == c) E.getValue().apply(this);
				 */
				Log("!>!>  " + this.getCommand(c));
				this.getCommand(c).getValue().apply(this);

				// CommandSymbols.get(c).apply(this);
			}

		else
			this.script = "";

		return this.cells;
	}

	public boolean validScript(String s) {
		boolean v = false;

		if (iCypher.containsOnlyThese(s, this.alphabet))
			v = true;

		return v;

	}

	public int rMatch(int index) {
		int c = 0;
		for (int i = index; i < this.script.length(); i++) {
			if (this.script.charAt(i) == '[')
				c--;
			if (this.script.charAt(i) == ']')
				c++;

			if (c == 1)
				return i;
		}
		return dataPointer + 1;
	}

	public int lMatch(int index) {
		int c = 0;
		for (int i = index; i > 0; i--) {
			if (this.script.charAt(i) == ']')
				c--;
			if (this.script.charAt(i) == '[')
				c++;

			if (c == 1)
				return i;
		}
		return dataPointer + 1;
	}

	public _Map.Entry<Integer, Integer> state() {
		return new _Map.Entry<Integer, Integer>(this.dataPointer, this.cellValue);
	}

	@Override
	public String toString() {

		return this.script;
	}

}
