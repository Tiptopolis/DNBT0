package com.Rev.Core._MonDexApp.Views;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core._MonDexApp.MonDirector;
import com.Rev.Core._MonDexApp.Data._Creature;

public class NewCreatureForm extends aConsoleView {

	// ui to creating a new account for current user.

	private String type = ""+0;
	boolean dioT = false;

	private boolean selectedType = true;

	public NewCreatureForm(ConsoleUI manager) {
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
			type = "CHK";
		else
			type = "SAV";
	}

	private void submit() {
		int userID = this.manager.Session.loggedAs.dbIndex();
		String type = this.type;

		_Creature account = new _Creature(userID, type);

		MonDirector.Creatures.create(account);
		this.clear();
		account.clear();
		// this.input("^"); // casts the 'OUT' spell
		this.manager.Session.setView(new UserView(this.manager));
	}

}
