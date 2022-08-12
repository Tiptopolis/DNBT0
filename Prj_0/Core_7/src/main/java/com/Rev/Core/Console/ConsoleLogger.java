package com.Rev.Core.Console;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.App;
import com.Rev.Core.Primitive.aSet;

public class ConsoleLogger {

	public boolean active = true;
	public static ConsoleLogger DefaultLogger;
	private static aSet<String> pending = new aSet<String>();
	private static aSet<String> toLog = new aSet<String>();

	App owner;

	//Logs console input to IDE console
	public ConsoleLogger(App owner) {
		DefaultLogger = this;
		this.owner = owner;

	}

	public static void logOut(String log) {

		Log(log);
	}

	public static void logOut() {
		for (String p : pending) {
			toLog.append(p);
		}
		for (String s : toLog) {
			Log(s);
		}
		toLog.clear();
	}

	public static void toLog(String to) {

		pending.append(to);
		// scan toLog list for Commands

	}

	public static void toLog(Object o) {
		pending.append(o.toString());
	}

	public static void toLog(Object[] os) {
		String res = "";
		for (int i = 0; i < os.length; i++) {
			res += "[" + i + "]: " + os[i].toString() + "\n";
		}
		pending.append(res);
	}

	public static void toLog(aSet<String> to) {
		for (String s : to) {
			pending.append(s);
		}
	}

}
