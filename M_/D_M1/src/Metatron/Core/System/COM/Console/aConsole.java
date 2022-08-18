
package Metatron.Core.System.COM.Console;

import static Metatron.Core._M.M_Utils.*;

import java.io.IOException;
import java.util.Map;

import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.System.IO_Port;
import Metatron.Core.System.aShell;
import Metatron.Core.System.Event.iEvent;
import Metatron.Core.System.Event.iEventHandler;

public class aConsole implements iEventHandler {

	// holds the input thread for non-blocking console input
	//

	protected aShell Target;
	public static aConsoleLogger Logger;
	public IO_Port IO;

	protected Thread ConsoleInputThread;
	public boolean echo = false;

	public aList<iEventHandler> Subscribers = new aList<iEventHandler>();
	
	

	public aConsole(aShell target) {
		this.Target = target;
		this.IO = new IO_Port(target);
		Logger = new aConsoleLogger(target);
		this.Subscribers.append(target);
		// create & start IO thread
		ConsoleInputThread = new Thread("ConsoleThread") {
			@Override
			public void run() {
				if (Target.isActive() && Target != null) {
					try {
						aConsole.this.consoleLoop();
					} catch (Throwable t) {
						throw new RuntimeException(t);
					}
				}
			}
		};
		ConsoleInputThread.start();
	}

	// console loop?
	public void consoleLoop() throws IOException {
		System.out.println("_CONSOLE LOOP START");
		String tmp = ":";
		System.out.flush();
		while (this.Target.isActive()) {// STEP INSTRUCTIONS

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

	// post input to ConsoleLogger's pending output
	public static void post(String input) {
		if (Logger != null && Logger.active) {
			aConsoleLogger.toLog(input);
			aConsoleLogger.logOut();
			
		}
	}

	public static void post(Object o) {
		String r = "" + o.toString();
		post(r);
	}

	// take & handle console input, passes to subscribed listeners
	public boolean input(String msg) {

		if (equalsAny(msg,"SHELL:TERMINATE",":TERMINATE")) {
			Log(this.toLog());
			post("SHELL:TERMINATE");
			Target.dispose();

		}

		if (msg.equals(":LOG")) {
			post(":LOG");
			Log(this.toLog());

		}

		if (msg.equals("APP:LOG")) {
			Log(this.Target.toLog());
		}

		for (iEventHandler c : this.getSubscribers()) {
			if (c.handle(msg))
				return true;
		}
		return false;
	}

	// get listeners
	public iCollection<iEventHandler> getSubscribers() {
		if (this.Subscribers == null)
			this.Subscribers = new aList<iEventHandler>();
		return this.Subscribers;
	}

	@Override
	public boolean handle(iEvent o) {
		for (iEventHandler H : this.getSubscribers()) {
			boolean b = H.handle(o);
			if (b)
				return b;
		}
		return false;
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