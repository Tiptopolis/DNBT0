package Core._PRIM.A_I;

import Core._PRIM.aNode;

public interface iNode<T> {

	public iNode getRoot();
	



	public T get();

	public void set(T to);

	public default Class of() {
		if (this.get() != null)
			return this.get().getClass();
		else
			return Object.class;
	}

}
