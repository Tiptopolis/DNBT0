package com.Rev.P1_Core.Console.UI;

public interface iDataCRUD<T> {

	public void create(T entry);

	public T read(int indx);

	public void update( int index, T entry); // CRUD

	public void delete(int index);

}
