package com.Rev.P1_Core.Primitive.A_I;

import com.Rev.P1_Core.Primitive.Data.JSON.JSONAware;

public interface iNode<T> extends JSONAware {

	public T get();

	// public void set(T to);

	public default Object of() {
		if (this.get() != null)
			return this.get().getClass();
		else
			return Object.class;
	}

}
