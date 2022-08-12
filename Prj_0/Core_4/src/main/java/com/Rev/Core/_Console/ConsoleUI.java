package com.Rev.Core._Console;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core._Banko.MGMT.aUseSession;
import com.Rev.Core._Banko.Views.MainMenu;
import com.Rev.Core._Console.UI.ConsoleView;
import com.Rev.Core._Console.UI.iConsoleListener;
import com.Rev.Core._PRIM.aLinkedList;
import com.Rev.Core._PRIM.A_I.iCollection;

public class ConsoleUI implements iConsoleListener {

	
	public aUseSession Session; // replaces current&previous

	protected aLinkedList<ConsoleView> CustomerPath;
	protected aLinkedList<ConsoleView> BankPath;

	public ConsoleUI() {
		this.Session = new aUseSession(this,new MainMenu(this));
		this.Session.setView(Session.Root);
		
	}

	public void render() {
		Session.Current.renderFrame();
	}






	@Override
	public boolean input(String inp) {
		//Log(this.getClass().getSimpleName() + ":> " + inp);

		//if (CurrentView != null)
			//return CurrentView.input(inp);
		if (Session != null)
		return Session.input(inp);
		return false;
	}

	@Override
	public iCollection<iConsoleListener> getSubscribers() {
		// TODO Auto-generated method stub
		return null;
	}

}
