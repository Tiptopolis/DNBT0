package com.Rev.Core.Primitive;

import static com.Rev.Core.AppUtils.*;

import java.util.Iterator;

import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core.Primitive.A_I.iNodeIterator;

public class aLinkedList<T> implements iCollection<T> {

	private aNode<T> first;
	private aNode<T> last;

	// add
	@Override
	public void append(T entry) {
		this.linkNode(entry);
	}

	// addAll
	@Override
	public void append(T... entries) {
		for (int i = 0; i < entries.length; i++)
			this.append(entries[i]);
	}

	// insert aNode of <T> @index
	@Override
	public void insert(T entry, int atIndex) {
		// displaces current node down(+) 1
		aNode newNode = new aNode(entry);

		aNode current = (aNode) this.get(atIndex);
		aNode prev = null;
		if (current.has("PREVIOUS", this)) {
			prev = ((aLink) current.links.get("PREVIOUS")).target;

			((aLink) current.links.get("PREVIOUS")).target = newNode;
			((aLink) prev.links.get("NEXT")).target = newNode;
			newNode.link("NEXT", current, this);
			newNode.link("PREVIOUS", prev, this);
		}

	}

	// handles linking for append()
	private void linkNode(T entry) {
		aNode newNode = new aNode(entry);
		boolean F = (this.first != null);
		boolean L = (this.last != null);

		if (!F && !L) {
			this.first = newNode;
			this.last = newNode;
		} else {
			this.linkNode(this.last, newNode, null);
			this.last = newNode;
		}

	}

	// handles linking for insert
	private void linkNode(aNode previous, aNode entry, aNode next) {

		boolean P = (previous != null);
		boolean E = ((entry != null) && (entry != previous) && (entry != next));
		boolean N = (next != null);

		if (E && P) {

			if (previous.has("NEXT", this))
				((aLink) previous.links.get("NEXT")).target = entry;
			else
				previous.link("NEXT", entry, this);

			if (entry.has("PREVIOUS", this))
				((aLink) previous.links.get("PREVIOUS")).target = entry;
			else
				entry.link("PREVIOUS", previous, this);
		}
		if (E && N) {
			if (entry.has("NEXT", this))
				((aLink) entry.links.get("NEXT")).target = next;
			else
				entry.link("NEXT", next, this);

			if (next.has("PREVIOUS", this))
				((aLink) next.links.get("PREVIOUS")).target = entry;
			else
				next.link("PREVIOUS", entry, this);
		}

	}

	// overwrites data in the node @index -> to
	@Override
	public void setAt(int at, T to) {
		if (at < this.getSize()) {
			aNode N = (aNode) this.get(at);
			N.set(to);
		}
	}

	// quick get
	// returns the node @index. chaining getClass() on this returned value breaks,
	// use getNode/getData for external access
	@Override
	public T get(int index) {
		for (T n : this) {
			if (this.indexOf(n) == index)
				return n;
		}
		return null;
	}

	// returns the node @index
	public aNode<T> getNode(int index) {
		return (aNode) this.get(index);
	}

	// returns the data of the node @index
	public T getData(int index) {
		return ((aNode<T>) this.get(index)).get();
	}

	// how many nodes?
	@Override
	public int getSize() {
		int c = 0;
		for (T N : this) {
			c++;
		}

		return c;
	}

	public aNode<T> getFirst() {
		return this.first;
	}

	public aNode<T> getLast() {
		return this.last;
	}

	@Override
	public int indexOf(Object object) {
		// loop thru till you find the target, return iteration count

		// aNode current = this.first;
		int c = 0;
		for (T n : this) {

			if (n instanceof aNode) {
				aNode N = (aNode) n;
				if (object == N || object == N.get())
					return c;
			}
			c++;

		}

		return -1;
	}

	@Override
	public void remove(int index) {
		aNode N = (aNode) this.get(index); // node to remove

		boolean isFirst = (N == this.first);
		boolean isLast = (N == this.last);

		aNode next = null;
		aNode prev = null;

		if (N.has("PREVIOUS", this)) {
			prev = ((aLink) N.links.get("PREVIOUS")).target;

			if (isLast)
				this.last = prev;
			N.disconnect("PREVIOUS", this);
			if (next != null)
				this.linkNode(prev, next, null);
		}

		if (N.has("NEXT", this)) {
			next = ((aLink) N.links.get("NEXT")).target;
			if (isFirst)
				this.first = next;
			N.disconnect("NEXT", this);
			if (prev != null)
				this.linkNode(null, prev, next);
		}

	}

	@Override
	public void clear() {

		int size = this.getSize();
		for (int i = 0; i < size; i++) {

			this.remove(0);

		}
		this.last = null;
		this.first = null;

	}

	@Override
	public boolean contains(T entry) {
		for (T n : this) {
			if (n == entry || n.equals(entry))
				return true;
			if (n instanceof aNode) {
				aNode N = (aNode) n;
				if (N.get() == entry || N.get().equals(entry))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return (this.first == null);
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkIterator(this, this.first);
	}

	@Override
	public String toString() {
		String s = "";
		s += this.first + " => " + this.last;

		return s;
	}

	public String toLog() {
		String log = this.toString();

		return log;
	}

	private class LinkIterator<T> implements iNodeIterator<T> {

		private aLinkedList array;

		private aNode current;
		private aNode next;

		public LinkIterator(aLinkedList<T> array, aNode first) {
			this.array = array;
			this.current = first;

		}

		@Override
		public boolean hasNext() {

			if (this.current != null && this.current.has("NEXT", this.array)) {
				this.next = ((aLink) this.current.links.get("NEXT")).target;
				return true;
			}

			return false;
		}

		@Override
		public aNode<T> next() {

			if (this.hasNext()) {

				this.current = this.next;
				this.next = null;
				return current;
			}

			return null;
		}

	}

}
