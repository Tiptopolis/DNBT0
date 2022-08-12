package Core._PRIM;

import static Core.AppUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import Core._PRIM.A_I.iCollection;

public class aSet<T> implements iCollection<T> {

	// no duplicatess

	protected Object[] data;

	public aSet() {
		this.data = new Object[0];
	}
	
	public aSet(T... entries) {
		this();
		for (int i = 0; i < entries.length; i++) {
			this.append(entries[i]);
		}
	}

	private void growTo(int toCap) {
		data = Arrays.copyOf(data, toCap);
	}

	public void append(T object) {

		if (!this.contains(object)) {
			int i = data.length + 1;
			this.growTo(i);
			data[i - 1] = object;
		}
	}

	public void append(T... objects) {
		for (int i = 0; i < objects.length; i++) {
			this.append(objects[i]);
		}
	}

	@Override
	public void insert(T entry, int atIndex) {
		aSet<T> tmpAll = new aSet<T>();
		aSet<T> tmpNew = new aSet<T>();
		for (int i = 0; i < atIndex; i++) {

		}
	}

	public void remove(int i) {
		final int newSize;
		if ((newSize = data.length - 1) > i)
			System.arraycopy(this.data, i + 1, this.data, i, newSize - i);
		this.data[newSize] = null;
		this.growTo(data.length - 1);
	}

	public T get(int index) {

		if (index < data.length) {
			return (T) data[index];
		} else
			return null;
	}

	public void set(int atIndex, T entry) {
		if (atIndex <= this.getSize())
			this.data[atIndex] = entry;
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
		String log = "aList{" + this.getSize() + "}\n";
		for (int i = 0; i <= this.data.length - 1; i++) {
			if (data[i] == obj)
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
		for (int i = 0; i < this.data.length; i++) {
			this.remove(i);
		}
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

		public aSet<T> array;
		int index = -1;
		boolean valid = true;

		public aListIterator(aSet<T> array) {
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
