package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.Log;

import com.Rev.Core._Console.ConsoleUI;
import com.Rev.Core._Console.UI.ConsoleView;

public class UserLogin extends ConsoleView {

	public UserLogin(ConsoleUI manager) {
		super(manager);
		// TODO Auto-generated constructor stub
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
