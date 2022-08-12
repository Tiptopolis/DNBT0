package com.Rev.Core._Banko.MGMT;

import java.util.Date;

import com.Rev.Core._PRIM.aMap;
import com.Rev.Core._PRIM.aSet;

public class _UserProfile {

	public String firstName;
	public String lastName;
	public String email;
	public aMap<_Account, _Account.User> accounts;

	public Date customerFrom;

	public UserType type;

	public _UserProfile(String name, String email) {
		this.firstName = name;
		this.email = email;
		this.accounts = new aMap<_Account, _Account.User>();
	}

	
	public enum UserType{
		
		Customer,
		Bank;
	}
	
}
