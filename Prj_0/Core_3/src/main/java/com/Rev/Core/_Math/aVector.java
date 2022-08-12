package com.Rev.Core._Math;

import java.util.Iterator;

import static com.Rev.Core._Math.A_I.N_Resolver.*;

import com.Rev.Core._Math.A_I.N_Resolver;
import com.Rev.Core._PRIM.aList;
import com.Rev.Core._PRIM.A_I.iCollection;

public class aVector<N extends Number> extends aNumber
		implements Iterable<Number>, CharSequence, Comparable<Number>, iCollection<Number> {
	public aList<Number> elements;

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
	public void set(int at, Number to) {
		this.elements.set(at, to);

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
	
	
	public String valueString()
	{
		String s = "";
		for(int i =0; i < this.elements.getSize(); i++)
		{
			s+=this.elements.get(i);
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
	public int length() {
		return this.elements.getSize();
	}

	@Override
	public char charAt(int index) {
		return this.valueString().charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return this.valueString().subSequence(start, end);
	}
}
