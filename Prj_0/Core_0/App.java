package Core;

import static Core.AppUtils.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import Core._Banko.aTransaction;
import Core._Banko.MGMT.AccountManager;
import Core._Console.Console;
import Core._Console.ConsoleUI;
import Core._Console.UI.iConsoleListener;
import Core._PRIM.aSet;
import Core._PRIM.iCollection;
import Core._PRIM.aMap;
import Core._PRIM.aMultiMap;
import Core._PRIM.aList;

public class App implements iConsoleListener {

	public static App Current;
	public boolean running;
	private long prevTime = System.nanoTime();
	private float deltaTime = 0f;

	public static Console AppConsole;
	public static ConsoleUI UI;

	public AccountManager Accounts;

	aList<Integer> L = new aList<Integer>();
	aSet<Integer> S = new aSet<Integer>();
	aMap<String, String> M = new aMap<String, String>();
	aMultiMap<String, String> MM = new aMultiMap<String, String>();

	public App() {
		App.Current = this;
		this.running = true;
		AppConsole = new Console(Current);
		
		UI = new ConsoleUI();
		AppConsole.getSubscribers().add(this);
		
		this.Accounts = new AccountManager();

		genTestSet();
		genTestList();
		genTestMap();
		genTestMultiMap();

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
			// logTestSet();
			// logTestList();
			// logTestMap();
			// logTestMultiMap();
			// Log(this.toLog());

			// aTransaction.Type T = aTransaction.Type.Deposit;
			// Log(T.getDirection() + " " + T.signum());
			// T = aTransaction.Type.Withdrawal;
			// Log(T.getDirection() + " " + T.signum());
		}

	}

	public void terminate() {
		this.running = false;
		this.dispose();
		System.exit(0);
	}

	public void dispose() {
		Log("D-------------------------------------------------------G");

	}

	@Override
	public boolean input(String inp) {
		Log(this.getClass().getSimpleName() + ":" + inp);
		UI.input(inp);
		
		return false;
	}

	@Override
	public iCollection<iConsoleListener> getSubscribers() {
		
		return UI.getSubscribers();
	}

	public String toLog() {
		String log = "\n";
		log += this.Accounts + "\n";
		log += this.Accounts.DB_Link + "\n";
		return log;
	}

	////////////////

	private static class ShutDownHook extends Thread {
		public void run() {
			Current.terminate();
		}
	}

	private void SEC0_TESTING() {
	}

	public void genTestSet() {
		S = new aSet<Integer>();
		S.add(1, 1);
		S.add(1);
		S.add(32);
		S.add(64);
		S.add(666);
		S.remove(2);
	}

	public void genTestList() {
		L = new aList<Integer>();
		L.add(1, 1);
		L.add(1);
		L.add(32);
		L.add(64);
		L.add(666);

		L.insert(42, 1);
		L.set(0, 100);
		// L.clear();
	}

	public void genTestMap() {
		M = new aMap<String, String>();
		M.put("A", "1");
		M.put("A", "2");
		M.put("A", "1");
		M.put("B", "1");
		M.put("B", "2");
		M.put("A", "5");
		M.put("A", "5");
		M.put("A", "1");

	}

	public void genTestMultiMap() {
		MM = new aMultiMap<String, String>();
		MM.put("A", "1");
		MM.put("A", "2");
		MM.put("A", "1");
		MM.put("B", "1");
		MM.put("B", "2");
		MM.put("A", "5");
		MM.put("A", "5");
		MM.put("A", "1");

	}

	public void logTestSet() {
		Log("aSet>>");
		Log("for{");
		Log(S);
		Log("forEach{");
		for (Integer i : this.S) {
			Log("*[" + S.indexOf(i) + "]" + i);
		}
		Log("----");
	}

	public void logTestList() {
		Log("aList>>");
		Log("for{");
		Log(L);
		Log("forEach{");
		for (Integer i : this.L) {
			Log("*[" + L.indexOf(i) + "]" + i);
		}
		Log("----");
	}

	public void logTestMap() {
		// needs to sort lol
		Log("aMap>>");
		Log(M);
		Log("All: A");
		aList r = M.pull("A");
		Log(r);
		Log("----");

	}

	public void logTestMultiMap() {
		// needs to sort lol
		Log("aMultiMap");
		Log(MM);
		Log("All: A");
		// aList r = (aList) MM.pull("A");
		Log(MM.pull("A"));
		Log(MM.pull("A").get(2));
		Log("----");

	}

}
