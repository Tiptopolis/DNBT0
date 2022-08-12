package com.Rev.P1_Core._MonDexApp.Views;

import static com.Rev.P1_Core.AppUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import com.Rev.P1_Core.AppUtils;
import com.Rev.P1_Core.Console.ConsoleUI;
import com.Rev.P1_Core.Console.UI.aConsoleView;
import com.Rev.P1_Core.Util.StringUtils;
import com.Rev.P1_Core._MonDexApp.MonDirector;
import com.Rev.P1_Core._aBankApp.Data._User;

public class NewUserForm extends aConsoleView {

	// regex is voodoo sorcery lol

	private String FirstName = "";
	private boolean dioFN = false;
	private String LastName = "";
	private boolean dioLN = false;
	private String Email = "";
	private boolean dioEM = false;
	private String Password = "";
	private boolean dioPW = false;

	private boolean inputInvalid = false;
	private String specificMessageLol = "*INVALID INPUT <<<";

	public NewUserForm(ConsoleUI manager) {
		super(manager);

	}

	@Override
	public void init() {
		super.init();

		this.options.put("1", "FIRST_NAME");
		this.options.put("2", "LAST_NAME");
		this.options.put("3", "EMAIL");
		this.options.put("4", "PASSWORD");
		this.options.put("*", "-CLEAR-");
		this.options.put(".", "-SUBMIT-");
	}

	// print to console
	@Override
	public void render() {

		super.render();

		Log(this.options.toString());
		Log("FIRST_NAME: " + this.FirstName);
		Log("LAST_NAME: " + this.LastName);
		Log("E-MAIL:" + this.Email);
		Log("PASSWORD: " + this.Password);

		Log("");
		if (inputInvalid)
			Log(specificMessageLol);
		// Log("*INVALID INPUT <<<");

		this.inputInvalid = false;
		dioFN = false;
		dioLN = false;
		dioPW = false;
		dioEM = false;
		specificMessageLol = "*INVALID INPUT <<<";
	}

	// handle incoming console input
	@Override
	public boolean handle(String inp) {

		if (!dioFN && !dioLN && !dioEM && !dioPW) // no diologueues open
			if (super.handle(inp))
				return true;

		if (dioFN)
			this.FirstName = inp.replaceAll("[^a-zA-Z]", " ");
		// this.FirstName = inp.replaceAll("\\d", ""); //no numbers allowed lol
		if (dioLN)
			this.LastName = inp.replaceAll("[^a-zA-Z]", " ");
		// this.LastName = inp.replaceAll("\\d", "");
		if (dioEM) {
			if (StringUtils.validEmail(inp))
				this.Email = inp;
			else {
				this.inputInvalid = true;
				this.specificMessageLol = "*INVALID EMAIL <<<";
				ThrowFancyException(specificMessageLol); // exceptions are for nerds lol
				return false;
			}
		}
		if (dioPW)
			this.Password = inp;

		if (inp.equals(".") || inp.equals("") || inp.equals(" ") || inp.equals("SUBMIT") || inp == null) {
			Log("TRY SUBMIT>");
			this.submit();
			return true;
		}
		// need a static boolean caseEquals(inp, Str);
		if (inp.equals("1") || inp.equals("FIRST NAME") || inp.equals("FIRST_NAME") || inp.equals("FIRSTNAME")) {
			Log("ENTER FIRST_NAME: ");
			this.dioFN = true;
			return dioFN;
		}
		if (inp.equals("2") || inp.equals("LAST NAME") || inp.equals("LAST_NAME") || inp.equals("LASTNAME")) {
			Log("ENTER LAST_NAME: ");
			this.dioLN = true;
			return dioLN;
		}
		if (inp.equals("3") || inp.equals("EMAIL")) {
			Log("ENTER EMAIL: ");
			this.dioEM = true;
			return dioEM;
		}
		if (inp.equals("4") || inp.equals("PASSWORD")) {
			Log("ENTER PASSWORD: ");
			this.dioPW = true;
			return dioPW;
		}

		if (inp.equals("*")) {
			this.clear();
		}

		this.render();

		return false;
	}

	public void clear() {
		this.FirstName = "";
		this.LastName = "";
		this.Password = "";
		this.Email = "";
	}

	@Override
	public void dispose() {
		this.clear();
		super.dispose();
	}

	//////
	//

	private void submit() {
		if (this.FirstName.equals("") || this.LastName.equals("") || this.Password.equals("")
				|| this.Email.equals("")) {
			Log("MISSING INFO");
			return;
		} else {
			if (StringUtils.validEmail(Email)) {
				_User newUser = new _User(this.FirstName, this.LastName, this.Email, this.Password);
				MonDirector.Users.create(newUser);
				newUser.clear();
				this.clear();
				this.input("^"); //casts the 'OUT' spell from the options list
			} else // exceptions are for nerds lol
			{
				this.specificMessageLol = "*INVALID EMAIL <<<";
				ThrowFancyException(specificMessageLol);
				return;
			}

		}
	}
}
