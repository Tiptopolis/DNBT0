package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.*;
import java.awt.*;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core.Console.UI.iConsoleListener;
import com.Rev.Core.Primitive.aMap;
import com.Rev.Core.zCHEAT_CODEX.AbstractDoodad;

public class MainMenu extends aConsoleView {

	

	public MainMenu(ConsoleUI manager) {
		super(manager);

	}

	@Override
	public void init() {
		super.init();
		this.options.put("1", "LOGIN");
		this.options.put("2", "REGISTER");
		
	}

	@Override
	public void render() {
		super.render();
		// data
		// input options

		Log(this.options.toString());

	}

	@Override
	public boolean input(String inp) {
		Log(this.getClass().getSimpleName() + ":> " + inp);
		if (this.handle(inp)) {
			return true;
		}
		this.render();
		if (this.getSubscribers() != null)
			for (iConsoleListener s : this.getSubscribers()) {
				if (s.input(inp))
					return true;

			}
		return false;
	}

	@Override
	public boolean handle(String inp) {
		if (super.handle(inp))
			return true;

		if (inp.equals("1")) {
			this.manager.Session.setView(new UserLogin(this.manager));
			return true;
		}

		
		if (inp.equals("2")) {
			//this.manager.Session.setView(new BankLogin(this.manager));
			this.manager.Session.setView(new NewUserForm(this.manager));
			return true;
		}

		this.render();
		return false;
	}

}
