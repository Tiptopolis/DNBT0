package Metatron.Core.Primitive.Struct;

import Metatron.Core.Math.aMaths;
import Metatron.Core.Utils.StringUtils;

public class _Chord implements CharSequence {

	private _Array<String> inner;
	private boolean del = true;
	private String delimiter = " ";

	// has all from "alphabet"
	Number maxLength = Float.POSITIVE_INFINITY;
	Number minLength = Float.NEGATIVE_INFINITY;
	String nullDef = "";
	public _Chord() {
		this.inner = new _Array<String>(" ");
		this.delimiter = "";
		this.del = false;
	}

	public _Chord(String... s) {
		this(true, s);
	}

	public _Chord(char del, String... s) {
		this(true, "" + del, s);

	}

	public _Chord(boolean ssv, String... s) {
		this(ssv, " ", s);
	}

	public _Chord(boolean csv, String del, String... s) {
		this.inner = new _Array<String>(s);
		this.delimiter = del;
		this.del = csv;
	}

	@Override
	public int length() {
		return this.toString().length();
	}

	@Override
	public char charAt(int index) {
		return this.toString().charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return this.toString().subSequence(start, end);
	}

	public String[] series(int start, int end) {
		end = aMaths.min(inner.size(), end).intValue();
		start = aMaths.min(inner.size(), start).intValue() - 1;

		String[] out = new String[end - start];
		for (int i = 0, j = start; i < out.length; i++, j++)
			out[i] = this.inner.get(j);
		return out;
	}

	public String[] parse() {
		int s = this.inner.size();
		String[] out = new String[s];
		for (int i = 0; i < s; i++)
			out[i] = this.inner.get(i);
		return out;
	}

	public String[] parseAgainst(String... in) {
		_Array<String> s = new _Array<String>();

		for (String a : in) {
			Object[] O = StringUtils.split(a, this.delimiter);
			for (Object o : O)
				s.append("" + o);
		}

		String[] out = new String[s.size()];
		for (int i = 0; i < s.size(); i++)
			out[i] = s.get(i);

		return out;
	}

	@Override
	public String toString() {
		String out = "";
		for (String s : this.inner) {
			out += s;
			if (this.del)
				out += this.delimiter;
		}
		return out;
	}

}
