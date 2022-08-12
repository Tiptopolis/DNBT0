package Core._Banko.Views;

import static Core.AppUtils.Log;

import Core._Console.ConsoleUI;
import Core._Console.UI.ConsoleView;

public class BankMain extends ConsoleView {

	// BANK_MAIN >> BANK_LOGIN[UN,PW] -[v]
	// {[NEW CUSTOMER, NEW ACCOUNT]} -{V}
	// [1]-NewCst = Name, Email, Phone#
	// [2]-NewAcct = Cst, Type, InitialDeposit

	// PendingAccountRequests

	public BankMain(ConsoleUI manager) {
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
