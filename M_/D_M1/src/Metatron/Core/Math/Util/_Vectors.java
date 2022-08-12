package Metatron.Core.Math.Util;

import Metatron.Core.Math.N_Operator;
import Metatron.Core.Math.aGeom;
import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Primitive.Util.aThingCounter;

public class _Vectors {

	
	
	public static aVector Axis(int dim) {

		// aVector D = new aVector(StringUtils.commaLen(dim));
		aVector D = new aVector().filled(dim, 0f);
		D.setAt(dim - 1, 1f);
		return D;
	}

	// theta in radians; 0->1 value :>: 0->2PI
	public static aVector Axis(int dim, float theta) {
		// i-time, t-time
		aVector axis = Axis(dim);
		axis.setAt(dim, theta);
		return axis;
	}

	public static aVector[] Axes(aVector AxisVect) {
		int s = AxisVect.size();
		Number type = AxisVect.value;
		aVector[] axes = new aVector[s];
		for (int i = 0; i < s; i++) {
			Number z = N_Operator.resolveTo(0, type);
			aVector a = new aVector().filled(s, z);
			a.setAt(i, N_Operator.resolveTo(1, type));
			axes[i] = a;
		}
		return axes;
	}

	public static aVector[] AxesTheta(aVector thetaAxisVect) {
		int s = thetaAxisVect.size();
		Number type = 0f;
		aVector[] axes = new aVector[s];
		for (int i = 0; i < s; i++) {
			Number z = N_Operator.resolveTo(0, type);
			aVector a = new aVector().filled(s, z);
			a.setAt(i, thetaAxisVect.get(i));
			axes[i] = a;
		}
		return axes;
	}

	public static aVector Range(Number min, Number max) {
		Number f = N_Operator.sub(max, min);

		Number F = N_Operator.add(f, 1);
		return Range(min, max, F);
	}

	public static aVector Range(Number min, Number max, Number F) {

		Number[] n = aGeom.interpolate(new aVector(min), new aVector(max), F);
		Number[] N = new Number[n.length];
		for (int i = 0; i < n.length; i++) {
			Number e = N_Operator.resolveTo(n[i], min);
			N[i] = e;
		}

		return new aVector(N) {
			@Override
			public String toString() {
				return "R" + this.size() + "[" + min + " -{> " + max + "]";
			}
		};
	}

	public static aVector rndAxis(int dim, int num) {
		// dim is the total magnitude of vectors
		// num; posative means that the # of 1s, negative means # of 0s
		// so dim @3 & num @1 would result in something like (1,0,0), (0,1,0), (0,0,1)
		// so dim @3 & num @2 would result in something like (1,0,1), (1,1,0), (0,1,1)

		aVector v = new aVector();
		boolean d = true;
		if (num < 0) {
			v.fill(dim, 1f);
			d = false;
		}
		if (num >= 0) {
			v.fill(dim, 0f);
		}

		int N = Math.abs(num);

		if (N >= dim)
			N = 1;

		// while (//count of 1s/0s) {
		// ThingCounter T;
		aThingCounter X = new aThingCounter(v);
		if (d) {
			// while (count of 1s){
			while (X.getCountOf(1) < num) {
				X.countThings(v);
			}
		} else {
			// while (count of 0s){
			while (X.getCountOf(0) < num) {
				X.countThings(v);
			}
		}

		return v;
	}
	
}
