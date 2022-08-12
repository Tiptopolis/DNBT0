package com.Rev.Core.Primitive.A_I;

public interface iCollection<E> extends Iterable<E>, iGroup {

	public void append(E entry); // add lol

	public void append(E... entries);// addAll lol
	
	public default void append(iCollection<E> entries)
	{
		this.append(entries.toArray());
	}

	public void insert(E entry, int atIndex);// insert entry @index, shifts others down

	public void setAt(int at, E to); // replace item @index with E to

	public E get(int index); // returns item @index

	public int indexOf(Object object); // return @index of object

	public void remove(int index); // removes item @index, shifts others up

	public default E take(int index)
	{
		E get = this.get(index);
		this.remove(index);
		return get;
	}

	public boolean contains(E entry); // does has?!

	
	public E[] toArray();
}
