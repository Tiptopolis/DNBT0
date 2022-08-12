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

	public static Console AppConsole;
	public static ConsoleUI UI;

	public BankDirector Management;



	public App() {
		System.setProperty("java.awt.headless", "true");
		App.Current = this;
		this.running = true;
		this.Management = new BankDirector();
		Log("-<>-");
		Log(this.Management.toLog());
		Log(">--<");

		AppConsole = new Console(Current);
		UI = new ConsoleUI();
		AppConsole.getSubscribers().append(this);



		// B_SortTest(); //werx

		try {
			this.loop();
		} finally {
			this.dispose();
		}
	}

	public float getDeltaTime() {
		return this.deltaTime;
	}

	public void loop() {
		while (this.running) {
			long time = System.nanoTime();
			float dT = ((time - prevTime) / 1000000f);
			prevTime = time;
			this.deltaTime = dT;
			// Log(getDeltaTime());

		}

	}

	public void terminate() {
		this.running = false;
		this.dispose();
		System.exit(0);
	}

	public void dispose() {
		AppConsole.IO.dispose();
		
		Log("D-------------------------------------------------------G");

	}

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
