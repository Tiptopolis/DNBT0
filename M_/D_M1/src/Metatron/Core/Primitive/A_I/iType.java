package Metatron.Core.Primitive.A_I;

public interface iType<T> extends iCollection<T> {

	public String label();

	public default void extend(iType sub) {
		
	}

	public default void inherit(iType sup) {
		
	}

	@Override
	public default void insert(Integer at, T member) {
		 this.append(member);
		
			
	}

	@Override
	public default void set(Integer i, T o) {

	}

	@Override
	public default void append(T entry) {
		// extend or inherit?
		// extend

	}

	@Override
	public default iGroup resize(int to) {
		return this;
	}

	@Override
	public default Integer getIndexType() {
		return 0;
	}

	@Override
	public default T[] toArray() {
		// TODO Auto-generated method stub
		return (T[]) new Object[] { this.get(0) };
	}

	@Override
	public default void setAt(int at, T to) {

	}

	@Override
	public default void swap(int i, int j) {

	}

	@Override
	public default T[] getComponentData() {
		return this.toArray();
	}

}
