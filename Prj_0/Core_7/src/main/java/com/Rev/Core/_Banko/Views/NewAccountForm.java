package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.Log;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core._Banko.BankDirector;
import com.Rev.Core._Banko.Data._Account;

public class NewAccountForm extends aConsoleView {

	// store in junction table?
	
	private int type = 0;
	boolean dioT = false;

	private boolean selectedType = true;

	public NewAccountForm(ConsoleUI manager) {
		super(manager);
	}

	@Override
	public void init() {
		super.init();

		this.options.put("1", "ACCT TYPE");
		this.options.put(".", "-SUBMIT-");
	}

	@Override
	public void render() {

		String type = "";
		if (selectedType)
			type = "CHECKING";
		else
			type = "SAVINGS";

		super.render();

		Log(this.options.toString());
		Log("*" + type);
	}

	@Override
	public boolean handle(String inp) {

		if (super.handle(inp))
			return true;

		if (inp.equals("1"))
			selectedType = !selectedType;
		
		setActTp();
		if (inp.equals(".") || inp.equals("") || inp.equals(" ") || inp.equals("SUBMIT") || inp == null) {
			Log("TRY SUBMIT>");
			this.submit();			
			return true;
		}
		

		return false;
	}
	
	private void setActTp()
	{
		if(this.selectedType)
			type =0;
		else
			type =1;
	}
	
	private void submit()
	{
		int userID = this.manager.Session.loggedAs.Index();
		int type = this.type;
		
		_Account account = new _Account(userID, type);
		
		BankDirector.Accounts.create(account);
		this.clear();
		account.clear();
		//this.input("^");
		this.manager.Session.setView(new UserView(this.manager));
	}

}
