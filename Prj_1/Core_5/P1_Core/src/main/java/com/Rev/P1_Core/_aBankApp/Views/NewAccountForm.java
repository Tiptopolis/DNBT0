package com.Rev.P1_Core._aBankApp.Views;

import static com.Rev.P1_Core.AppUtils.Log;

import com.Rev.P1_Core.Console.ConsoleUI;
import com.Rev.P1_Core.Console.UI.aConsoleView;
import com.Rev.P1_Core._aBankApp.BankDirector;
import com.Rev.P1_Core._aBankApp.Data._Account;

public class NewAccountForm extends aConsoleView {

	// ui to creating a new account for current user.

	private int type = 0;
	boolean dioT = false;

	private boolean selectedType = true;

	public NewAccountForm(ConsoleUI manager) {
		super(manager);
	}

	@Override
	public void init() {
		super.init();

		this.options.put("*", "ACCT TYPE");
		this.options.put(".", "-SUBMIT-");
	}

	// print to console
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

	// handle incoming console input
	@Override
	public boolean handle(String inp) {

		if (!dioT) // no dialogs open?
			if (super.handle(inp))
				return true;

		if (inp.equals("*"))
			selectedType = !selectedType;

		setAcctType();
		if (inp.equals(".") || inp.equals("") || inp.equals(" ") || inp.equals("SUBMIT") || inp == null) {
			Log("TRY SUBMIT>");
			this.submit();
			return true;
		}

		return false;
	}

	//////
	//
	private void setAcctType() {
		if (this.selectedType)
			type = 0;
		else
			type = 1;
	}

	private void submit() {
		int userID = this.manager.Session.loggedAs.dbIndex();
		int type = this.type;

		_Account account = new _Account(userID, type);

		BankDirector.Accounts.create(account);
		this.clear();
		account.clear();
		// this.input("^"); // casts the 'OUT' spell
		this.manager.Session.setView(new UserView(this.manager));
	}

}
