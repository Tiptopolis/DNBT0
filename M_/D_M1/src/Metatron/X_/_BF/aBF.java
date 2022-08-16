package Metatron.X_._BF;

import static Metatron.Core.M_Utils.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct._Map.Entry;
import Metatron.Core.Utils.StringUtils;

public class aBF {

	public static final String alphabet = "><+-.,[]";
	protected static final char end = '!';
	protected _Array<Integer> memory;
	protected char[] script;

	private int memPointer = 0;
	private int progCnt = 0;

	public short loopIndex[];

	private final InputStreamReader inputReader;
	private final PrintStream outputSt;

	public static aMap<_Map.Entry<Character, String>, iFunctor> Commands = new aMap<_Map.Entry<Character, String>, iFunctor>();

	static {
		iFunctor.Effect<aBF> F;

		F = (a) -> {
			a.memPointer = a.memPointer % a.memory.size();
			a.memPointer++;
			return a;
		};
		buildCommand('>', "++ptr;", F);

		F = (a) -> {
			if (a.memPointer == 0)
				a.memPointer = a.memory.size();
			a.memPointer--;
			return a;
		};
		buildCommand('<', "--ptr;", F);

		F = (a) -> {
			int i = a.get() + 1;
			a.set(i);
			return a;
		};
		buildCommand('+', "++*ptr;", F);

		F = (a) -> {
			int i = a.get() - 1;
			a.set(i);
			return a;
		};
		buildCommand('-', "--*ptr;", F);

		F = (a) -> {
			a.outputMemCell();
			return a;
		};
		buildCommand('.', "putchar(*ptr);", F);

		F = (a) -> {
			a.inputIntoMemCell();
			return a;
		};
		buildCommand(',', "*ptr = getchar();", F);

		F = (a) -> {
			if (a.get() == 0) {
				a.progCnt = a.loopIndex[a.progCnt];
			}
			return a;
		};
		buildCommand('[', "while (*ptr) {", F);

		F = (a) -> {
			if (a.get() == 0)
				return a;
			else {
				a.progCnt = a.loopIndex[a.progCnt];
				Log(" _" +a.loopIndex[a.progCnt]);
			}
			return a;

		};
		buildCommand(']', "}", F);
	}

	public aBF(String script) {
		String s = StringUtils.stripExcept(script, alphabet)+end;
		this.script = s.toCharArray();
		this.memory = new _Array<Integer>();
		for (int i = 0; i < 100; i++)
			memory.append(0);
		this.prepLoops();
		this.inputReader = new InputStreamReader(System.in);
		this.outputSt = System.out;
	}

	public static void buildCommand(char sym, String alt, iFunctor fn) {
		Entry<Character, String> E = new Entry<>(sym, alt);
		Commands.put(E, fn);
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
		outputSt.print((char) memory.get(memPointer).intValue());
	}

	private void inputIntoMemCell() {
		try {
			memory.set(memPointer, ((int) ((byte) inputReader.read())));
		} catch (IOException e) {
		}
	}

	//////
	//

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

		// Log(index +" -> "+this.script[index] + "("+this.memPointer + ":"+this.memory.get(index)+")"+this.script.length);
		if(index>this.script.length || this.script[index]==end)
			this.memPointer = -1;
				
		iFunctor f = this.getCommand(this.script[index]);
		if(f!= null)
			f.apply(this);
		
		//Log("  " + progCnt + " -> " + this.script[index] + "(" + this.memPointer + ":" + this.memory.get(index) + ") "+this.progCnt);

	}

	public int get() {
		return this.memory.get(this.memPointer);
	}

	public void set(int to) {
		this.memory.setAt(this.memPointer, to);
	}

	public void execute() {
		this.progCnt = 0;
		this.memPointer=0;
		
		while(this.memPointer!=-1)
		{
			this.parseAt(this.progCnt);
			this.progCnt++;
		}
	}

	public _Array<Integer> getMemory() {
		return this.memory;
	}
}
