package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.Log;

import com.Rev.Core._Console.ConsoleUI;
import com.Rev.Core._Console.UI.ConsoleView;

public class BankLogin extends ConsoleView {

	public BankLogin(ConsoleUI manager) {
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

}
