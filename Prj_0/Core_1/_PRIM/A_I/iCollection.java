package Core._PRIM.A_I;

public interface iCollection<E> extends Iterable<E>{

	public int getSize();
	public void append(E entry); //add lol
	public void append(E... entries);//addAll lol
	public void insert(E entry, int atIndex);
	public void set(int at, E to);
	public E get(int index);
	public int indexOf(Object object);
	public void remove(int index);	
	public void clear();
	public boolean contains(E entry);
	public boolean isEmpty();
	
	
}
