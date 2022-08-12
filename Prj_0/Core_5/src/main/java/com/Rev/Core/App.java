package com.Rev.Core;

import static com.Rev.Core.AppUtils.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Random;

import com.Rev.Core._Banko.BankDirector;
import com.Rev.Core._Console.Console;
import com.Rev.Core._Console.ConsoleUI;
import com.Rev.Core._Console.UI.iConsoleListener;
import com.Rev.Core._Math.Maths;
import com.Rev.Core._Math.aVector;
import com.Rev.Core._PRIM.aSet;
import com.Rev.Core._PRIM.A_I.iCollection;
import com.Rev.Core._PRIM.Data.aDataField;
import com.Rev.Core._PRIM.aMap;
import com.Rev.Core._PRIM.aMultiMap;
import com.Rev.Core._PRIM.aNode;
import com.Rev.Core._PRIM.aConnection;
import com.Rev.Core._PRIM.aLinkedList;
import com.Rev.Core._PRIM.aList;

public class App implements iConsoleListener {

	public static App Current;
	public boolean running;
	private long prevTime = System.nanoTime();
	private float deltaTime = 0f;

	public static Console AppConsole;
	public static ConsoleUI UI;

	public BankDirector Management;

	aSet<Integer> S = new aSet<Integer>();
	aList<Integer> L = new aList<Integer>();
	aLinkedList<Integer> LL = new aLinkedList<Integer>();
	aMap<String, String> M = new aMap<String, String>();
	aMultiMap<String, String> MM = new aMultiMap<String, String>();

	aNode N1;
	aNode N2;
	aNode N3;

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

		genTestSet();
		genTestList();
		genTestLinkedList();
		genTestMap();
		genTestMultiMap();
		genTestNodes();

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
			// logTestSet();
			// logTestList();
			// logTestLinkedList();
			// logTestMap();
			// logTestMultiMap();
			// Log(this.toLog());
			// logTestNodes();

			// aTransaction.Type T = aTransaction.Type.Deposit;
			// Log(T.getDirection() + " " + T.signum());
			// T = aTransaction.Type.Withdrawal;
			// Log(T.getDirection() + " " + T.signum());

			// aVector V = new aVector(1, 5, 7, 9);
			// V.append(3.1f);
			// Log(V);
			// Log("" + Maths.round(5.55f, 2));
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

	private static class ShutDownHook extends Thread {
		public void run() {
			Current.terminate();
		}
	}

	private void SEC0_TESTING() {
	}

	public void genTestSet() {
		S = new aSet<Integer>();
		S.append(1, 1);
		S.append(1);
		S.append(32);
		S.append(64);
		S.append(666);

	}

	public void genTestList() {
		L = new aList<Integer>();
		L.append(1, 1);
		L.append(1);
		L.append(32);
		L.append(64);
		L.append(666);

		L.insert(42, 1);
		L.remove(0);
		L.set(0, 100);

		// L.clear();
	}

	public void genTestLinkedList() {
		LL = new aLinkedList<Integer>();
		LL.append(1, 1);
		LL.append(1);
		LL.append(32);
		LL.append(64);
		LL.append(666);
		LL.remove(2);
		LL.insert(13, 3);
		LL.set(0, 100);

		// logTestLinkedList();
		// Log(">> " + LL.get(4));
		// LL.set(5, 777);
		// logTestLinkedList();
		// Log(LL.contains(404) + " / " + LL.contains(777));
		// LL.clear();

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

	public void genTestNodes() {
		this.N1 = new aNode("Node1");
		this.N2 = new aNode(42);
		this.N3 = new aNode(new aList<Float>(1f, 2f, 3f));
	}

	public void genTestFields() {
		aDataField<String> F1 = new aDataField<String>("Name", "PP");
		aDataField<Integer> F2 = new aDataField<Integer>("Age", 32);
		aDataField F3 = new aDataField("?", 42f);
		aDataField F4 = new aDataField(">>", F3);
		Log(F1);
		Log(F2);
		Log(F3);
		Log(F4);
		Log(">>>>>>>>>>>>>>");
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
		Log("     -0 " + L.getLast());
		Log("----");
	}

	public void logTestLinkedList() {
		Log("aLinkedList>>");
		Log("for{");
		Log(LL);
		Log("forEach[" + LL.getSize() + "]{");

		for (Object o : this.LL) {

			// Log("*" + o);
			aNode n = (aNode) o;
			Log("*[" + LL.indexOf(n) + "]" + o);
			if (n.has("NEXT")) {
				aNode next = ((aConnection) n.connections.get("NEXT")).target;
				Log("    <NEXT>=> [" + LL.indexOf(next) + "]" + next);
			}

		}

		// Log(" -0 "+LL.getLast());
		Log("----");
		// Log(LL.first.toLog());

		Log("________");
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
		// Log(MM.pull("A").get(2));
		Log(">" + MM.get("A", 2));
		Log("----");
		// MM.clear();
		// Log(MM);

	}

	public void logTestNodes() {
		// Log(N1 + " " + N1.get().toString());
		// Log(N2 + " " + N2.get().toString());
		// Log(N3 + " " + N3.get().toString());
		Log(N1.toLog());
		Log(N2.toLog());
		Log(N3.toLog());
		Log();
	}

	public aList BSort = new aList<Integer>();

	///////////////
	public void B_SortTest() {
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			BSort.append(rand.nextInt(100));
		}

		Log("--UNSORTED");
		Log(BSort);
		BubbleSorter BS = new BubbleSorter(BSort);
		BS.sortAscending();
		Log("--Ascending");
		Log(BSort);

		BS.sortDescending();
		Log("--Descending");
		Log(BSort);
	}

	private static class BubbleSorter {

		iCollection<Integer> subject;

		public int bubbleIndex = 0;
		private int bubbleNext = 0; // (bubbleIndex+1)%subject.getSize();

		private int min = Integer.MAX_VALUE;
		private int max = 0;

		public BubbleSorter(aList L) {
			this.subject = L;
		}

		public void sortAscending() {

			int tmp = 0;
			for (int i = 0; i < subject.getSize(); i++) {
				for (int j = 1; j < (subject.getSize() - i); j++) {
					if (subject.get(j - 1) > subject.get(j)) {
						tmp = subject.get(j - 1);
						subject.set((j - 1), subject.get(j));
						subject.set(j, tmp);
					}

				}
			}
		}

		public void sortDescending() {

			int tmp = 0;
			for (int i = 0; i < subject.getSize(); i++) {
				for (int j = 1; j < (subject.getSize() - i); j++) {
					if (subject.get(j - 1) < subject.get(j)) {
						tmp = subject.get(j - 1);
						subject.set((j - 1), subject.get(j));
						subject.set(j, tmp);
					}

				}
			}
		}

	}

}
