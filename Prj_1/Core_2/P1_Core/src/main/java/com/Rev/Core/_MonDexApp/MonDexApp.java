package com.Rev.Core._MonDexApp;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.App;
import com.Rev.Core.Console.Console;

public class MonDexApp extends App{

	public MonDexApp()
	{
		super();
	}
	
	
	@Override
	public void init()
	{
		super.init();
		Log("-<>-");
		UI = new MonDexUI();
		Log(this.getClass().getSimpleName());
		Log(">--<");
	}
}
