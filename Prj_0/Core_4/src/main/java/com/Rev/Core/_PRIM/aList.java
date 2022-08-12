package com.Rev.Core._PRIM;

import static com.Rev.Core.AppUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.Rev.Core._PRIM.A_I.iCollection;

public class aList<T> implements iCollection<T> {

	// susceptible to duplicates

	protected Object[] data;

	public aList() {
		this.data = new Object[0];
	}

	public aList(T... entries) {
		this();
		for (int i = 0; i < entries.length; i++) {
			this.append(entries[i]);
		}
	}

	protected void grow(int by) {
		data = Arrays.copyOf(data, data.length + by);
	}

	protected void sizeTo(int toCap) {
		data = Arrays.copyOf(data, toCap);
	}

	// add()
	public void append(T object) {

		int i = data.length + 1;
		this.sizeTo(i);
		data[i - 1] = object;

	}

	// add()
	public void append(T... objects) {
		for (int i = 0; i < objects.length; i++) {
			this.append(objects[i]);
		}
	}

	@Override
	public void insert(T entry, int atIndex) {

		// this.clear();
		aList<T> pre = new aList<T>();
		aList<T> pst = new aList<T>();
		for (int i = 0; i < atIndex; i++) {
			pre.append(this.get(i));
		}

		for (int j = atIndex; j < this.getSize(); j++) {
			pst.append(this.get(j));
		}

		this.clear();
		for (int i = 0; i < pre.getSize(); i++) {
			this.append(pre.get(i));
		}

		this.append(entry);
		for (int j = 0; j < pst.getSize(); j++) {
			this.append(pst.get(j));
		}

	}

	public void set(int atIndex, T entry) {
		if (atIndex <= this.getSize())
			this.data[atIndex] = entry;
	}

	public void remove(int index) {

		// anti-insert
		aList<T> result = new aList<T>();

		this.data[index] = null;
		for (int i = 0; i < this.getSize(); i++) {
			if (this.get(i) != null)
				result.append(this.get(i));
		}

		this.sizeTo(0);

		for (int i = 0; i < result.getSize(); i++) {
			this.append(result.get(i));
		}
	}

	public void remove(T object) {
		if (this.contains(object)) {
			int i = this.indexOf(object);
			this.remove(i);
		}
	}

	public T get(int index) {

		if (index < data.length) {
			return (T) data[index];
		} else
			return null;
	}

	public T getFirst() {
		return this.get(0);
	}

	public T getLast() {
		int s = this.getSize() - 1;
		return this.get(s);
	}

	public int getSize() {
		return this.data.length;
	}

	public int indexOf(Object object) {

		for (int i = 0; i < this.data.length; i++) {
			if (data[i] == object)
				return i;
		}
		return -1;
	}

	public boolean contains(Object obj) {

		for (int i = 0; i <= this.data.length - 1; i++) {
			if (data[i] == obj)
				return true;
		}
		return false;
	}

	public boolean has(Object obj) {

		for (int i = 0; i <= this.data.length - 1; i++) {
			if (data[i].equals(obj))
				return true;
		}
		return false;
	}

	public boolean isEmpty() {
		if (this.data.length <= 0)
			return true;
		else
			return false;
	}

	public void clear() {
		for (int i = 0; i <= this.getSize() - 1; i++) {
			this.remove(i);
		}
		this.sizeTo(0);
	}

	@Override
	public String toString() {

		// String s = this.getClass().getSimpleName() + "{" + this.getSize() + "}\n";
		String s = "";
		s += "{";
		if (this.data != null)
			for (int i = 0; i < this.data.length; i++) {
				s += /* "[" + i + "]" + */this.data[i];
				if (i != this.data.length - 1)
					s += ",";
			}
		s += "}";
		return s;
	}

	public String toLog() {
		String log = this.getClass().getSimpleName() + "{" + this.getSize() + "}\n";
		if (this.data != null)
			for (int i = 0; i < this.data.length; i++) {
				log += "[" + i + "]" + this.data[i] + "\n";
			}
		return log;
	}

	@Override
	public Iterator<T> iterator() {
		return new aListIterator(this);
	}

	public class aListIterator implements Iterator<T> {

		public aList<T> array;
		int index = -1;
		boolean valid = true;

		public aListIterator(aList<T> array) {
			this.array = array;
		}

		@Override
		public boolean hasNext() {

			if (!valid) {
				throw new RuntimeException("#iterator() cannot be used nested.");
			}
			return index < (array.getSize() - 1);
		}

		@Override
		public T next() {
			if (index >= (array.getSize()))
				throw new NoSuchElementException(String.valueOf(index));
			if (!valid) {
				throw new RuntimeException("#iterator() cannot be used nested.");
			}
			index++;

			return array.get(index);
		}

	}

}
