package com.Rev.Core.Primitive.Data;

import com.Rev.Core.Primitive.aSet;

public interface iType {

	/*
	 * public default<T> T def() { return null; }
	 */

	public boolean is(String of);

	public boolean is(Object of);

	public boolean isOf(String of);
	
	public boolean isOf(Object of);

	public boolean covers(String other);

	public boolean covers(Object other);

	public default aSet<String> aliases() {
		return new aSet<String>();
	}

}
