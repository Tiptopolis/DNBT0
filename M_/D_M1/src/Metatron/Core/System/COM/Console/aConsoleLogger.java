package Metatron.Core.System.COM.Console;



import static Metatron.Core._M.M_Utils.*;

import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.System.Event.iEventHandler;


public class aConsoleLogger {

	public boolean active = true;
	public static aConsoleLogger DefaultLogger;
	private static aSet<String> pending = new aSet<String>();

	public iEventHandler owner;
	
	

	//Logs console input to IDE console
	public aConsoleLogger(iEventHandler owner) {
		DefaultLogger = this;
		this.owner = owner;

	}
	
	//append to pending
	public static void toLog(String to) {

		pending.append(to);
		// scan toLog list for Commands

	}

	//append to pending
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

	//flushes pending -> toLog and toLog -> System.out
	public static void logOut(String log) {

		toLog(log);
		logOut();
	}

	//flushes pending -> toLog and toLog -> System.out
	public static void logOut() {
		for (String p : pending) {
			Log(p);
		}
		
		pending.clear();
	}



}