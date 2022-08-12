package Core._Banko;

import Core._PRIM.aList;

public class _Account {

	public aList<_UserProfile> ValidUsers = new aList<_UserProfile>();

	protected final Type AccountType;
	protected final int AccountNumber;
	protected final int RouttingNumber;

	public float fundage = 0;

	public _Account(_UserProfile user, Type type) {
		this.ValidUsers.append(user);
		this.AccountType = type;
		this.AccountNumber = this.genAcctNum();
		this.RouttingNumber = this.genRtnNum();
	}

	private int genAcctNum() {
		return 0;
	}

	private int genRtnNum() {
		return 0;
	}

	public void addValidUser(_UserProfile user, _Account.User as) {
		this.ValidUsers.append(user);
		user.accounts.put(this, as);

	}

	public String toString() {
		return this.AccountType.pfx + this.AccountNumber + "::" + this.RouttingNumber;
	}

	public enum User {
		Owner, Access;
	}

	public enum Type {
		Checking("CH"), Savings("SA"), Credit("CR");

		private String pfx;

		private Type(String pfx) {
			this.pfx = pfx;
		}

		public String getPrefix() {
			return this.pfx;
		}
	}
}
