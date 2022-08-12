package com.Rev.Core._Math;

import static com.Rev.Core.AppUtils.*;

import java.util.Iterator;

import static com.Rev.Core._Math.A_I.N_Resolver.*;

import com.Rev.Core.Primitive.aList;
import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core._Math.A_I.N_Operator;
import com.Rev.Core._Math.A_I.N_Resolver;

public class aVector<N extends Number> extends aNumber
		implements Iterable<Number>, Comparable<Number>, iCollection<Number> {
	public aList<Number> elements;

	// i figured i'd try and redo my N-Vector with my list impl

	public aVector() {
		this(0f);
	}

	public aVector(Number type) {
		this.elements = new aList();
		elements.append(resolveTo(type, 0));
	}

	public aVector(Number... values) {
		this.elements = new aList();
		for (int i = 0; i < values.length; i++) {
			this.append(values[i]);
		}
	}

	@Override
	public int getSize() {

		return this.elements.getSize();
	}

	@Override
	public void append(Number entry) {
		if (!this.elements.isEmpty())
			this.elements.append(as(entry, this.elements.get(0)));
		else
			this.elements.append(entry);

	}

	@Override
	public void append(Number... entries) {
		this.elements.append(entries);

	}

	@Override
	public void insert(Number entry, int atIndex) {
		this.elements.insert(entry, atIndex);

	}

	@Override
	public void setAt(int at, Number to) {

		if (at >= this.getSize())
			this.append(to);
		else
			this.elements.setAt(at, to);
	}

	public aVector set(Number... values) {
		this.clear();
		for (int i = 0; i < values.length; i++) {
			this.append(values);
		}
		return this;
	}

	public aVector set(aVector to) {
		this.clear();
		for (Object n : to.elements)
			for (int i = 0; i < to.getSize(); i++) {
				// this.append(to.get(i));
				this.setAt(i, to.get(i));
			}
		return this;
	}

	@Override
	public Number get(int index) {
		return (Number) this.elements.get(index);
	}

	@Override
	public void remove(int index) {
		this.elements.remove(index);

	}

	@Override
	public void clear() {
		if (this.elements == null)
			this.elements = new aList<Number>();
		else
			this.elements.clear();
		this.append(0);
	}

	@Override
	public boolean contains(Number entry) {
		return this.elements.contains(entry);
	}

	@Override
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

	@Override
	public int compareTo(Number o) {
		return 0;
	}

	@Override
	public Iterator<Number> iterator() {
		return this.elements.iterator();
	}

	public String valueString() {
		String s = "";
		for (int i = 0; i < this.elements.getSize(); i++) {
			s += this.elements.get(i);
		}
		return s;
	}

	@Override
	public String toString() {
		// return this.elements.toString();String s = "";
		String s = "(";
		if (this.elements != null)
			for (int i = 0; i < this.elements.getSize(); i++) {
				s += /* "[" + i + "]" + */this.elements.get(i);
				if (i != this.elements.getSize() - 1)
					s += ",";
			}
		s += ")";
		return s;
	}

	@Override
	public int indexOf(Object object) {
		return this.elements.indexOf(object);
	}
	
	@Override
	public Number[] toArray() {
		return this.elements.toArray();
	}

	//////
	private void ___OPERATORS______() {
		// OPTION A, retain size & add congruent values
		// (1,2,3,4,5) <- this
		// +
		// (6,7,8) <-other, fills missing elements with 0 >: (6,7,8)0,0)
		// ___________
		// (7,9,11,4,5)
		/////////////////
		// (6,7,8) <-this
		// +
		// (1,2,3,4,5) <-other
		// ___________
		// (7,9,11)
		//
		// OPTION B, append 0s to missing dimensions
		// so ((7,8,9)4,5)
	}

	// remove _ to run as main
	public static void main_(String... args) {
		aVector a = new aVector(1, 2, 3, 4, 5);
		aVector b = new aVector(6, 7, 8);

		Log("a= " + a);
		Log("b= " + b);
		Log();
		Log("(a+b)=" + a.cpy().add(b.cpy()));
		Log("(b+a)=" + b.cpy().add(a.cpy()));
	}

	public aVector cpy() {
		return new aVector().set(this);
	}

	public aVector add(aVector other) {
		if (this.isEmpty())
			return this.set(other);

		Number type = this.get(0);

		for (int i = 0; i < this.getSize(); i++) {
			Number nxt = 0;
			Number to = 0;
			if (i > other.getSize() - 1)
				nxt = resolveTo(type, 0);
			else
				nxt = resolveTo(type, other.get(i));
			to = N_Operator.add(this.get(i), nxt);
			this.setAt(i, to);
		}
		return this;
	}

	public aVector add(Number other) {
		if (this.isEmpty())
			return this.set(other);

		Number type = this.get(0);

		for (int i = 0; i < this.getSize(); i++) {
			Number n = this.get(i);
			Number to = N_Operator.add(n, resolveTo(type, other));
			this.setAt(i, to);
		}
		return this;
	}

	public aVector sub(aVector other) {
		if (this.isEmpty())
			return this.set(other);

		Number type = this.get(0);

		for (int i = 0; i < this.getSize(); i++) {
			Number nxt = 0;
			Number to = 0;
			if (i > other.getSize() - 1)
				nxt = resolveTo(type, 0);
			else
				nxt = resolveTo(type, other.get(i));
			to = N_Operator.sub(this.get(i), nxt);
			this.setAt(i, to);
		}

		return this;
	}

	public aVector sub(Number other) {
		if (this.isEmpty())
			return this.set(other);

		Number type = this.get(0);

		for (int i = 0; i < this.getSize(); i++) {
			Number n = this.get(i);
			Number to = N_Operator.sub(n, resolveTo(type, other));
			this.setAt(i, to);
		}
		return this;
	}

	public aVector mul(aVector other) {
		if (this.isEmpty())
			return this.set(other);

		Number type = this.get(0);

		for (int i = 0; i < this.getSize(); i++) {
			Number nxt = 0;
			Number to = 0;
			if (i > other.getSize() - 1)
				nxt = resolveTo(type, 0);
			else
				nxt = resolveTo(type, other.get(i));
			to = N_Operator.mul(this.get(i), nxt);
			this.setAt(i, to);
		}

		return this;
	}

	public aVector mul(Number other) {
		if (this.isEmpty())
			return this.set(other);

		Number type = this.get(0);

		for (int i = 0; i < this.getSize(); i++) {
			Number n = this.get(i);
			Number to = N_Operator.mul(n, resolveTo(type, other));
			this.setAt(i, to);
		}
		return this;
	}

	public aVector div(aVector other) {
		if (this.isEmpty())
			return this.set(other);

		Number type = this.get(0);

		for (int i = 0; i < this.getSize(); i++) {
			Number nxt = 0;
			Number to = 0;
			if (i > other.getSize() - 1)
				nxt = resolveTo(type, 0);
			else
				nxt = resolveTo(type, other.get(i));
			to = N_Operator.div(this.get(i), nxt);
			this.setAt(i, to);
		}

		return this;
	}

	public aVector div(Number other) {
		if (this.isEmpty())
			return this.set(other);

		Number type = this.get(0);

		for (int i = 0; i < this.getSize(); i++) {
			Number n = this.get(i);
			Number to = N_Operator.div(n, resolveTo(type, other));
			this.setAt(i, to);
		}
		return this;
	}


}
