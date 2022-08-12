package com.Rev.Core._Banko;

import com.Rev.Core._Banko.MGMT._Account;

public class aTransaction {

	public void post(_Account a, float ammount) {

	}

	public enum Direction {
		Credit(1), Debit(-1);

		public final int signum;

		private Direction(int sig) {
			this.signum = sig;
		}
	}

	public enum Type {
		Deposit(Direction.Credit), Withdrawal(Direction.Debit), Expenditure(Direction.Debit);

		private Direction dir;

		private Type(Direction dir) {
			this.dir = dir;
		}

		public int signum() {
			return this.dir.signum;
		}

		public Direction getDirection() {
			return this.dir;
		}
	}

}
