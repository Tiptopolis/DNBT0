package Core._Console;

import static Core.AppUtils.*;


import Core._Console.UI.ConsoleView;
import Core._Console.UI.iConsoleListener;
import Core._PRIM.iCollection;

public class ConsoleUI implements iConsoleListener{

	public static ConsoleView CurrentView;
	
	
	public ConsoleUI()
	{
		
	}
	

	

	
	public void render()
	{
		
	}

	@Override
	public boolean input(String inp) {
		Log(this.getClass().getSimpleName() + ":" + inp);

		if (this.getSubscribers() != null)
			for (iConsoleListener s : this.getSubscribers()) {
				if (s.input(inp))
					return true;
			}
		return false;
	}


	@Override
	public iCollection<iConsoleListener> getSubscribers() {
		// TODO Auto-generated method stub
		return null;
	}






}
