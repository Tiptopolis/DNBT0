
package Metatron.Core.Math.Primitive;

import java.util.function.Supplier;

import Metatron.Core.Math.N_Operator;
import Metatron.Core.Math.aMaths;
import Metatron.Core.Math.Primitive.A_I.iNumeric;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.A_I.iNode;
import Metatron.Core.Primitive.A_I.iToken;
import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.Utils.StringUtils;

public class aNumber extends Number implements iNode<Number>, CharSequence, iNumeric {

	public Number value = Float.NaN;
	public Supplier<Number> Value = () -> this.value;

	protected aSet<aValue> constraints;

	public aNumber() {

	}

	public aNumber(Number n) {

		this.value = n;
	}

	public aNumber(CharSequence s) {
		this.value = StringUtils.parseNum("" + s);
	}

	public Number resolveToThis(Number n) {
		return N_Operator.resolveTo(n, this.value);
	}

	@Override
	public Number get() {
		return this.value;
	}

	@Override
	public void set(Number to) {
		this.value = to;
	}

	@Override
	public int intValue() {
		return this.numberValue().intValue();
	}

	@Override
	public long longValue() {
		return this.numberValue().longValue();
	}

	@Override
	public float floatValue() {
		return this.numberValue().floatValue();
	}

	@Override
	public double doubleValue() {
		return this.numberValue().doubleValue();
	}

	public Number numberValue() {
		return this.value;
	}

	public String stringValue() {
		return "" + this.numberValue();
	}

	@Override
	public int compareTo(Object other) {
		// Number n resolveTo

		if (other instanceof Number) {
			Number n = (Number) other;
			if (aMaths.isEqual(this.floatValue(), n.floatValue()))
				return 0;
			if (this.floatValue() > n.floatValue())
				return 1;
			if (this.floatValue() < n.floatValue())
				return -1;
		}

		return 0;
	}

	@Override
	public int length() {
		return this.value.toString().length();

	}

	@Override
	public char charAt(int index) {

		return this.value.toString().charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {

		return new aNumber(this.value.toString().subSequence(end, end));
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	public boolean isEqual(Number other, float epsilon) {
		return aMaths.isEqual(this.floatValue(), other.floatValue(), epsilon);
	}

	public Number revalidate() {
		for (aValue v : this.constraints) {

		}
		return this;
	}



}