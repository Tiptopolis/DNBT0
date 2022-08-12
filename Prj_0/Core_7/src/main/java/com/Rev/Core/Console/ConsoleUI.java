package com.Rev.Core.Console;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core.Console.UI.aUseSession;
import com.Rev.Core.Console.UI.iConsoleListener;
import com.Rev.Core.Primitive.aLinkedList;
import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core._Banko.Views.MainMenu;

public class ConsoleUI implements iConsoleListener {

	// Input Manager
	// transmits console input as String to Session(ViewManager)

	public aUseSession Session; // replaces current&previous

	protected aLinkedList<aConsoleView> CustomerPath;
	protected aLinkedList<aConsoleView> BankPath;

	public ConsoleUI() {
		this.Session = new aUseSession(this, new MainMenu(this));
		this.Session.setView(Session.Root);

	}

	public void render() {
		Session.Current.render();
	}

	@Override
	public boolean input(String inp) {
		// Log(this.getClass().getSimpleName() + ":> " + inp);

		// if (CurrentView != null)
		// return CurrentView.input(inp);
		if (Session != null)
			return Session.input(inp);
		return false;
	}

	@Override
	public iCollection<iConsoleListener> getSubscribers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void dispose()
	{
		
	}

}
