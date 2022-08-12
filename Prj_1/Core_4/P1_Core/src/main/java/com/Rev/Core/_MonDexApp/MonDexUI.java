package com.Rev.Core._MonDexApp;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aUseSession;
import com.Rev.Core._MonDexApp.Views.MainMenu_Mon;

public class MonDexUI extends ConsoleUI{

	public MonDexUI()
	{
		this.Session = new aUseSession(this, new MainMenu_Mon(this));
		this.Session.setView(Session.Root);
	}
	
}
