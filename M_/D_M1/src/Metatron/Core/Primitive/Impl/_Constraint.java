package Metatron.Core.Primitive.Impl;

import static Metatron.Core.M_Utils.*;


import Metatron.Core.Math.N_Operator;
import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.iFunctor;

public abstract class _Constraint<T> extends aToken<aValue<T>> implements iFunctor.Effect<aValue<T>> {

	protected iFunctor.Effect<T> doFn;

	public _Constraint(iFunctor.Effect<T> F) {
		this.doFn = F;
	}

	@Override
	public aValue<T> apply(aValue<T> t) {
		Log(" >>>>>>> " + t);
		T n = this.doFn.apply(t.get());
		t.set(n);
		Log(" <<<<<<<" + t);
		return t;
	}

	/////////

	public static aValue<Number> Min(aValue<Number> subject, Number min) {
		subject.apply(new Min(subject, N_Operator.resolveTo(min, subject.get())));
		return subject;
	}

	public static aValue<Number> Max(aValue<Number> subject, Number max) {
		subject.apply(new Max(subject, N_Operator.resolveTo(max, subject.get())));
		return subject;
	}

	public static aValue<Number> Range(aValue<Number> subject, Number min, Number max) {

		Number a = N_Operator.resolveTo(min, subject.get());
		Number b = N_Operator.resolveTo(max, subject.get());
		subject.apply(new Range(subject, a, b));
		return subject;
	}

	public static class Min extends _Constraint<Number> {

		Number val = Integer.MIN_VALUE;

		public Min(aValue<Number> subject, Number min) {
			super((a) -> {				
				return N_Operator.min(a, min);
			});
			this.apply(subject);
			subject.data.put("@MIN", this);
		}

	
	}

	public static class Max extends _Constraint<Number> {

		Number val = Integer.MAX_VALUE;

		public Max(aValue<Number> subject, Number max) {
			super((a) -> {
				return N_Operator.max(a, max);
			});
			this.apply(subject);
			subject.data.put("@MAX", this);
		}

	}

	public static class Range extends _Constraint<Number> {

		Number val = Integer.MAX_VALUE;

		public Range(aValue<Number> subject, Number min, Number max) {
			super((a) -> {
				return N_Operator.clamp(a, min, max);
			});
			this.apply(subject);
			subject.data.put("@RANGE", this);
		}

	}

}
