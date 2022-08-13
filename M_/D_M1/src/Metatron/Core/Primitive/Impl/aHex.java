package Metatron.Core.Primitive.Impl;

import Metatron.Core.Math.Primitive.A_I.iNumeric;
import Metatron.Core.Primitive.A_I.iNode;
import Metatron.Core.Primitive.Struct._Chord;
import Metatron.Core.Utils.StringUtils;

public class aHex extends Number implements iNode<Number>, CharSequence, iNumeric{

	
	// use as basis for module?
	Number value = Float.NaN;
	_Chord strVal;

	public aHex(Number n) {

		this.value = n;
		strVal = new _Chord(""+n);
	}

	public aHex(CharSequence s) {
		this.value =StringUtils.parseNum("" + s);
		this.strVal = new _Chord(""+s);
	}
	
	public static boolean isHex(String q)
	{
		
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
