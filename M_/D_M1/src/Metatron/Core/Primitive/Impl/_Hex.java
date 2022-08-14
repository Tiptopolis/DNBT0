package Metatron.Core.Primitive.Impl;

import Metatron.Core.Math.N_Operator;
import Metatron.Core.Math.Primitive.A_I.iNumeric;
import Metatron.Core.Primitive.A_I.iNode;
import Metatron.Core.Utils.StringUtils;
import Metatron.Core.Utils.iCypher;

public class _Hex extends Number implements iNode<Number>, CharSequence, iNumeric {

	// use as basis for module?

	Number value = Float.NaN;
	_Chord strVal;

	public _Hex(CharSequence s) {
		this.value = StringUtils.parseNum("" + s);

		if (!isHex("" + s))
			s = StringUtils.compile(StringUtils.backFill(16, "0", "" + s));
		this.strVal = new _Chord("" + s);
	}

	public _Hex(Number n) {

		this.value = n;
		
		String s = "" + n;
		if (!isHex(s))
			s = StringUtils.compile(StringUtils.backFill(16, "0", "" + s));
		this.strVal = new _Chord("" + s);
	}

	public static boolean isHex(String q) {
		if (q.length() % 16 != 0)
			return false;

		if (iCypher.containsOnlyThese(q, iCypher._HEX + iCypher._HEX.toLowerCase()))
			return true;

		return false;

	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return this.value.intValue();
	}

	@Override
	public long longValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float floatValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double doubleValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number numberValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char charAt(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(Number to) {
		// TODO Auto-generated method stub

	}

}
