package Metatron.Core.Primitive.Data.A_I;

public interface iCRUD<T> {

	public void create(T entry);
	
	public T read(int index);
	
	public<X> X update(int at, T entry);
	
	public void delete(int index);
	
	
	
	
}
