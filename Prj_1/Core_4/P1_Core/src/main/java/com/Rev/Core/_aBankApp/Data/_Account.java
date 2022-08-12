package com.Rev.Core._aBankApp.Data;

import static com.Rev.Core.Primitive.Data.aDataField.*;

import com.Rev.Core.Primitive.Data.aDataEntry;
import com.Rev.Core.Util.StringUtils;

public class _Account extends aDataEntry {

	protected int ownerIndex;
	protected int type;

	protected float balance = 0f;
	protected int AcctNum;

	private static boolean debug = true;
	protected int dbIndex = -1; // index of this account in DB_Table

	public _Account(int ownerID, int type) {
		super("Account", aDataType.OBJ);
		this.ownerIndex = ownerID;
		this.type = type;
		this.balance = 0f;
	}

	public _Account(int ownerID, int type, float balance, int acctnum) {
		super("Account", aDataType.OBJ);
		this.ownerIndex = ownerID;
		this.type = type;
		this.balance = balance;
		this.AcctNum = acctnum;
	}

	public _Account(int ownerID, int type, float balance, int acctnum, int dbIndex) {
		super("Account", aDataType.OBJ);
		this.ownerIndex = ownerID;
		this.type = type;
		this.balance = balance;
		this.AcctNum = acctnum;
		this.dbIndex = dbIndex;
	}

	//////
	//

	public int Owner() {
		return this.ownerIndex;
	}

	public int dbType() {
		return this.type;
	}

	public float Balance() {
		return this.balance;
	}

	public float Balance(float f) {
		this.balance = f;
		return this.balance;
	}

	public int dbIndex() {
		return this.dbIndex;
	}

	//
	//////

	@Override
	public String toString() {

		String typeName = "";
		if (type == 0)
			typeName += "Checking";
		else
			typeName += "Savings";

		String post = "";
		if (debug)
			post += "->" + this.dbIndex;

		String s = "[" + typeName + " @" + this.AcctNum + " | " + StringUtils.toMoney(balance) + "]" + post;
		return s;
	}

	public void deposit(float amt) {
		this.crebit(Math.abs(amt));
	}

	public void withdraw(float amt) {
		float transactionFee = 4f;
		this.crebit(-Math.abs(amt));
	}

	protected void crebit(float amt) {
		this.balance += amt;
	}

}
