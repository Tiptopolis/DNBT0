package com.Rev.P1_Core._aBankApp;

import static com.Rev.P1_Core.AppUtils.*;

import com.Rev.P1_Core.App;

public class _BankApp extends App{

	public BankDirector Management; // data-manager
	
	public _BankApp()
	{
		super();
		
	}
	
	@Override
	public void init()
	{
		super.init();
		this.Management = new BankDirector();
		UI = new BankUI();
		Log("-<>-");
		Log(this.Management.toLog());
		Log(this.getClass().getSimpleName());
		Log(">--<");
	}
	
	public String toLog() {
		String log = "\n";
		log += this.Management + "\n";
		log += this.Management.DB_Link + "\n";
		log += UI.Session.Current + "\n";
		log += "o<[" + UI.Session.Current.getClass().getSimpleName() + "]\n";
		if (UI.Session.Previous != null)
			log += "o<  [" + UI.Session.Previous.getClass().getSimpleName() + "]\n";
		return log;
	}
}
