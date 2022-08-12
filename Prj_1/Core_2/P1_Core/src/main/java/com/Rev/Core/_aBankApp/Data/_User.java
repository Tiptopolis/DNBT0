package com.Rev.Core._aBankApp.Data;

import static com.Rev.Core.AppUtils.*;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Rev.Core.Primitive.aList;
import com.Rev.Core.Primitive.Data.aDataEntry;
import com.Rev.Core.Util.StringUtils;
import com.Rev.Core._aBankApp.BankDirector;
import com.Rev.Core._aBankApp.BankDirector.UserManager;

public class _User extends aDataEntry {

	private UserManager manager;
	private String fn = "";
	private String ln = "";
	private String em = "";
	private String pw = "";
	private int index = -1;

	public _User(int index, String fn, String ln, String em, String pw) {
		super("User", aDataType.OBJ);
		this.index = index;
		this.fn = fn;
		this.ln = ln;
		this.em = em;
		this.pw = pw;
	}

	public _User(String fn, String ln, String em, String pw) {
		super("User", aDataType.OBJ);
		this.fn = fn;
		this.ln = ln;
		this.em = em;
		this.pw = pw;
	}

	//////
	//

	public String FirstName() {
		return this.fn;
	}

	public String LastName() {
		return this.ln;
	}

	public String Email() {
		return this.em;
	}

	public String Password() {
		return this.pw;
	}

	public int Index() {
		return this.index;
	}

	//
	//////

	@Override
	public String toString() {
		String s = "[" + index + "](" + StringUtils.toName(fn) + "," + StringUtils.toName(ln) + "," + em + "," + pw
				+ ")";

		return s;
	}

	//////
	// debug stoof

	
	// lists all _Users from the appropriately named table
	public static aList<_User> getUsers() {
		String query = "SELECT * FROM users";
		aList<_User> list = new aList<_User>();
		Connection connection = BankDirector.DB_Link;

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			// rs.next();
			while (rs.next()) {
				_User user = new _User(rs.getInt(1), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("password"));

				list.append(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// same as the one above, but slightly different.
	public static aList<_User> getUserIndex() {
		String query = "SELECT * FROM users";
		aList<_User> list = new aList<_User>();
		Connection connection = BankDirector.DB_Link;

		try {
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.executeQuery(query);
			rs.next();// initiate lol
			Log("_-" + rs.getInt(1));
			while (rs.next()) {

				_User user = new _User(rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"),
						rs.getString("password"));

				list.append(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
