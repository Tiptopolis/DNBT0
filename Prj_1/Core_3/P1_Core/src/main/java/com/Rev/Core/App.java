package com.Rev.Core;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.Console.Console;
import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.iConsoleListener;
import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core.Primitive.A_I.iDisposable;
import com.Rev.Core._aBankApp.BankDirector;
import com.Rev.Core._aBankApp.BankUI;

public class App implements iConsoleListener, iDisposable {

	public static App Current;
	public boolean running;
	protected long prevTime = System.nanoTime();
	protected float deltaTime = 0f;

	public static Console AppConsole; // console input
	public static ConsoleUI UI; // view-manager

	

	public App() {
		// System.setProperty("java.awt.headless", "true"); // was thinking of exploring
		// headlseess awt options for easier input-handling.
		App.Current = this;
		this.running = true;
		
		this.init();
		
		
		try {
			this.loop();
		} finally {
			this.dispose();
		}
	}
	
	@Override
	public void init()
	{
		AppConsole = new Console(Current);		
		AppConsole.getSubscribers().append(this);
	}

	// not used here, but I like having it lol
	public float getDeltaTime() {
		return this.deltaTime;
	}

	// main App loop
	protected void loop() {
		while (this.running) {
			long time = System.nanoTime();
			float dT = ((time - prevTime) / 1000000f);
			prevTime = time;
			this.deltaTime = dT;
			// Log(getDeltaTime());
			this.update(deltaTime);

		}

	}
	
	public void update(float deltaTime)
	{
		
	}

	// stops running, murders the console, burns down its house
	public void terminate() {
		this.running = false;
		this.dispose();
		System.exit(0);
	}

	// kills the console thread, ensures everything is properly GC'd.
	public void dispose() {

		AppConsole.IO.dispose();
		UI.dispose();
		Log("D-------------------------------------------------------G");

	}

	// call from input-sender
	@Override
	public boolean input(String inp) {
		// Log(this.getClass().getSimpleName() + ":" + inp);

		return UI.input(inp);
		// Log(" App.input() >>>>>>");
		//return false;
	}

	//new phone, who this?
	@Override
	public iCollection<iConsoleListener> getSubscribers() {
		return UI.getSubscribers();
	}

	public String toLog() {
		String log = "\n";
		log += UI.Session.Current + "\n";
		log += "o<[" + UI.Session.Current.getClass().getSimpleName() + "]\n";
		if (UI.Session.Previous != null)
			log += "o<  [" + UI.Session.Previous.getClass().getSimpleName() + "]\n";
		return log;
	}

	////////////////

}
