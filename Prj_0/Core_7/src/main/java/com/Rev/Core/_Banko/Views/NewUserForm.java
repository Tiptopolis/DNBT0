package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import com.Rev.Core.AppUtils;
import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core.Util.StringUtils;
import com.Rev.Core._Banko.BankDirector;
import com.Rev.Core._Banko.Data._User;

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
		this.options.put("C", "-CLEAR-");
		this.options.put(".", "-SUBMIT-");
	}

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
			Log("*INVALID INPUT <<<");

		this.inputInvalid = false;
		dioFN = false;
		dioLN = false;
		dioPW = false;
		dioEM = false;
	}

	@Override
	public boolean handle(String inp) {

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
				ThrowFancyException(" >>INVALID EMAIL<<"); // exceptions are for nerds lol
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

		if (inp.equals("C")) {
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

	/////////////////////////////////

	private void submit() {
		if (this.FirstName.equals("") || this.LastName.equals("") || this.Password.equals("")
				|| this.Email.equals("")) {
			Log("MISSING INFO");
			return;
		} else {
			if (StringUtils.validEmail(Email)) {
				_User newUser = new _User(this.FirstName, this.LastName, this.Email, this.Password);
				BankDirector.Users.create(newUser);
				newUser.clear();
				this.clear();
				this.input("^");
			} else // exceptions are for nerds lol
			{
				ThrowFancyException("INVALID EMAIL");
				return;
			}

		}
	}

	/////////////////////////////////
	private void submitX() {
		// String SQL = "INSERT INTO actor(first_name,last_name) "
		// + "VALUES(?,?)";
		if (this.FirstName.equals("") || this.LastName.equals("") || this.Password.equals("")
				|| this.Email.equals("")) {
			Log("MISSING INFO");
			return;
		} else {
			// create new _User, pass it to BankDirector.UserManager
			// Log("INSERT INTO users(FirstName, LastName, Username, Email)
			// VALUES(?,?,?,?)");
			try {
				Connection c = BankDirector.DB_Link;
				String sql = "INSERT INTO users(first_name, last_name, email, password, routing_num) VALUES(?,?,?,?,?)";

				PreparedStatement pS = c.prepareStatement(sql);
				// Statement stm = BankDirector.DB_Link.createStatement();
				// stm.executeUpdate(sql);
				pS.setString(1, this.FirstName);
				pS.setString(2, this.LastName);
				pS.setString(3, this.Email);
				pS.setString(4, this.Password);
				pS.setInt(5, this.hashCode());

				pS.executeUpdate();
				// String a = this.FirstName + "," + this.LastName + "," + this.Email + "," +
				// this.Password + ","
				// + this.hashCode();
				// Log("INSERT INTO users(first_name, last_name, email, password, routing_num)
				// VALUES(" + a + ")");
				this.clear();
				this.input("^");
				// Log("WELCOME " + this.FirstName + " " + this.LastName);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
