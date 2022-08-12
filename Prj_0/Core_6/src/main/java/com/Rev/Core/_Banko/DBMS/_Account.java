package com.Rev.Core._Banko.DBMS;

import static com.Rev.Core.Primitive.Data.aDataField.*;

import com.Rev.Core.Primitive.Data.aDataEntry;
import com.Rev.Core._Banko.Util.StringUtils;

public class _Account extends aDataEntry {

	protected int ownerIndex;
	protected AccountType type;

	protected float balance = 0f;
	protected int AcctNum;

	protected int dbIndex; // index of this account in DB_Table

	public _Account(int ownerID, int type, float balance) {
		super("Account", aDataType.OBJ);
		this.ownerIndex = ownerID;
		int t = type % 1;
		if (t == 0)
			this.type = AccountType.C;
		else
			this.type = AccountType.S;

	}

	public int Owner() {
		return this.ownerIndex;
	}

	public AccountType Type() {
		return this.type;
	}

	public float Balance() {
		return this.balance;
	}

	@Override
	public String toString() {

		String s = "[" + this.type.getName() + " " + this.AcctNum + " | " + StringUtils.toMoney(balance) + "]";
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

	public static enum AccountType {
		C(0, "Checking"), S(1, "Savings");

		private final int index;
		private final String name;

		private AccountType(int typeIndex, String typeName) {
			this.index = typeIndex;
			this.name = typeName;

		}

		public int getIndex() {
			return this.index;
		}

		public String getName() {
			return this.name;
		}

		/////
	}

}
