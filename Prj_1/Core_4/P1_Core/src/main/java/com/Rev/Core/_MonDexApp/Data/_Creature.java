package com.Rev.Core._MonDexApp.Data;

import static com.Rev.Core.Primitive.Data.aDataField.*;

import org.json.JSONObject;

import com.Rev.Core.Primitive.Data.aDataEntry;
import com.Rev.Core.Primitive.Data.aType;
import com.Rev.Core.Util.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

public class _Creature  {

	protected int ownerIndex;
	@JsonProperty
	protected String type = "";

	//features :: CSV->int[]
	
	protected float balance = 0f;
	protected int AcctNum;
	
	//////
	public static aType Creature = new aType("Creature", Object.class);

	private static boolean debug = true;
	protected int dbIndex = -1; // index of this account in DB_Table

	public _Creature(int ownerID, String type) {
		//super("Creature", aDataType.OBJ);
		this.ownerIndex = ownerID;
		this.balance = 0f;
		this.type=type;
		
	}

	public _Creature(int ownerID, String type, float balance, int acctnum) {
		//super("Creature", aDataType.OBJ);
		this.ownerIndex = ownerID;		
		this.balance = balance;
		this.AcctNum = acctnum;
		this.type=type;
	}

	public _Creature(int ownerID, String type, float balance, int acctnum, int dbIndex) {
		//super("Creature", aDataType.OBJ);
		this.ownerIndex = ownerID;
		this.balance = balance;
		this.AcctNum = acctnum;
		this.dbIndex = dbIndex;
		this.type=type;
	}

	//////
	//

	public int Owner() {
		return this.ownerIndex;
	}

	public String Type() {
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
		

		String post = "";
		if (debug)
			post += "->" + this.dbIndex;

		String s = "[" + this.type + " @" + this.AcctNum + " | " + StringUtils.toMoney(balance) + "]" + post;
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
