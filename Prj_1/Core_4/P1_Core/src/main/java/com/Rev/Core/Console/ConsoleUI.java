package com.Rev.Core.Console;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core.Console.UI.aUseSession;
import com.Rev.Core.Console.UI.iConsoleListener;
import com.Rev.Core.Primitive.aLinkedList;
import com.Rev.Core.Primitive.aSet;
import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core._aBankApp.Views.MainMenu_Bank;

public class ConsoleUI implements iConsoleListener {

	// Input Manager
	// transmits console input as String to Session(ViewManager)

	public aUseSession Session; // replaces current&previous

	protected aLinkedList<aConsoleView> CustomerPath;
	protected aLinkedList<aConsoleView> BankPath;

	public ConsoleUI() {
		//this.Session = new aUseSession(this, new MainMenu_Bank(this));
		//this.Session.setView(Session.Root);

	}

	// prints session's current view to console
	public void render() {
		Session.Current.render();
	}

	// receives input, passes to user session for handling
	@Override
	public boolean input(String inp) {
		// Log(this.getClass().getSimpleName() + ":> " + inp);

		if (Session != null)
			return Session.input(inp);
		return false;
	}

	//gets listeners subscribed to this
	@Override
	public iCollection<iConsoleListener> getSubscribers() {

		aSet<iConsoleListener> s = new aSet<iConsoleListener>();
		if (this.Session != null)
			s.append(this.Session);
		return s;
	}

	public void dispose() {
		Session.dispose();
	}

}
