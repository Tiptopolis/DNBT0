package com.Rev.Core._Banko.Views;

import static com.Rev.Core.AppUtils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import com.Rev.Core._Banko.BankDirector;
import com.Rev.Core._Console.ConsoleUI;
import com.Rev.Core._Console.UI.ConsoleView;

public class NewUserForm extends ConsoleView {

	// regex is voodoo sorcery lol
	private final String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	private String FirstName = "";
	private boolean dioFN = false;
	private String LastName = "";
	private boolean dioLN = false;
	private String Email = "";
	private boolean dioEM = false;
	private String Password = "";
	private boolean dioPW = false;

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
	public void renderFrame() {

		super.renderFrame();

		Log(this.options.toString());
		Log("FIRST_NAME: " + this.FirstName);
		Log("LAST_NAME: " + this.LastName);
		Log("E-MAIL:" + this.Email);
		Log("PASSWORD: " + this.Password);

		Log("");
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
			this.FirstName = inp;
		if (dioLN)
			this.LastName = inp;
		if (dioEM && validEmail(inp))
			this.Email = inp;
		if (dioPW)
			this.Password = inp;

		if (inp.equals(".") || inp.equals("") || inp.equals(" ") || inp == null) {
			Log("TRY SUBMIT>");
			this.submit();
			return true;
		}
		if (inp.equals("1")) {
			Log("ENTER FIRST_NAME: ");
			this.dioFN = true;
			return dioFN;
		}
		if (inp.equals("2")) {
			Log("ENTER LAST_NAME: ");
			this.dioLN = true;
			return dioLN;
		}
		if (inp.equals("3")) {
			Log("ENTER EMAIL: ");
			this.dioEM = true;
			return dioEM;
		}
		if (inp.equals("4")) {
			Log("ENTER PASSWORD: ");
			this.dioPW = true;
			return dioPW;
		}

		if (inp.equals("C")) {
			this.clear();
		}

		this.renderFrame();

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
		super.dispose();
		this.FirstName = "";
		this.LastName = "";
		this.Password = "";
		this.Email = "";
	}

	/////////////////////////////////

	private void submit() {
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
				pS.setString(5, "" + this.hashCode());

				pS.executeUpdate();
				String a = this.FirstName + "," + this.LastName + "," + this.Email + "," + this.Password + ","
						+ this.hashCode();
				Log("INSERT INTO users(first_name, last_name, email, password, routing_num) VALUES(" + a + ")");
				this.clear();
				this.input("^");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private boolean validEmail(String emailAddress) {
		return patternMatches(emailAddress, emailRegexPattern);
	}

	/////////////////////////////////
	public static boolean patternMatches(String emailAddress, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(emailAddress).matches();
	}

}
