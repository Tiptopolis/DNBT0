package com.Rev.Core._Console.UI;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.App;
import com.Rev.Core._Banko.Views.MainMenu;
import com.Rev.Core._Console.ConsoleUI;
import com.Rev.Core._PRIM.aLinkedList;
import com.Rev.Core._PRIM.A_I.iCollection;

public class aUseSession implements iConsoleListener {

	private ConsoleUI Handler;	

	public ConsoleView Root;
	
	public ConsoleView Current;
	public ConsoleView Previous;
	
	aLinkedList<ConsoleView> NaigationPath;
	
	public String as = "null";

	public aUseSession(ConsoleUI handler, ConsoleView root) {
		this.Root = root;
		this.Handler = handler;
	}

	@Override
	public boolean input(String inp) {
		//Log(this.getClass().getSimpleName() + ":> " + inp);
		
		if (this.Current != null)
			return this.Current.input(inp);

		return false;
	}

	public void back() {
		if (this.Previous != null) {
			this.setView(this.Previous);
		}
	}



	public void returnMain() {
		this.Previous = null;
		this.setView(this.Root);
	}

	public void exit() {
		App.AppConsole.input("SHELL:TERMINATE");
	}
	
	public void setView(ConsoleView view) {
		if (this.Current != null) {
			this.Previous = this.Current;
			this.Current.dispose();
		}
		Log("VIEW> " + view.getClass().getSimpleName());
		this.Current = view;
		this.Current.show();

	}

	@Override
	public iCollection<iConsoleListener> getSubscribers() {
		// TODO Auto-generated method stub
		return null;
	}

}
