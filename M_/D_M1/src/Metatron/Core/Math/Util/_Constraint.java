package Metatron.Core.Math.Util;

import static Metatron.Core.M_Utils.*;

import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.iFunctor;

public class _Constraint<T> extends aToken<Object> implements iFunctor.Effect<aValue<T>> {

	// needs to be a token you drop into aValue to automatically clamp its value
	// put constraint in Value's data-map
	// constraint.apply(val) on every get/set
	public iFunctor<aValue<T>> doFn;

	protected _Constraint(iFunctor<aValue<T>> Fn) {
		this.type = _Constraint.class;
		this.value = "aConstraintFn";
		this.doFn = Fn;
	}

	public _Constraint(String n, iFunctor<aValue<T>> Fn) {
		this.type = _Constraint.class;
		this.value = n;
		this.doFn = Fn;
	}

	public aValue<T> apply(aValue<T> t) {
		T mfn = this.doFn.apply(t);
		return t;
	}

	public static _Constraint<Number> MIN(aValue<Number> v, Number min) {
		iFunctor.Effect<aValue<Number>> f = _Maths.Min(min);

		return new _Constraint("MIN", f);
	}

	public static _Constraint MAX(aValue<Number> v, Number max) {
		iFunctor.Effect<aValue<Number>> f = _Maths.Max(max);

		return new _Constraint("MAX", f);

	}

	public static _Constraint RANGE(aValue<Number> v, Number min, Number max) {
		iFunctor.Effect<aValue<Number>> f = _Maths.Range(min, max);

		return new _Constraint("RANGE", f);
	}

}
