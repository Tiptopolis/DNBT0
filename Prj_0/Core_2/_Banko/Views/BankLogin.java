package Core._Banko.Views;

import static Core.AppUtils.Log;

import Core._Console.ConsoleUI;
import Core._Console.UI.ConsoleView;

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
