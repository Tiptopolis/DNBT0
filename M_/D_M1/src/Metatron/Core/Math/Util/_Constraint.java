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

	public static _Constraint<Number> Min(aValue<Number> v, Number min) {
		iFunctor.Effect<aValue<Number>> f = _Maths.Min(min);
		
		return null;
	}

	public static _Constraint Max(aValue<Number> v, Number min) {
		return null;

	}

	public static _Constraint Range(aValue<Number> v, Number min, Number max) {
		return null;
	}
	
	

}
