package com.Rev.Core.Primitive.A_I;

public interface iCollection<E> extends Iterable<E> {

	public int getSize(); // number of items in collection

	public void append(E entry); // add lol

	public void append(E... entries);// addAll lol

	public void insert(E entry, int atIndex);// insert entry @index, shifts others down

	public void setAt(int at, E to); // replace item @index with E to

	public E get(int index); // returns item @index

	public int indexOf(Object object); // return @index of object

	public void remove(int index); // removes item @index, shifts others up

	public void clear(); //removes all

	public boolean contains(E entry); //does has?!

	public boolean isEmpty(); //no clue, honest...

}
