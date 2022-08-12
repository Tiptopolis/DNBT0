package com.Rev.Core._MonDexApp.Views;

import static com.Rev.Core.AppUtils.*;
import java.awt.*;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core.Console.UI.iConsoleListener;
import com.Rev.Core.Primitive.aMap;
import com.Rev.Core.zCHEAT_CODEX.AbstractDoodad;

public class MainMenu_Mon extends aConsoleView {

	public MainMenu_Mon(ConsoleUI manager) {
		super(manager);
		Log("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
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
		Log("Welcome to aBeastiary!");
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
