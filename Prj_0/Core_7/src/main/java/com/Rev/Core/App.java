package com.Rev.Core;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.Console.Console;
import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.iConsoleListener;
import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core._Banko.BankDirector;

public class App implements iConsoleListener {

	public static App Current;
	public boolean running;
	private long prevTime = System.nanoTime();
	private float deltaTime = 0f;

	public static Console AppConsole; //console input
	public static ConsoleUI UI; // view-manager

	public BankDirector Management; //data-manager



	public App() {
		//System.setProperty("java.awt.headless", "true"); // was thinking of exploring headless awt options for easier input-handling.
		App.Current = this;
		this.running = true;
		this.Management = new BankDirector();
		Log("-<>-");
		Log(this.Management.toLog());
		Log(">--<");

		AppConsole = new Console(Current);
		UI = new ConsoleUI();
		AppConsole.getSubscribers().append(this);
		
		

		try {
			this.loop();
		} finally {
			this.dispose();
		}
	}

	//not used here, but I like having it lol
	public float getDeltaTime() {
		return this.deltaTime;
	}

	//main App loop
	public void loop() {
		while (this.running) {
			long time = System.nanoTime();
			float dT = ((time - prevTime) / 1000000f);
			prevTime = time;
			this.deltaTime = dT;
			// Log(getDeltaTime());

		}

	}

	//stops running, murders the console, burns down its house
	public void terminate() {
		this.running = false;
		this.dispose();
		System.exit(0);
	}

	//kills the console thread, ensures everything is properly GC'd. 
	public void dispose() {
		AppConsole.IO.dispose();
		
		Log("D-------------------------------------------------------G");

	}

	//call from input-sender
	@Override
	public boolean input(String inp) {
		// Log(this.getClass().getSimpleName() + ":" + inp);

		UI.input(inp);
		//Log("  App.input()                                        >>>>>>");

		// Retrieving the list of column names

		return false;
	}

	
	@Override
	public iCollection<iConsoleListener> getSubscribers() {

		return UI.getSubscribers();
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

	////////////////





}
