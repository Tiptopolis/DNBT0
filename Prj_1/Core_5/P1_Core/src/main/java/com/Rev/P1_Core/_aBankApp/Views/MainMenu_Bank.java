package com.Rev.P1_Core._aBankApp.Views;

import static com.Rev.P1_Core.AppUtils.*;

import java.awt.*;

import com.Rev.P1_Core.Console.ConsoleUI;
import com.Rev.P1_Core.Console.UI.aConsoleView;
import com.Rev.P1_Core.Console.UI.iConsoleListener;
import com.Rev.P1_Core.Primitive.aMap;
import com.Rev.P1_Core.zCHEAT_CODEX.AbstractDoodad;

public class MainMenu_Bank extends aConsoleView {

	public MainMenu_Bank(ConsoleUI manager) {
		super(manager);

	}

	@Override
	public void init() {
		// super.init();

		this.options = new aMap<String, String>();
		this.options.put("X", "EXIT");
		this.options.put("1", "LOGIN");
		this.options.put("2", "REGISTER");

	}

	
	@Override
	public void render() {
		super.render();
		// data
		// input options
		Log("Welcome to aBank!");
		Log(this.options.toString());

	}

	// passes console input to all subscribers
	@Override
	public boolean input(String inp) {
		Log(this.getClass().getSimpleName() + ":> " + inp);
		if (this.handle(inp)) {
			return true;
		}
		this.render(); //print after input returns false. didnt look work without it
		if (this.getSubscribers() != null)
			for (iConsoleListener s : this.getSubscribers()) {
				if (s.input(inp))
					return true;

			}
		return false;
	}

	// handle incoming console input
	@Override
	public boolean handle(String inp) {

		// overrides the 'back' command
		// prevents backing into the AccountView or whatever
		if (inp.equals("<"))
			return false;

		if (super.handle(inp))
			return true;

		if (inp.equals("1")) {
			this.manager.Session.setView(new UserLogin(this.manager));
			return true;
		}

		if (inp.equals("2")) {
			this.manager.Session.setView(new NewUserForm(this.manager));
			return true;
		}

		this.render();
		return false;
	}

}
