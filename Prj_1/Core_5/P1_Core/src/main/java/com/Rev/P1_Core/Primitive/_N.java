package com.Rev.P1_Core.Primitive;

public interface _N<N> {

	// names _Struct lolol

	public default void Label(String name) {

	}

	public default String Label() {
		return this.getClass().getSimpleName();
	}

	public default Object Node() {
		return this;
	}

	public default N Type() {
		return null;
	}

	public default _N Symbol() {
		return this;
	}

	public default Number Index() {
		return 0;
	}

	public default String toLog() {
		return this.getClass().getSimpleName();
	}

}
