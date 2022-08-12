package com.Rev.Core._aBank.Views;

import static com.Rev.Core.AppUtils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core.Util.StringUtils;
import com.Rev.Core._aBank.BankDirector;
import com.Rev.Core._aBank.Data._User;
import com.Rev.Core.zCHEAT_CODEX.VariousTests;

public class UserLogin extends aConsoleView {

	private String Email = "";
	private String Password = "";

	private boolean dioEM = false;
	private boolean dioPW = false;

	public UserLogin(ConsoleUI manager) {
		super(manager);
	}

	@Override
	public void init() {
		super.init();

		this.options.put("1", "EMAIL");
		this.options.put("2", "PASSWORD");
	}

	// print to console
	@Override
	public void render() {

		// data
		// input options
		super.render();
		Log("" + dioPW + "  " + dioEM);
		String apOpA = "";
		String apOpB = "";
		if (this.dioEM)
			apOpA = "*";
		if (this.dioPW)
			apOpB = "*";
		// ^none of this * marking $#!% works right lol

		Log(this.options.toString());
		Log(apOpA + "EMAIL: " + this.Email);
		Log(apOpB + "PASS: " + this.Password);

		Log("");
		dioEM = false;
		dioPW = false;
	}

	// handle incoming console input
	@Override
	public boolean handle(String inp) {

		if (!dioEM && !dioPW)
			if (super.handle(inp))
				return true;

		if (dioEM)
			this.Email = inp;
		if (dioPW)
			this.Password = inp;

		if (inp.equals("") || inp.equals(" ") || inp.equals(".") || inp == null) {
			Log("TRY LOGIN>");
			this.submit();
			return true;
		}
		if (inp.equals("1") || inp.equals("EMAIL")) {
			Log("ENTER EMAIL: ");
			this.dioEM = true;
			return dioEM;
		}
		if (inp.equals("2") || inp.equals("PASSWORD")) {
			Log("ENTER PASSWORD: ");
			this.dioPW = true;
			return dioPW;
		}

		this.render();

		return false;
	}

	@Override
	public void dispose() {
		super.dispose();
		dioEM = false;
		dioPW = false;
		this.Email = "";
		this.Password = "";
	}

	//////
	//

	private void submit() {

		String query = "SELECT * FROM users WHERE email=? AND password=?";

		Connection connection = BankDirector.DB_Link;
		int i = -1;
		try {

			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, Email);
			pst.setString(2, Password);

			ResultSet rs = pst.executeQuery();
			Log();

			int u_id = -1;
			String fn = "";
			String ln = "";
			String em = "";
			String pw = "";

			while (rs.next()) {
				i++;
				Log("--[" + rs.getInt(1) + "]" + rs.getString("email") + " " + rs.getString("password")); // accout got
				u_id = (rs.getInt("user_ID"));
				fn = (rs.getString("first_name"));
				ln = (rs.getString("last_name"));
				em = (rs.getString("email"));
				pw = (rs.getString("password"));
			}

			if (u_id != -1) {
				this.manager.Session.loggedAs = new _User(u_id, fn, ln, em, pw);
				this.manager.Session.setView(new UserView(this.manager));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// redundant

		if (i == -1) {
			Log("USER NOT FOUND");
			return;
		}

		// VariousTests.Testo.logTestLinkedList();
		// Log(_User.getUserIndex().toString());
		// Log("------------------");
		// Log(StringUtils.toMoney(8675309.21f));

	}

}
