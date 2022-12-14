package Metatron.X_._BF;

import static Metatron.Core._M.M_Utils.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct._Map.Entry;
import Metatron.Core.Utils.StringUtils;

public class aBF {

	// https://github.com/gpolic/brainfuck-interpreter/blob/master/src/com/gpolic/brainfuck/BrainInt.java
	// https://esolangs.org/wiki/Extended_Brainfuck

	public static final String alphabet = "><+-.,[]";
	protected static final char end = ';';
	protected _Array<Integer> memory; // tape lol
	protected char[] script;

	private int memPointer = 0;
	private int progCnt = 0;

	public short loopIndex[];

	private final InputStreamReader inputReader;
	private final PrintStream outputSt;

	public static aMap<_Map.Entry<Character, String>, iFunctor> Commands = new aMap<_Map.Entry<Character, String>, iFunctor>();

	private boolean firstRun = true;
	protected int postCalcSize = 0;
	public aVector lastMemory;
	public String lastOut = "";

	static {
		iFunctor.Effect<aBF> F;

		// replace C-item with a C-map & a basic command name
		// DEF( ">" | shift ptr right)
		F = (a) -> {
			a.memPointer = a.memPointer % a.memory.size();
			a.memPointer++;
			if (a.memPointer >= a.memory.size())
				a.memory.append(0);
			return a;
		};
		buildCommand('>', "++ptr;", F);

		// DEF( "<" | shift ptr left)
		F = (a) -> {
			if (a.memPointer == 0)
				a.memPointer = a.memory.size();
			a.memPointer--;
			if (a.memPointer < 0)
				a.memory.insert(0, 0);
			return a;
		};
		buildCommand('<', "--ptr;", F);

		// DEF( "+" | increment val@ptr)
		F = (a) -> {
			int i = a.get() + 1;
			a.set(i);
			return a;
		};
		buildCommand('+', "++*ptr;", F);

		// DEF( "-" | decrement val@ptr)
		F = (a) -> {
			int i = a.get() - 1;
			a.set(i);
			return a;
		};
		buildCommand('-', "--*ptr;", F);

		// DEF( "." | output <- val@ptr)
		F = (a) -> {
			a.outputMemCell();
			return a;
		};
		buildCommand('.', "putchar(*ptr);", F);

		// DEF( "." | input -> val@ptr)
		F = (a) -> {
			a.inputIntoMemCell();
			return a;
		};
		buildCommand(',', "*ptr = getchar();", F);

		// DEF( "[" | loop@ptr)
		F = (a) -> {
			if (a.get() == 0) {
				a.progCnt = a.loopIndex[a.progCnt];
			}
			return a;
		};
		buildCommand('[', "while (*ptr) {", F);

		// DEF( "]" | break@ptr)
		F = (a) -> {
			if (a.get() == 0)
				return a;
			else {
				a.progCnt = a.loopIndex[a.progCnt];
			}
			return a;

		};
		buildCommand(']', "}", F);
	}

	public aBF(String script) {
		String s = StringUtils.stripExcept(script, alphabet) + end;
		this.script = s.toCharArray();
		this.memory = new _Array<Integer>();
		int c = this.estSize();
		for (int i = 0; i < c; i++) // could could the balance of shift characters to determine size? dont ant
			memory.append(0); // array-growth during execution lol

		this.prepLoops();
		this.inputReader = new InputStreamReader(System.in);
		this.outputSt = System.out;
	}

	public static void buildCommand(char sym, String alt, iFunctor fn) {
		Entry<Character, String> E = new Entry<>(sym, alt);
		Commands.put(E, fn);
	}

	private int estSize() {

		// trys to precalculate size
		// doesnt take branches into account

		int c = 0;
		for (char C : this.script)
			switch (C) {
			case '>' -> c++;
			case '<' -> c--;
			}
		return c;
	}

	private void prepLoops() throws IllegalArgumentException {
		loopIndex = new short[script.length];
		short start;
		short end = (short) (script.length - 1);
		int in = 0;

		for (char ch : script) {
			if (ch == '[')
				in++;
			if (ch == ']')
				in--;
			if (in < 0)
				break; // negative "in" is a big problem!
		}

		if (in != 0) {
			throw new IllegalArgumentException("Invalid Loops. Check the source code");
		}

		for (start = 0; start < end; start++) { // now optimize
			if (script[start] == '[') { // find start of loop up to length-1
				in = 0; // inner loop indicator
				for (short p = (short) (start + 1); p <= end; p++) { // find end of loop up to length
					if (script[p] == ']') { // move forward to find the matching ']'
						if (in > 0)
							in--; // if we are a inner loop
						else { // position of ']' is assigned to matching '['
							loopIndex[start] = p;
							loopIndex[p] = start;
							break; // position of first '[' is assigned to matching ']'
						}
					} else if (script[p] == '[')
						in++; // we are in a inner loop
				}
			}
		}
	}

	private void outputMemCell() {

		// Log("<!>");

		outputSt.print((char) memory.get(memPointer).intValue());
		lastOut += ("" + (char) memory.get(memPointer).intValue());

	}

	private void inputIntoMemCell() {
		try {
			memory.set(memPointer, ((int) ((byte) inputReader.read())));
		} catch (IOException e) {
		}
	}

	protected static iFunctor getCommand(char sym) {
		for (Entry<_Map.Entry<Character, String>, iFunctor> E : Commands) {
			if (E.getKey().getKey() == sym)
				return E.getValue();
		}
		return null;
	}

	protected static iFunctor getCommand(String name) {
		if (name.length() == 1)
			return getCommand(name.charAt(0));
		else {
			for (Entry<_Map.Entry<Character, String>, iFunctor> E : Commands) {
				if (StringUtils.isFormOf(E.getKey().getValue(), name))
					return E.getValue();
			}
		}
		return null;
	}

	protected void parseAt(int index) {
		if (index > this.script.length || this.script[index] == end)
			this.memPointer = -1;

		if (this.firstRun) {
			char c = this.script[index];
			switch (c) {
			case '>' -> this.postCalcSize++;
			case '<' -> this.postCalcSize--;
			}
		}

		iFunctor f = this.getCommand(this.script[index]);
		if (f != null)
			f.apply(this);
	}

	public int get() {
		return this.memory.get(this.memPointer);
	}

	public void set(int to) {
		this.memory.setAt(this.memPointer, to);
	}

	public void execute() {
		this.progCnt = 0;
		this.memPointer = 0;

		while (this.memPointer != -1) {
			this.parseAt(this.progCnt);
			this.progCnt++;
		}

		if (this.firstRun)
			this.firstRun = false;

		this.end();
	}

	public _Array<Integer> getMemory() {
		return this.memory;
	}

	public void end() {
		if (this.lastMemory == null)
			this.lastMemory = new aVector().resize(this.memory.size());

		this.lastMemory.clear();
		for (int i = 0; i < this.postCalcSize; i++)
			this.lastMemory.append(this.memory.get(i));

		this.memory.clear();
		for (int i = 0; i < this.postCalcSize; i++)
			this.memory.append(0);
	}

	public String[] toHex() {
		String[] out = new String[this.lastMemory.size()];
		for (int i = 0; i < this.lastMemory.size(); i++) {
			out[i] = Integer.toHexString(this.lastMemory.get(i).intValue());
		}

		return out;
	}

	public String toHexString() {
		String H = "";
		String[] h = this.toHex();
		for (int i = 0; i < h.length; i++) {
			H += StringUtils.frontFill(6, "" + 0, h[i])[0];
			if (i != h.length - 1)
				H += ",";
		}

		return "[" + H + "]";
	}

	public String[] toBin() {
		String[] out = new String[this.lastMemory.size()];
		for (int i = 0; i < this.lastMemory.size(); i++) {
			out[i] = Integer.toBinaryString(this.lastMemory.get(i).intValue());
		}

		return out;
	}

	public String toBinString() {
		String B = "";
		String[] b = this.toBin();
		for (int i = 0; i < b.length; i++) {
			B += StringUtils.backFill(8, "" + 0, b[i])[0];
			if (i != b.length - 1)
				B += ",";
		}

		return "[" + B + "]";
	}

	public String[] toC() {
		String[] out = new String[this.script.length];
		for (int i = 0; i < this.script.length; i++)
			for (Entry<_Map.Entry<Character, String>, iFunctor> E : Commands) {
				if (E.getKey().getKey() == this.script[i])
					out[i] = E.getKey().getValue();
			}

		return out;
	}

	public String toC_String() {
		String out = "";
		for (int i = 0; i < this.script.length; i++)
			for (Entry<_Map.Entry<Character, String>, iFunctor> E : Commands) {
				if (E.getKey().getKey() == this.script[i])
					out += E.getKey().getValue() + "\n";
			}

		return out;
	}
}
