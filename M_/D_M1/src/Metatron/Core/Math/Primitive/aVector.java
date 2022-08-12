
package Metatron.Core.Math.Primitive;

import static Metatron.Core.Math.N_Operator.*;
import static Metatron.Core.M_Utils.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import Metatron.Core.M_Utils;
import Metatron.Core.Math.N_Operator;
import Metatron.Core.Math.V_Operator;
import Metatron.Core.Math.aGeom;
import Metatron.Core.Math.aMaths;
import Metatron.Core.Math.Primitive.fromGdx.Gfx.Color;
import Metatron.Core.Math.Primitive.fromGdx.Math.Quaternion;
import Metatron.Core.Math.Primitive.fromGdx.Math.Vector2;
import Metatron.Core.Math.Primitive.fromGdx.Math.Vector3;
import Metatron.Core.Math.Util.aVectorUtils;
import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.A_I.iGroup;
import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.Primitive.Util.aThingCounter;
import Metatron.Core.Utils.StringUtils;

public class aVector<N extends Number> extends aNumber implements Iterable<Number>, iCollection<Number> {

	public _Array<Number> elements;
	public Function<Number, Number> Resolve = (Number n) -> {
		return N_Operator.resolveTo(n, this.value);
	};

	public aVector() {
		this(Float.NaN);
	}

	public aVector(Number type) {
		super(resolve(type));
		this.elements = new aList();
		elements.append(type);
	}

	public aVector(aVector from) {
		super(resolve(from.value));
		this.elements = new aList();
		for (int i = 0; i < from.size(); i++) {
			this.append(from.get(i));
		}
	}

	public aVector(iCollection<Number> from) {
		super(resolve(from.get(0)));
		this.elements = new aList();
		for (int i = 0; i < from.size(); i++) {
			this.append(from.get(i));
		}
	}

	public aVector(Number... values) {
		this();

		for (int i = 0; i < values.length; i++) {
			if (!(values[i] instanceof aVector))
				this.append(resolveTo(values[i], values[0]));
			else
				this.append(resolveTo(((aVector) values[i]).get(0), ((aVector) values[0]).value));

		}
	}

	public aVector(float[] values) {
		super(Float.NaN);
		this.value = 0f;
		this.elements = new aList<Number>();

		for (int i = 0; i < values.length; i++) {
			this.setAt(i, values[i]);
		}
	}

	public aVector(String csv) {
		this(StringUtils.resolveCsvToNumbers(csv));
	}

	public aVector(Vector2 vec2) {
		this(vec2.x, vec2.y);
	}

	public aVector(Vector3 vec3) {
		this(vec3.x, vec3.y, vec3.z);
	}

	public aVector(Quaternion quat) {
		this(quat.x, quat.y, quat.z, quat.w);
	}

	public static aVector[] fromCollections(iCollection<Number>... C) {
		aVector[] V = new aVector[C.length];
		for (int i = 0; i < C.length; i++)
			V[i] = new aVector(C[i]);
		return V;
	}

	////////
	@Override
	public void appendAll(Number... entries) {
		this.elements.appendAll(entries);
	}

	public void append(aVector entry) {
		this.appendAll(entry.getComponentData());
	}

	@Override
	public void append(Number entry) {
		if (this.value.equals(Float.NaN) || this.elements.isEmpty()) {
			this.value = entry;
			this.setAt(0, entry);
		} else
			this.elements.append(entry);
	}

	@Override
	public Number get() {
		return this;
	}

	public Number getDefault(int index, Number def) {
		Number e = this.get(index);
		if (e == null)
			return resolveTo(def, this.get());
		else
			return e;
	}

	@Override
	public Number get(Integer index) {
		return this.elements.get(index);
	}

	@Override
	public void setAt(int at, Number to) {
		this.elements.setAt(at, to);

	}

	public void setAll(Number val) {
		for (int i = 0; i < this.size(); i++) {
			Number N = N_Operator.resolveTo(val, this.value);
			this.setAt(i, N);
		}

	}

	// lol
	@Override
	public Integer indexOf(Object member) {
		return this.elements.indexOf(member);
	}

	@Override
	public aVector filled(int to, Number val) {
		this.clear();
		for (int i = 0; i < to; i++)
			this.append(val);

		return this;
	}

	public aVector with(Number... entries) {
		this.appendAll(entries);
		return this;
	}

	@Override
	public void set(Integer i, Number o) {
		this.elements.set(i, resolveTo(o, this.value));

	}

	@Override
	public boolean contains(Number entry) {
		return this.elements.contains(entry);
	}

	@Override
	public void insert(Integer at, Number member) {
		this.elements.insert(at, member);

	}

	@Override
	public void remove(Integer at) {
		this.elements.remove(at);

	}

	@Override
	public void swap(int i, int j) {
		this.elements.swap(i, j);

	}

	@Override
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

	@Override
	public int size() {
		return this.elements.size();
	}

	@Override
	public aVector resize(int to) {

		if (to < this.size())
			return this.truncate(to);
		else {
			int diff = to - this.size();

			for (int i = 0; i < diff; i++)
				this.append(N_Operator.resolveTo(0, this.value));
			return this;
		}

	}

	public aVector truncate(int to) {
		for (int i = this.size() - 1; i > to; i--)
			this.remove(i);
		return this;
	}

	public aSet<aVector> splitEvery(int n) {
		return splitEvery(n, true);
	}

	public aSet<aVector> splitEvery(int n, boolean square) {
		aSet<aVector> r = new aSet<aVector>();
		aVector v = new aVector();
		for (int i = 0; i < this.size(); i++) {
			v.append(this.get(i));
			if ((i + 1) % n == 0) {
				r.append(v);
				v = new aVector();
			}
			if (i == this.size() - 1)
				r.append(v);
		}
		if (square)
			r.get(r.size() - 1).resize(n);

		return r;
	}

	public aVector truncate(int from, int to) {
		aVector ref = this.cpy();
		this.clear();
		for (int i = from; i < to; i++)
			this.append(ref.get(i));

		return this;
	}

	@Override
	public void clear() {
		this.elements.clear();
		this.value = Float.NaN;
	}

	@Override
	public aVector cpy() {
		aVector out = new aVector();
		for (int i = 0; i < this.size(); i++)
			out.append(this.get(i));
		return out;
	}
	
	@Override
	public aVector sort()
	{
		aVectorUtils.sortMagnitudeAscending(this);
		return this;
	}

	public int lMag() {
		int c = 0;
		for (int i = 0; i < this.size(); i++)
			if (isEqual(this.get(i), 0))
				c++;
			else
				return c;
		return c;
	}

	public int rMag() {
		int c = 0;
		for (int i = this.size(); i > 0; i--)
			if (isEqual(this.get(i), 0))
				c++;
			else
				return c;
		return c;
	}

	@Override
	public Iterator<Number> iterator() {
		return this.elements.iterator();
	}

	@Override
	public Number[] getComponentData() {
		return this.elements.getComponentData();
	}

	@Override
	public Number[] toArray() {
		return this.elements.getComponentData();
	}

	public aList<Number> toList() {
		aList<Number> l = new aList<Number>();
		for (int i = 0; i < this.size(); i++)
			l.append(this.get(i));
		return l;
	}

	public String toElementString() {
		String s = "";
		for (int i = 0; i < this.size(); i++) {
			s += this.elements.get(i);
			if (i != (this.size() - 1))
				s += ",";
		}
		return s;
	}

	public String toValueString() {
		return "(" + this.toElementString() + ")";
	}

	public String toJoinedString()
	{
		String s = "";
		for(Number n : this)
			s+=n;
		return s;
	}
	
	@Override
	public String toString() {
		String s = "V" + this.size() + StringUtils.numberFormat(this.value) + this.toValueString();

		return s;
	}

	public boolean isEqual(Number other) {
		return this.isEqual(other, aMaths.FLOAT_ROUNDING_ERROR);
	}

	public boolean isEqual(Number other, float epsilon) {
		int s = 0;
		if (other instanceof aVector) {
			aVector v = (aVector) other;
			s = Math.min(this.size(), v.size());
			for (int i = 0; i < s; i++)
				if (!aMaths.isEqual(this.get(i).floatValue(), v.get(i).floatValue(), epsilon))
					return false;

			return true;
		} else
			for (int i = 0; i < s; i++)
				if (!aMaths.isEqual(this.get(i).floatValue(), other.floatValue(), epsilon))
					return false;

		return true;
	}

	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (other instanceof Number)
			return this.isEqual((Number) other);

		return false;
	}

	private static void _OPERATORS_() {

	}

	public aVector idt() {
		this.setAll(N_Operator.resolveTo(1, this.value));

		return this;
	}

	public aVector add(aVector other) {
		return V_Operator.add(this, other);
	}

	public aVector add(Number other) {
		return V_Operator.add(this, other);
	}

	public aVector sub(aVector other) {
		return V_Operator.sub(this, other);
	}

	public aVector sub(Number other) {
		return V_Operator.sub(this, other);
	}

	public aVector mul(aVector other) {
		return V_Operator.mul(this, other);
	}

	public aVector mul(Number other) {
		return V_Operator.mul(this, other);
	}

	public aVector scl(Number other) {
		return this.mul(other);
	}

	public aVector div(aVector other) {
		return V_Operator.div(this, other);
	}

	public aVector div(Number other) {
		return V_Operator.div(this, other);
	}

	public aVector pow(aVector p) {
		return V_Operator.pow(this, p);
	}

	public aVector pow(Number p) {
		return V_Operator.pow(this, p);
	}

	public aVector root(Number rt) {
		return V_Operator.root(this, rt);
	}

	public aVector root(aVector p) {
		return V_Operator.root(this, p);
	}

	public aVector max(Number n) {
		return V_Operator.max(this, n);
	}

	public aVector max(aVector v) {
		return V_Operator.max(this, v);
	}

	public aVector min(Number n) {
		return V_Operator.min(this, n);
	}

	public aVector min(aVector v) {
		return V_Operator.min(this, v);
	}

	public aVector floor() {
		for (int i = 0; i < this.size(); i++) {
			Number N = this.get(i);
			int I = N.intValue();
			this.set(i, N);
		}
		return this;
	}

	public aVector floor(Number n) {
		return V_Operator.floor(this, n);
	}

	public aVector ceil(Number n) {
		return V_Operator.ceil(this, n);
	}

	public aVector mod(Number m) {
		return V_Operator.mod(this, m);
	}

	public aVector mod(aVector m) {
		return V_Operator.mod(this, m);
	}

	public Number dot(Number a) {
		return V_Operator.dot(this, a);
	}

	public aVector crs2d(aVector a) {
		return V_Operator.crs2d(this, a);
	}

	public aVector ncrs2d(aVector other) {
		return V_Operator.ncrs2d(this, other);
	}

	public aVector crs(aVector a) {
		return V_Operator.crs(this, a);
	}

	public aVector ncrs(aVector a) {
		return V_Operator.ncrs(this, a);
	}

	public static aVector dst(aVector from, aVector to) {
		return from.dst(to);
	}

	public aVector dst(aVector to) {
		return this.cpy().sub(to.cpy()).scl(-1);
	}

	public float dstLen(aVector to) {
		return this.dst(to).len();
	}

	public aVector dir(aVector to) {
		return this.cpy().sub(to.cpy()).nor().scl(-1);
	}

	// scalar float lol
	public static aVector lerp(aVector from, aVector to, float progress) {
		int size = Math.min(from.size(), to.size());

		aVector value = new aVector(size);
		for (int i = 0; i < size; i++) {
			float f = from.get(i).floatValue();
			float t = to.get(i).floatValue();
			float v = aMaths.lerp(f, t, progress).floatValue();
			value.setAt(i, v);
		}

		return value;
	}

	public aVector lerp(aVector other, float delta) {
		return lerp(this, other, delta);
	}

	public static aVector ilerp(aVector from, aVector to, float delta) {
		int size = Math.min(from.size(), to.size());

		aVector value = new aVector(size);
		for (int i = 0; i < size; i++) {
			float f = from.get(i).floatValue();
			float t = to.get(i).floatValue();
			float v = aMaths.inverseLerp(f, t, delta).floatValue();
			value.setAt(i, v);
		}

		return value;
	}

	public aVector ilerp(aVector other, float delta) {
		return ilerp(this, other, delta);
	}

	public static aVector interp(aVector from, aVector to, aVector delta) {
		int size = N_Operator.minOf(from.size(), to.size(), delta.size()).intValue();
		aVector value = new aVector(size);
		for (int i = 0; i < size; i++) {
			float f = from.get(i).floatValue();
			float t = to.get(i).floatValue();
			float d = delta.get(i).floatValue();
			float v = aMaths.lerp(f, t, d).floatValue();
			value.setAt(i, v);
		}

		return value;

	}

	public aVector clamp(aVector min, aVector max) {
		for (int i = 0; i < this.size() && i < min.size() && i < max.size(); i++) {
			Number f = N_Operator.clamp(this.get(i), min.get(i), max.get(i));
			this.setAt(i, f);
		}
		return this;
	}

	public aVector slerp(final aVector target, float alpha) {
		final float dot = this.dot(target).floatValue();
		// If the inputs are too close for comfort, simply linearly interpolate.
		if (dot > 0.9995 || dot < -0.9995)
			return this.lerp(target, alpha);

		// theta0 = angle between input vectors
		final float theta0 = (float) Math.acos(dot);
		// theta = angle between this vector and result
		final float theta = theta0 * alpha;

		final float st = (float) Math.sin(theta);

		aVector t = target.cpy().sub(this.cpy().mul(dot));
		float l2 = t.cpy().mag().floatValue();
		final float dl = st * ((l2 < 0.0001f) ? 1f : 1f / (float) Math.sqrt(l2));

		aVector L = t.mul(dl);
		return this.scl((float) Math.cos(theta)).add(L).nor();
	}

	public Number sign() {
		return (Math.signum(this.sum().doubleValue()));
	}

	public aVector toSign(Number a) {
		return V_Operator.toSign(this, a);
	}

	public aVector abs() {
		for (int i = 0; i < this.size(); i++) {
			Number n = this.get(i);
			this.setAt(i, N_Operator.abs(n));
		}

		return this;
	}

	public aVector neg() {
		for (int i = 0; i < this.size(); i++) {
			Number n = this.get(i);
			this.setAt(i, N_Operator.neg(n));
		}

		return this;
	}

	// euclidean length
	public float len() {
		// return (float)Math.sqrt(x * x + y * y + z * z);
		return (float) Math.sqrt(this.len2());
	}

	float len(int n) {
		return (float) N_Operator.root(this.lenN(n), n);
	}

	public float lenN(Number pow) {
		aVector a = this.cpy().pow(pow);

		return a.sum().floatValue();
	}

	public float len2() {

		aVector a = this.cpy().pow(2);

		return a.sum().floatValue();
	}

	public aVector inv() {
		aVector tmp = this.cpy();
		for (int i = tmp.size() - 1, j = 0; i >= 0; i--, j++)
			this.setAt(j, tmp.get(i));

		return this;
	}

	public aVector fract() {
		return V_Operator.fract(this);
	}

	public aVector nor() {
		float len2 = this.len2();
		if (aMaths.isEqual(len2, 0f) || aMaths.isEqual(len2, 1f))
			return this;
		return this.mul(1f / (float) Math.sqrt(len2));
	}

	private void ___ROTATION___() {

	}

	

	public aVector setToRnd(Number min, Number max) {
		int s = this.size();
		if (this.value instanceof Integer || this.value instanceof Byte) {
			for (int i = 0; i < this.size(); i++) {
				this.setAt(i, M_Utils.rndInt(min.intValue(), max.intValue()));
			}
		}
		if (this.value instanceof Float || this.value instanceof Double) {
			for (int i = 0; i < this.size(); i++) {
				this.setAt(i, M_Utils.rndFlt(min.floatValue(), max.floatValue()));
			}
		}
		return this;
	}

	public aVector setToRndDir() {
		return this.setToRnd(0f, 1f).nor();
	}

	public static aVector rndDir() {
		return rndDir(3);
	}

	public static aVector rndDir(int mag) {
		aVector v = new aVector().filled(mag, 0f);
		v.setToRnd(0f, 1f);
		return v;
	}

	public aVector sin() {
		aVector out = new aVector().filled(this.size(), 0);
		for (int i = 0; i < this.size(); i++)
			out.setAt(i, Math.sin(this.get(i).floatValue()));
		return out;
	}

	public aVector cos() {
		aVector out = new aVector().filled(this.size(), 0);
		for (int i = 0; i < this.size(); i++)
			out.setAt(i, Math.cos(this.get(i).floatValue()));
		return out;
	}

	public aVector tan() {
		aVector out = new aVector().filled(this.size(), 0);
		for (int i = 0; i < this.size(); i++)
			out.setAt(i, Math.tan(this.get(i).floatValue()));
		return out;
	}

	public aVector setFromAxis(aVector axis, float angle) {
		return this.setFromAxisRad(axis, angle * aMaths.degreesToRadians);
	}

	// only works in std2D (z-axis)
	public aVector setFromAxisRad(aVector axis, float radians) {
		// float d = Vector3.len(x, y, z);
		// if (d == 0f) return idt();
		// d = 1f / d;
		// float l_ang = radians < 0 ? MathUtils.PI2 - (-radians % MathUtils.PI2) :
		// radians % MathUtils.PI2;
		// float l_sin = (float)Math.sin(l_ang / 2);
		// float l_cos = (float)Math.cos(l_ang / 2);
		// return this.set(d * x * l_sin, d * y * l_sin, d * z * l_sin, l_cos).nor();
		axis = axis.toType(0f);
		float d = axis.len();
		if (d == 0f)
			return idt();
		d = 1f / d;
		float l_ang = radians < 0 ? aMaths.PI2 - (-radians % aMaths.PI2) : radians % aMaths.PI2;
		float l_sin = (float) Math.sin(l_ang / 2f);
		float l_cos = (float) Math.cos(l_ang / 2f);

		aVector out = axis.cpy().mul(l_sin).mul(d);
		out.append(l_cos);
		// Log("** " + out.cpy().nor());
		this.set(out.nor());
		return this;

	}

	// aVector rN = mid.sub(dir.crs(D).nor().scl(MathUtils.PI));//rotation formula

	public aVector rotate(aVector axis, float theta) {

		// need to account for trimmed off parts
		Vector3 res = this.toVec3().rotate(axis.toVec3(), theta);
		aVector Res = new aVector(res);

		if (this.size() > Res.size()) {
			aVector r = this.cpy();
			for (int i = Res.size(); i < r.size(); i++)
				Res.append(r.get(i));
			this.set(Res);
			return this;
		} else {

			this.set(Res);
			Res.clear();
			return this;
		}
	}

	private static void _STATS_() {

	}

	public Number sum() {
		Number res = N_Operator.resolveTo(0, this.value);
		for (Number n : this)
			res = N_Operator.add(res, n);

		return res;
	}

	public float crsSum(aVector other) {
		float res = 0f;

		aVector r = this.cpy().crs(other.cpy());

		for (int i = 0; i < r.size(); i++) {
			res -= r.get(i).floatValue();
		}

		return res;
	}

	public float dif() {
		float sum = 0;
		for (int i = 0; i < this.size(); i++) {
			sum = N_Operator.sub(sum, this.get(i)).floatValue();
		}
		return sum;
	}

	public float mean() {
		return this.sum().floatValue() / this.size();
	}

	public Number med() {
		// order from greatest to least
		Float[] f = N_Operator.resolveTo(this.toArray(), 0f);
		Arrays.sort(f);

		int m = (this.size() / 2) - 1;
		if (aMaths.isEven(this.size())) {
			int m1 = m + 1;
			return N_Operator.div(N_Operator.add(f[m], f[m1]), 2);
		} else
			return f[m];

		// return N_Operator.floor((this.size() / 2)).intValue();

	}

	public Number mode() {
		aThingCounter<Number> counter = new aThingCounter<Number>(this.elements);

		return counter.getMostFreq();

	}

	public Number mag() {// i1*i2*i3,etc
		float sum = 1;
		for (int i = 0; i < this.size(); i++) {
			sum = N_Operator.mul(sum, this.get(i)).floatValue();

		}

		return N_Operator.resolveTo(sum, this.value);
	}

	private static void _GROUP_() {

	}

	@Override
	public aVector join(iGroup other) {
		// union add
		this.elements.join(other);
		return this;
	}

	public aVector[] split(int atIndex) {
		aVector[] result = new aVector[2];
		if (atIndex > this.size() - 1) {
			result[0] = this;
			result[1] = new aVector().resize(this.size());
		}

		aVector nV = new aVector(this.value);
		for (int i = atIndex, j = 0; i < this.size(); i++, j++) {
			nV.setAt(j, this.get(i));
		}

		this.truncate(atIndex - 1);

		result[0] = this;
		result[1] = nV;
		return result;
	}

	public aVector join(aVector other) {
		this.append(other);
		return this;
	}

	public aVector[] stack(aVector... others) {
		aVector[] r = new aVector[others.length + 1];
		r[0] = this;
		for (int i = 0; i < others.length; i++)
			r[i + 1] = others[i];
		return r;
	}

	public aVector indent(int by) {
		for (int i = 0; i < by; i++)
			this.insert(0, 0);
		return this;
	}

	private static void _UTIL_() {

	}

	public static aVector Zeros(int len) {
		return new aVector().filled(len, 0);
	}

	public static aVector Ones(int len) {
		return new aVector().filled(len, 1);
	}

	public static aVector Unit(aVector v) {
		return new aVector().filled(v.size(), 1);
	}

	public Number getLargest() {
		return N_Operator.maxOf(this.toList());
	}

	public Number getSmallest() {
		return N_Operator.minOf(this.toList());
	}

	// cofactor
	public aVector cof() {
		aVector v = new aVector().filled(this.size(), 0);
		for (int i = 0; i < v.size(); i++)
			if (aMaths.isEven(i))
				v.setAt(i, 1);

		return v;
	}

	// swizzel
	public static aVector swz(aVector subject, aVector ind) {
		// xzz = append this.get(0,2,2)
		Number[] swz = new Number[ind.size()];
		int s = Math.min(subject.size(), ind.size());
		for (int i = 0; i < ind.size(); i++) {

			if (i < subject.size())
				swz[i] = subject.get(ind.get(i).intValue());
			else
				swz[i] = 0;
		}

		return new aVector(swz);
	}

	public aVector swz(Number... N) {
		return this.swz(new aVector(N));
	}

	public aVector toType(Number n) {
		aVector c = this.cpy();
		this.value = N_Operator.resolveTo(0, n);
		for (int i = 0; i < this.size(); i++) {
			Number N = this.get(i);
			this.setAt(i, N_Operator.resolveTo(N, n));
		}

		return this;
	}

	// shaves 0 from left-side of vector
	public aVector collapseLeft() {

		int to = 0;

		for (int i = 0; i < this.size() - 1; i++)
			if (N_Operator.isEqual(this.get(i), 0)) {
				to++;
			} else
				break;

		for (int r = 0; r < to; r++)
			this.remove(0);

		return this;
	}

	// shaves 0 from right-side of vector
	public aVector collapseRight() {

		for (int i = this.size() - 1; i >= 0; i--) {
			if (N_Operator.isEqual(this.get(i), 0))
				this.remove(i);
			else
				break;
		}

		return this;
	}

	public aVector collapse() {
		return this.collapseLeft().collapseRight();
	}

	public Vector2 toVec2() {
		Vector2 v = new Vector2();

		float x = 0;
		float y = 0;

		if (this.get(0) != null)
			x = this.get(0).floatValue();
		if (this.get(1) != null)
			y = this.get(1).floatValue();

		v.set(x, y);
		return v;

	}

	public Vector3 toVec3() {
		Vector3 v = new Vector3();

		float x = 0;
		float y = 0;
		float z = 0;

		if (this.get(0) != null)
			x = this.get(0).floatValue();
		if (this.get(1) != null)
			y = this.get(1).floatValue();
		if (this.get(2) != null)
			z = this.get(2).floatValue();

		v.set(x, y, z);
		return v;

	}

	public Quaternion toQuat() {
		Quaternion v = new Quaternion();

		float x = 0;
		float y = 0;
		float z = 0;
		float w = 0;

		if (this.get(0) != null)
			x = this.get(0).floatValue();
		if (this.get(1) != null)
			y = this.get(1).floatValue();
		if (this.get(2) != null)
			z = this.get(2).floatValue();
		if (this.get(3) != null)
			z = this.get(3).floatValue();

		v.set(x, y, z, w);

		return v;
	}

	public Color toColor() {
		Color v = new Color();

		float x = 0;
		float y = 0;
		float z = 0;
		float w = 0;

		if (this.get(0) != null)
			x = this.get(0).floatValue();
		if (this.get(1) != null)
			y = this.get(1).floatValue();
		if (this.get(2) != null)
			z = this.get(2).floatValue();
		if (this.get(3) != null)
			z = this.get(3).floatValue();

		v.set(x, y, z, w);

		return v;
	}

	public Number x() {
		return this.getDefault(0, this.Resolve.apply(0));
	}

	public Number y() {
		return this.getDefault(1, this.Resolve.apply(0));
	}

	public Number z() {
		return this.getDefault(2, this.Resolve.apply(0));
	}

	public Number w() {
		return this.getDefault(3, this.Resolve.apply(0));
	}

	@Override
	public Number numberValue() {
		return N_Operator.resolveTo(this.size(), this.value);
	}

	@Override
	public Integer firstIndex() {
		return 0;
	}

	@Override
	public aMap<Integer, Number> toMap() {
		aMap<Integer, Number> M = new aMap<Integer, Number>();
		for (int i = 0; i < this.size(); i++)
			M.put(i, this.get(i));
		return M;
	}
	
	@Override
	public Integer getIndexType() {
		return 0;
	}


}

