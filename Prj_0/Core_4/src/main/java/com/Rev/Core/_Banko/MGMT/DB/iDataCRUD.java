package com.Rev.Core._Banko.MGMT.DB;

public interface iDataCRUD<T> {

	public void create(T entry);

	public void read(int indx);

	public void update(T entry); // CRUD

	public void delete(int index);

}
