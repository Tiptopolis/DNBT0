package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.Log;

import com.Rev.Core._Console.ConsoleUI;
import com.Rev.Core._Console.UI.ConsoleView;
import com.Rev.Core._PRIM.aMultiMap;
import com.Rev.Core._PRIM.Data.aDataField;

public class UserViewMain extends ConsoleView {

	
	private aMultiMap<Integer, aDataField> accountCache = new aMultiMap<Integer, aDataField>();
	
	public UserViewMain(ConsoleUI manager ) {
		super(manager);
	}

	@Override
	public void renderFrame() {
		super.renderFrame();
		// data
		// input options

		Log(this.options.toString());
		Log("");
	}
	
	public void listAccounts()
	{
		
	}
	
	public void alterFunds(float amount)
	{
		
	}
	
}
