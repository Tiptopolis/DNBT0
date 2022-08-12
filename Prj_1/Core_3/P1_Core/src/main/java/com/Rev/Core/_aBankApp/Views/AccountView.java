package com.Rev.Core._aBankApp.Views;

import static com.Rev.Core.AppUtils.Log;
import static com.Rev.Core.AppUtils.ThrowFancyException;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core.Primitive.aMap;
import com.Rev.Core.Util.StringUtils;
import com.Rev.Core._Math.Maths;
import com.Rev.Core._aBankApp.BankDirector;
import com.Rev.Core._aBankApp.Data._Account;

public class AccountView extends aConsoleView {

	// Account Interactor
	// deposit, withdraw

	private _Account as;

	private float deltaBal = 0f;

	private boolean dioD = false;
	private boolean dioW = false;

	private boolean inputInvalid = false;

	public AccountView(ConsoleUI manager, _Account as) {
		super(manager);
		this.as = as;
	}

	@Override
	public void init() {
		super.init();

		// this.options = new aMap<String, String>();
		// this.options.put("^", "OUT");
		// this.options.put("X", "EXIT");

		// super.init();
		this.options = new aMap<String, String>();
		this.options.put("^", "OUT");
		this.options.put("<", "BACK");
		this.options.put("X", "EXIT");

		this.options.put("1", "DEPOSIT");
		this.options.put("2", "WITHDRAW");
		// this.options.put("2", "CLOSE ACCT"); //call customer service for that

	}

	/////////////////

	// print to console
	@Override
	public void render() {
		super.render();

		Log(this.as.toString());
		Log("___________");
		Log(this.options.toString());
		Log("");
		if (inputInvalid)
			Log("*INVALID INPUT <<<");

		this.inputInvalid = false;
		this.dioD = false;
		this.dioW = false;
	}

	// handle incoming console input
	@Override
	public boolean handle(String inp) {

		if (!dioD && !dioW)
			if (super.handle(inp))
				return true;

		if (dioD) {
			if (StringUtils.validAmount(inp)) {
				float f = Float.parseFloat(inp);
				this.deposit(Maths.round(f, 2));
			} else {
				this.inputInvalid = true;
				ThrowFancyException(" >>INVALID AMOUNT<<");
				return false;
			}

		}

		if (dioW) {

			//
			if (StringUtils.validAmount(inp)) {
				float f = Float.parseFloat(inp);
				this.withdraw(f);
			} else {
				this.inputInvalid = true;
				ThrowFancyException(" >>INVALID AMOUNT<<");
				return false;
			}

		}

		if (inp.equals("1")) {
			Log("*DEPOSIT");
			Log("ENTER AMOUNT: ");
			this.dioD = true;
			return dioD;
		}

		if (inp.equals("2")) {
			Log("*WITHDRAW");
			Log("ENTER AMOUNT: ");
			this.dioW = true;
			return dioW;
		}

		return false;
	}

	//////
	//

	public void deposit(float amt) {
		this.crebit(Math.abs(amt));
	}

	public void withdraw(float amt) {
		this.crebit(-(Math.abs(amt)));
	}

	private void crebit(float amt) {
		// submit lol
		// refresh & comit

		this.deltaBal += amt;
		float current = as.Balance(as.Balance() + deltaBal);
		this.deltaBal = 0;
		BankDirector.Accounts.update(as.dbIndex(), as);
	}

}
