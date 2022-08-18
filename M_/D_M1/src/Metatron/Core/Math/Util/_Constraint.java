package Metatron.Core.Math.Util;

import static Metatron.Core._M.M_Utils.*;

import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.A_I.iDisposable;

public class _Constraint<T> extends aToken<Object> implements iFunctor.Effect<aValue<T>>, iDisposable {

	// needs to be a token you drop into aValue to automatically clamp its value

	//if _Costraint C.apply(aValue V), temporary modification
	// if aValue V.apply(_Constraint C), permanent modification
	
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
	//non-permanent, permanent constraints applied within the aValue itself
		T mfn = this.doFn.apply(t);
		return t;
	}

	public static _Constraint<Number> MIN(Number min) {
		iFunctor.Effect<aValue<Number>> f = _Maths.Min(min);

		return new _Constraint("MIN", f);
	}

	public static _Constraint MAX(Number max) {
		iFunctor.Effect<aValue<Number>> f = _Maths.Max(max);

		return new _Constraint("MAX", f);

	}

	public static _Constraint RANGE( Number min, Number max) {
		iFunctor.Effect<aValue<Number>> f = _Maths.Range(min, max);

		return new _Constraint("RANGE", f);
	}

	@Override
	public void init() {
		
	}
	
	@Override
	public void dispose()
	{
		
	}

}
