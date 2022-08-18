package Metatron.Core.Math.Util;

import Metatron.Core.Math.N_Operator;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.iFunctor;

public class _Maths {

	public static iFunctor.Effect<aValue<Number>> Max(Number max) {
		iFunctor.Effect<aValue<Number>> f = (a) -> {
			a.set(N_Operator.max(a.getValue(), max));
			return a;
		};
		return f;
	}

	public static iFunctor.Effect<aValue<Number>> Min(Number min) {
		iFunctor.Effect<aValue<Number>> f = (a) -> {
			a.set(N_Operator.min(a.getValue(), min));
			return a;
		};
		return f;
	}

	public static iFunctor.Effect<aValue<Number>> Range(Number min, Number max) {
		iFunctor.Effect<aValue<Number>> f = (a) -> {
			a.set(N_Operator.clamp(a.getValue(), min, max));
			return a;
		};
		return f;
	}

}
