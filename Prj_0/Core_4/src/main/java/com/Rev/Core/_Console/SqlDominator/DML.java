package com.Rev.Core._Console.SqlDominator;

public enum DML implements iCmd{

	UPDATE("UPDATE"),
	INSERT("INSERT"),
	DELETE("DELETE"),
	SELECT("SELECT");
	
	private String CommandName;
	private final String Context = "DML";
	
	private DML(String commandName)
	{
		this.CommandName = commandName;
	}
	
	public String getCommand() {
		return this.CommandName;
	}

	public String getContext() {
		return this.Context;
	}
}
