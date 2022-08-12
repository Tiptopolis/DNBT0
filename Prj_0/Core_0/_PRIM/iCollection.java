package Core._PRIM;

public interface iCollection<E> extends Iterable<E>{

	public int getSize();
	public void add(E entry);
	public void add(E... entries);
	public void insert(E entry, int atIndex);
	public void set(int at, E to);
	public E get(int index);
	public void remove(int index);	
	public void clear();
	public boolean contains(E entry);
	public boolean isEmpty();
	
}
