package Core._PRIM;

import static Core.AppUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class aList<T> implements iCollection<T> {

	// susceptible to duplicates

	private Object[] data;

	public aList() {
		this.data = new Object[0];
	}

	protected void grow(int by) {
		data = Arrays.copyOf(data, data.length + by);
	}

	protected void growTo(int toCap) {
		data = Arrays.copyOf(data, toCap);
	}

	public void add(T object) {

		int i = data.length + 1;
		this.growTo(i);
		data[i - 1] = object;

	}

	public void add(T... objects) {
		for (int i = 0; i < objects.length; i++) {
			this.add(objects[i]);
		}
	}

	@Override
	public void insert(T entry, int atIndex) {

		// this.clear();
		aList<T> pre = new aList<T>();
		aList<T> pst = new aList<T>();
		for (int i = 0; i < atIndex; i++) {
			pre.add(this.get(i));
		}

		for (int j = atIndex; j < this.getSize(); j++) {
			pst.add(this.get(j));
		}

		this.clear();
		for (int i = 0; i < pre.getSize(); i++) {
			this.add(pre.get(i));
		}

		this.add(entry);
		for (int j = 0; j < pst.getSize(); j++) {
			this.add(pst.get(j));
		}

	}
	
	public void set(int atIndex, T entry)
	{
		if(atIndex <= this.getSize())
			this.data[atIndex] = entry;
	}

	public void remove(int i) {

		// anti-insert
		aList<T> res = new aList<T>();

		this.data[i] = null;
		for (int j = 0; j < this.getSize(); j++) {
			if (this.get(j) != null)
				res.add(this.get(j));
		}

		this.growTo(0);

		for (int j = 0; j < res.getSize(); j++) {
			this.add(res.get(j));
		}
	}

	public T get(int index) {

		if (index < data.length) {
			return (T) data[index];
		} else
			return null;
	}

	public int getSize() {
		return this.data.length;
	}

	public int indexOf(T object) {

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
		for (int i = 0; i <= this.getSize() - 1; i++) {
			this.remove(i);
		}
		this.growTo(0);
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
