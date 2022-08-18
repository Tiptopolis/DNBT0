package Metatron.Core.Primitive.Impl;

import static Metatron.Core.M_Utils.*;

import Metatron.Core.Math.N_Operator;
import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.iFunctor;

public abstract class _Constraint<T> extends aToken<aValue<T>> implements iFunctor.Effect<aValue<T>> {

	// needs to be a token you drop into aValue to automatically clamp its value
	// put constraint in Value's data-map
	// constraint.apply(val) on every get/set
	protected _Constraint() {

	}

	public static _Constraint Min(aValue<Number> v, Number min) {
		return null;
	}

	public static _Constraint Max(aValue<Number> v, Number min) {
		return null;

	}

	public static _Constraint Range(aValue<Number> v, Number min, Number max) {
		return null;
	}

}
