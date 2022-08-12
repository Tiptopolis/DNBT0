package com.Rev.Core._Banko.DBMS;

public interface iDataCRUD<T> {

	public void create(T entry);

	public T read(int indx);

	public void update(T entry); // CRUD

	public void delete(int index);

}
