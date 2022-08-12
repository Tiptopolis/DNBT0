package com.Rev.Core.Console;

import static com.Rev.Core.AppUtils.*;

import java.io.IOException;
import java.util.Map;

import com.Rev.Core.App;
import com.Rev.Core.Console.UI.iConsoleListener;
import com.Rev.Core.Primitive.aList;
import com.Rev.Core.Primitive.A_I.iCollection;

public class Console implements iConsoleListener {

	// holds the input thread for non-blocking console input
	//

	protected App Target;
	public static ConsoleLogger Logger;
	public ConsoleIO IO;

	protected Thread ConsoleInputThread;
	public boolean echo = false;

	public aList<iConsoleListener> Subscribers = new aList<iConsoleListener>();

	public Console(App target) {
		this.Target = target;
		this.IO = new ConsoleIO(target);
		Logger = new ConsoleLogger(target);

		ConsoleInputThread = new Thread("ConsoleThread") {
			@Override
			public void run() {
				if (Target == null && App.Current != null)
					Target = App.Current;
				if (Target.running && Target != null) {
					try {
						Console.this.consoleLoop();
					} catch (Throwable t) {
						throw new RuntimeException(t);
					}
				}
			}
		};
		ConsoleInputThread.start();
	}

	public void consoleLoop() throws IOException {
		System.out.println("_CONSOLE LOOP START");
		String tmp = ":";
		System.out.flush();
		while (this.Target.running) {// STEP INSTRUCTIONS

			synchronized (IO) {
				tmp = IO.readLine();

				// post(tmp);

				if (echo)
					System.out.println("$&: [" + tmp + "]");

			}
			boolean b = this.input(tmp);
			System.in.mark(0);
			System.in.reset();

			// System.out.println("Console Loop Executed Successfully");

		}
		System.out.println("Shell Teminated");
	}

	public static void post(String input) {
		if (Logger != null && Logger.active) {
			ConsoleLogger.toLog(input);
			ConsoleLogger.logOut();
		}

	}

	public boolean input(String msg) {

		if (msg.equals("SHELL:TERMINATE")) {
			Log(this.toLog());
			post("SHELL:TERMINATE");
			Target.terminate();
		}

		if (msg.equals(":LOG")) {
			post(":LOG");
			Log(this.toLog());

		}

		if (msg.equals("APP:LOG")) {
			Log(App.Current.toLog());
		}

		for (iConsoleListener c : this.getSubscribers()) {
			if (c.input(msg))
				return true;
		}
		return false;
	}

	@Override
	public iCollection<iConsoleListener> getSubscribers() {
		if (this.Subscribers == null)
			this.Subscribers = new aList<iConsoleListener>();
		return this.Subscribers;
	}

	public static void post(Object o) {
		String r = "" + o.toString();
		post(r);
	}

	public String toLog() {

		String log = "";

		log += this.Target.toLog();
		log += "\n";
		log += "#ThreadsActive- " + java.lang.Thread.activeCount();
		log += "\n";
		// log += ""+java.lang.Thread.getAllStackTraces();
		Map<Thread, StackTraceElement[]> threads = java.lang.Thread.getAllStackTraces();
		for (Map.Entry<Thread, StackTraceElement[]> t : threads.entrySet()) {

			if (!t.getKey().isDaemon()) {
				log += t.toString();
				log += "\n";
			}

		}
		log += "\n";

		return log;
	}
}
