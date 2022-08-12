package Core._Banko.Views;

import static Core.AppUtils.Log;

import Core._Console.ConsoleUI;
import Core._Console.UI.ConsoleView;

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
