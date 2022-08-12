package com.Rev.P1_Core.Console.UI;

import com.Rev.P1_Core.Primitive.A_I.iCollection;

public interface iConsoleListener {
	
	public default boolean input(Object inp) {
		return this.input(inp.toString());
	}

	//transmit input to registered subscribers
	public default boolean input(String inp) {
		if (this.getSubscribers() != null)
			for (iConsoleListener s : this.getSubscribers()) {
				if (s.input(inp))
					return true;
			}
		return false;
	}

	//to whom is my input transmitted? should probably return aSet until I hoobastank aNode stuff into this
	public default iCollection<iConsoleListener> getSubscribers()
	{
		return null;
	}
}
