package Core._PRIM;

public class aNode<T> {

	public aNode parent;
	public aMultiMap<String, aNode> connections;

	private T get;
	private Class of;

	public aNode(T data) {
		this.get = data;
		this.of = data.getClass();
	}

	public aNode getRoot() {
		if (this.parent != null)
			return this.parent.getRoot();
		else
			return this;
	}

	public Class of() {
		if (this.of != null)
			return this.of;
		else
			return Object.class;
	}

	public T get() {
		if (this.get != null)
			return (T) this.get;
		else
			return null;
	}

	public void set(T to) {
		this.get = to;
		this.of = to.getClass();
	}

}