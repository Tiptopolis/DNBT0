package com.Rev.Core._Console.UI;

import com.Rev.Core._PRIM.A_I.iCollection;

public interface iConsoleListener {

	public default boolean input(Object inp) {
		return this.input(inp.toString());
	}

	public default boolean input(String inp) {
		if (this.getSubscribers() != null)
			for (iConsoleListener s : this.getSubscribers()) {
				if (s.input(inp))
					return true;
			}
		return false;
	}

	public iCollection<iConsoleListener> getSubscribers();
}
