package Core._Math;

import Core._Math.A_I.N_Operator;

public class aNumber extends Number implements CharSequence {

	// protected static N_Operator Operator;
	// protected static N_Resolver Resolver;
	public String Label;
	public Number Value;

	@Override
	public int length() {
		return this.Value.toString().length();
	}

	@Override
	public char charAt(int index) {
		return this.Value.toString().charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return this.Value.toString().subSequence(start, end);
	}

	@Override
	public int intValue() {
		return this.Value.intValue();
	}

	@Override
	public long longValue() {
		return this.Value.longValue();
	}

	@Override
	public float floatValue() {
		return this.Value.floatValue();
	}

	@Override
	public double doubleValue() {
		return this.Value.doubleValue();
	}

	//////
	public Number add(Number b) {
		return N_Operator.add(this.Value, b);
	}

	public Number sub(Number b) {
		return N_Operator.sub(this.Value, b);
	}

	public Number mul(Number b) {
		return N_Operator.mul(this.Value, b);
	}

	public Number div(Number b) {
		return N_Operator.div(this.Value, b);

	}

	public Number pow(Number b) {
		return N_Operator.pow(this.Value, b);

	}

	public Number root(Number b) {
		return N_Operator.root(this.Value, b);
	}

	public Number mod(Number b) {
		return N_Operator.mod(this.Value, b);
	}

	public Number rem(Number b) {
		return N_Operator.rem(this.Value, b);
	}

	public Number min(Number b) {
		return N_Operator.min(this.Value, b);
	}

	public Number max(Number b) {
		return N_Operator.max(this.Value, b);
	}

	public Number abs() {
		return N_Operator.abs(this.Value);
	}

}