package Metatron.X_._BF;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Utils.StringUtils;
import Metatron.Core.Utils.iCypher;

public class BF_Script {

	protected static final String alphabet = "<>+-.,[]";

	private static final int MEM = 60000; // memory size
	private int memPointer = 0; // memory pointer
	private short loopPoints[]; // loop helper
	private char[] brainCode; // program code. Char array gains speed over String
	private final byte memory[] = new byte[MEM]; // 8bit memory cells
	private final InputStreamReader inputReader;
	private final PrintStream outputSt;

	public BF_Script(String prog) {
		this(prog, (BufferedInputStream) System.in, System.out);

	}

	public BF_Script(String prog, BufferedInputStream inputSt, PrintStream outputSt) {
		StringUtils.stripExcept(prog, alphabet);
		this.inputReader = new InputStreamReader(inputSt);
		this.outputSt = outputSt;
		this.brainCode = prog.toCharArray();
		optimize(this);
	}

	public void execute() {
		for (int pc = 0; pc < this.brainCode.length; pc++)
			switch (this.brainCode[pc]) {

			case '>' -> {
				this.memPointer++;
				this.memPointer = this.memPointer % MEM;
			}
			case '<' -> {
				this.memPointer--;
				if (this.memPointer > 0)
					this.memPointer = MEM;
			}
			case '+' -> this.memory[memPointer]++;
			case '-' -> this.memory[memPointer]--;
			case '.' -> this.outputMemCell();
			case ',' -> this.inputIntoMemCell();
			case '[' -> {
				if (this.memory[memPointer] == 0)
					pc = this.loopPoints[pc];
			}
			case ']' -> {
				if (this.memory[this.memPointer] == 0)
					break;
				pc = this.loopPoints[pc];
				break;
			}
			}
		;
	}

	public _Array<Byte> memOut() {
		_Array<Byte> out = new _Array<Byte>();
		for (int i = 0; i < this.memory.length; i++)
			out.append(this.memory[i]);

		return out;
	}

	private void outputMemCell() {
		outputSt.print((char) memory[memPointer]);
	}

	private void inputIntoMemCell() {
		try {
			memory[memPointer] = (byte) inputReader.read();
		} catch (IOException e) {
		}
	}

	protected static void optimize(BF_Script scr) {
		scr.optimizeBf();
		scr.optimizeLoops();
	}

	/**
	 * optimizeBf optimizes the code by shrinking repeated commands
	 * <p>
	 * First replaces the loops [-] and [+], with "Z" command: zero memory cell Then
	 * look for repeated commands like ++++++++ or <<<<<<<< and replace with single
	 * operations.
	 * <p>
	 * Single commands in the code will not be optimized. i.e. "->+<+->"" will not
	 * be touched Up to 40 repeated commands will be optimized. Example, brainfuck
	 * program :
	 * >++++[>++++++<-]>-[[<+++++>>+<-]>-]<<[<]>>>>--.<<<-.>>>-.<.<.>---.<<+++.>>>++.<<---.[>]<<.[-]
	 * <p>
	 * will be optimized to :
	 * >i4[>i6<-]>-[[<i5f2+<-]>-]b2[<]f4d2.b3-.f3-.<.<.>d3.b2i3.f3i2.b2d3.[>]b2.Z
	 */
	private void optimizeBf() {
		String bfCodeString = new String(brainCode); // String has the "replace" method

		/* introduce the Z command for these patterns (zero memory cell) */
		bfCodeString = bfCodeString.replace("[-]", "Z").replace("[+]", "Z");

//      Turns out this opt. does not have advantages
// 		bfCodeString = bfCodeString.replace("[->+<]", "a").replace("[->>+<<]", "A");

		StringBuilder codeSB = new StringBuilder(bfCodeString);

		// brainfuck commands >, <, +, - can be replaced by internal commands f, b, i, d
		char[][] commandSubstitutes = new char[][] { { '>', 'f' }, { '<', 'b' }, { '+', 'i' }, { '-', 'd' } };
		char bfCommand, optimizedCommand;

		for (int i = 0; i < codeSB.length(); i++) {
			int pos, counter, c;
			char ch = codeSB.charAt(i); // ch = current command

			for (c = 0; c < commandSubstitutes.length; c++) {
				if (ch == commandSubstitutes[c][0])
					break; // can this command be substituted ?
			}
			if (c == commandSubstitutes.length) // reached the end of commands list?
				continue; // Bf command not found, go to the next command

			bfCommand = commandSubstitutes[c][0]; // Bf command to be substituted
			optimizedCommand = commandSubstitutes[c][1]; // new/optimized command

			pos = i + 1;
			counter = 1; // occurrences of the same command

			while (pos < codeSB.length() && codeSB.charAt(pos) == bfCommand) {
				counter++; // look for repetitive BF commands
				pos++;
			}

			if (counter > 1) { // optimize for 2 or more repeated commands
				if (counter > 40) { // and up to 40 !
					codeSB.delete(i, i + 40); // delete 40 occurrences of the command
					counter = 40;
				} else {
					codeSB.delete(i, pos); // delete occurrences of the command
				}
				// Insert the optimized command and the number of repetitions in the code
				// Repetitions are represented by a character = '0' + repetitions
				// Why only 40 occurrences? chars 91 and 93 are: '[' and ']', these should never
				// go into the code

				codeSB.insert(i, String.valueOf(optimizedCommand) + (char) (counter + '0'));
				i++; // advance the pointer because we inserted 2 chars in the code
			}
		}
		brainCode = codeSB.toString().toCharArray();
	}

	/**
	 * Optimize the loop commands
	 * <p>
	 * Use a {@code short} array loopPoints as large as the code, to hold loop
	 * points If brainCode[X] is a loop command ("[" or "]"), then the respective
	 * loopPoints[X] will contain the start or end point of the loop
	 * <p>
	 * executeBf can do fast jumps using simple assignment instructions This MUST be
	 * called before executing the code
	 *
	 * @throws IllegalArgumentException when loops are not consistent
	 */
	private void optimizeLoops() throws IllegalArgumentException {
		loopPoints = new short[brainCode.length];
		short start;
		short end = (short) (brainCode.length - 1);
		int in = 0;

		for (char ch : brainCode) {
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
			if (brainCode[start] == '[') { // find start of loop up to length-1
				in = 0; // inner loop indicator
				for (short p = (short) (start + 1); p <= end; p++) { // find end of loop up to length
					if (brainCode[p] == ']') { // move forward to find the matching ']'
						if (in > 0)
							in--; // if we are a inner loop
						else { // position of ']' is assigned to matching '['
							loopPoints[start] = p;
							loopPoints[p] = start;
							break; // position of first '[' is assigned to matching ']'
						}
					} else if (brainCode[p] == '[')
						in++; // we are in a inner loop
				}
			}
		}
	}
}
