package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.*;
import java.awt.*;

import com.Rev.Core._Console.ConsoleUI;
import com.Rev.Core._Console.UI.ConsoleView;
import com.Rev.Core._Console.UI.iConsoleListener;
import com.Rev.Core._Math.Maths;
import com.Rev.Core._PRIM.aMap;
import com.Rev.Core.zCHEAT_CODEX.AbstractDoodad;

public class MainMenu extends ConsoleView {

	public AbstractDoodad D1;

	public MainMenu(ConsoleUI manager) {
		super(manager);

	}

	@Override
	public void init() {
		super.init();
		this.options.put("1", "USER");
		this.options.put("2", "MGMT");

		this.D1 = new AbstractDoodad() {
			@Override
			public void init() {
				logTo();
			}
		};
	}

	@Override
	public void renderFrame() {
		super.renderFrame();
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
		this.renderFrame();
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

		// if (Integer.parseInt(inp) == 1) {
		if (inp.equals("1")) {
			this.manager.Session.setView(new UserLogin(this.manager));
			return true;
		}

		// if (Integer.parseInt(inp) == 2) {
		if (inp.equals("2")) {
			this.manager.Session.setView(new BankLogin(this.manager));
			return true;
		}

		this.renderFrame();
		return false;
	}

}
