package com.Rev.Core._Banko.DBMS;

import static com.Rev.Core._PRIM.Data.aDataField.*;

import com.Rev.Core._Banko.BankDirector.AccountManager;
import com.Rev.Core._Math.Maths;
import com.Rev.Core._PRIM.Data.aDataField;

public class _Account extends aDataField<_Account> {

	protected AccountManager manager;
	protected int ownerIndex;
	protected AccountType type;

	protected String Owner = "";
	protected float Balance = 0f;
	protected int AcctNum;

	protected int dbIndex; // index of this account in DB_Table

	public _Account(AccountManager manager, int ownerID, int type) {
		super("Account", aDataType.OBJ);
		this.ownerIndex = ownerID;
		int t = type % 1;
		if (t == 0)
			this.type = AccountType.C;
		else
			this.type = AccountType.S;
		this.manager = manager;
		this.AcctNum = this.hashCode();
		manager.create(this);
	}

	@Override
	public String toString() {
		String bal = "" + Maths.round(this.Balance, 2); // need to currency format lol
		String s = "[" + this.type.getName() + " " + this.AcctNum + " | $" + bal + "]";
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
		this.Balance += amt;
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
