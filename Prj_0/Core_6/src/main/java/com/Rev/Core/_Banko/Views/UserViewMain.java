package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.Log;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core.Primitive.aMultiMap;
import com.Rev.Core.Primitive.aSet;
import com.Rev.Core.Primitive.Data.aDataField;
import com.Rev.Core._Banko.DBMS._Account;

public class UserViewMain extends aConsoleView {

	// private aMultiMap<Integer, aDataField> accountCache = new aMultiMap<Integer,
	// aDataField>();
	private aSet<_Account> accountCache = new aSet<_Account>();

	public UserViewMain(ConsoleUI manager) {
		super(manager);
		this.cacheAccounts();
	}

	@Override
	public void render() {
		super.render();
		// data
		// input options

		Log(this.options.toString());
		Log("");
	}

	private void cacheAccounts() {

	}

	public void alterFunds(float amount) {

	}

}
