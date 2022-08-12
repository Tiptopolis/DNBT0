package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.Log;

import com.Rev.Core._Console.ConsoleUI;
import com.Rev.Core._Console.UI.ConsoleView;

public class UserLogin extends ConsoleView {

	private String UserName = "";
	private String Password = "";

	private boolean dioUN = false;
	private boolean dioPW = false;

	public UserLogin(ConsoleUI manager) {
		super(manager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		super.init();

		this.options.put("1", "USER_ID");
		this.options.put("2", "PASSWORD");
	}

	@Override
	public void renderFrame() {

		// data
		// input options
		super.renderFrame();
		Log("" + dioPW + "  " + dioUN);
		String apOpA = "";
		String apOpB = "";
		if (this.dioUN)
			apOpA += "*";
		if (this.dioPW)
			apOpB += "*";

		Log(this.options.toString());
		Log(apOpA + "USER: " + this.UserName);
		Log(apOpB + "PASS: " + this.Password);

		Log("");
		dioUN = false;
		dioPW = false;
	}

	@Override
	public boolean handle(String inp) {

		if (super.handle(inp))
			return true;

		if (dioUN)
			this.UserName = inp;
		if (dioPW)
			this.Password = inp;

		if (inp.equals("") || inp.equals(" ") || inp == null) {
			Log("TRY LOGIN>");
			return true;
		}
		if (inp.equals("1")) {
			Log("ENTER USERNAME: ");
			this.dioUN = true;
			return dioUN;
		}
		if (inp.equals("2")) {
			Log("ENTER PASSWORD: ");
			this.dioPW = true;
			return dioPW;
		}

		this.renderFrame();

		return false;
	}

	@Override
	public void dispose() {
		super.dispose();
		dioUN = false;
		dioPW = false;
		this.UserName = "";
		this.Password = "";
	}

}
