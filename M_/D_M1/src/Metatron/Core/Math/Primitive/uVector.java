package Metatron.Core.Math.Primitive;

import Metatron.Core.Math.N_Operator;

public class uVector<N extends Number> extends aVector<N> {

	public N minVal;
	public N maxVal;

	public uVector(Number... numbers) {
		super(numbers);
		this.minVal = (N) N_Operator.resolveTo(Integer.MIN_VALUE, this.value);
		this.maxVal = (N) N_Operator.resolveTo(Integer.MAX_VALUE, this.value);
	}

	@Override
	public void append(Number n) {
		Number N = N_Operator.clamp(n, this.minVal, this.maxVal);
		super.append(N);
	}

	@Override
	public void insert(Integer at, Number member) {
		Number N = N_Operator.clamp(member, this.minVal, this.maxVal);
		super.insert(at, N);
	}
	
	
	@Override
	public void setAt(int at, Number to) {
		Number N = N_Operator.clamp(to, this.minVal, this.maxVal);
		super.setAt(at, to);

	}

}
