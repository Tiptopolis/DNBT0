package com.Rev.Core._aBankApp;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aUseSession;
import com.Rev.Core._aBankApp.Views.MainMenu_Bank;

public class BankUI extends ConsoleUI{

	public BankUI()
	{
		this.Session = new aUseSession(this, new MainMenu_Bank(this));
		this.Session.setView(Session.Root);
	}
	
}
