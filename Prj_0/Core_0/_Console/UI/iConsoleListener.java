package Core._Console.UI;

import Core._PRIM.iCollection;

public interface iConsoleListener {

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
