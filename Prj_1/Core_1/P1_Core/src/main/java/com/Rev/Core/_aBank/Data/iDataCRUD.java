package com.Rev.Core._aBank.Data;

public interface iDataCRUD<T> {

	public void create(T entry);

	public T read(int indx);

	public void update( int index, T entry); // CRUD

	public void delete(int index);

}
