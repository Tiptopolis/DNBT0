package Metatron.Core.Primitive.Impl;

import Metatron.Core.Math.N_Operator;
import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.iFunctor;

public abstract class _Constraint<T> extends aToken<aValue<T>> implements iFunctor.Effect<aValue<T>> {

	protected iFunctor.Effect<T> doFn;

	public _Constraint(iFunctor.Effect<T> F) {
		this.doFn = F;
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

		@Override
		public aValue<Number> apply(aValue<Number> t) {

			Number n = this.doFn.apply(t.get());
			t.set(n);
			return t;
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

		@Override
		public aValue<Number> apply(aValue<Number> t) {
			Number n = this.doFn.apply(t.get());
			t.set(n);
			return t;
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

		@Override
		public aValue<Number> apply(aValue<Number> t) {
			Number n = this.doFn.apply(t.get());
			t.set(n);
			return t;
		}

	}

}
