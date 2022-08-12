package com.Rev.P1_Core.zCHEAT_CODEX;

import static com.Rev.P1_Core.AppUtils.*;

import com.Rev.P1_Core.Util.DefaultResources;

public class CaesarCipherDemo {

	public static String alphabetA = "abcdefghijklmnopqrstuvwxyz";
	public static String alphabetB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String alphabetC = "-_+-*/!,.?@#$%^&*(){}[]<>/\';:`~";
	public static String alphabetD = "1234567890";

	public static String cypher(String input, int offset) {
		String s = "";
		String I = input;

		int alphaRdx = alphabetA.length();
		for (int i = 0; i < I.length(); i++) {

			if (alphabetC.contains("" + I.charAt(i))) {
				s += "" + I.charAt(i);
			}
			if (alphabetD.contains("" + I.charAt(i))) {
				s += "" + I.charAt(i);
			}

			else if (alphabetA.contains("" + I.charAt(i))) {
				int map = alphabetA.indexOf(I.charAt(i));
				int mapTo = (map + offset) % alphaRdx;

				s += alphabetA.charAt(mapTo) + "";
			} else if (alphabetB.contains("" + I.charAt(i))) {
				int map = alphabetB.indexOf(I.charAt(i));
				int mapTo = (map + offset) % alphaRdx;

				s += alphabetB.charAt(mapTo) + "";
			}
		}

		return s;
	}

	public static String decypher(String input, int offset) {
		String s = "";
		String I = input;

		int alphaRdx = alphabetA.length();
		for (int i = 0; i < I.length(); i++) {

			if (alphabetC.contains("" + I.charAt(i))) {
				s += "" + I.charAt(i);
			}
			if (alphabetD.contains("" + I.charAt(i))) {
				s += "" + I.charAt(i);
			}

			else if (alphabetA.contains("" + I.charAt(i))) {
				int map = alphabetA.indexOf(I.charAt(i));
				int mapTo = (map - offset) % alphaRdx;

				s += alphabetA.charAt(mapTo) + "";
			} else if (alphabetB.contains("" + I.charAt(i))) {
				int map = alphabetB.indexOf(I.charAt(i));
				int mapTo = (map - offset) % alphaRdx;

				s += alphabetB.charAt(mapTo) + "";
			}
		}

		return s;
	}

	public static void main(String[] args) {
		String I = "CaeserSalad";
		int offset = 3;
		Log(I);
		String C = cypher(I, offset);
		Log(C);
		String D = decypher(C, offset);
		Log(D);
	}

}
