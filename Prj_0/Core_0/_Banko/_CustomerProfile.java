package Core._Banko;

import java.util.Date;

import Core._PRIM.aMap;
import Core._PRIM.aSet;

public class _CustomerProfile {

	public String name;
	public String email;
	public aMap<_Account, _Account.User> accounts;

	public Date customerFrom;

	public _CustomerProfile(String name, String email) {
		this.name = name;
		this.email = email;
		this.accounts = new aMap<_Account, _Account.User>();
	}

}
