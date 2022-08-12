package com.Rev.Core._Console.SqlDominator;

public enum DDL implements iCmd{
	CREATE("CREATE"), ALTER("ALTER"), DROP("DROP");

	private String CommandName;
	private final String Context = "DDL";

	private DDL(String commandName) {
		this.CommandName = commandName;
	}

	public String getCommand() {
		return this.CommandName;
	}

	public String getContext() {
		return this.Context;
	}
}
