package com.Rev.P1_Core.zCHEAT_CODEX;

import static com.Rev.P1_Core.AppUtils.Log;

import java.util.Random;

import com.Rev.P1_Core.Primitive.BubbleSorter;
import com.Rev.P1_Core.Primitive.aLink;
import com.Rev.P1_Core.Primitive.aLinkedList;
import com.Rev.P1_Core.Primitive.aList;
import com.Rev.P1_Core.Primitive.aMap;
import com.Rev.P1_Core.Primitive.aMultiMap;
import com.Rev.P1_Core.Primitive.aNode;
import com.Rev.P1_Core.Primitive.aSet;
import com.Rev.P1_Core.Primitive.A_I.iCollection;
import com.Rev.P1_Core.Primitive.Data.aDataField;

public class VariousTests {
	aSet<Integer> S = new aSet<Integer>();
	aList<Integer> L = new aList<Integer>();
	aLinkedList<Integer> LL = new aLinkedList<Integer>();
	aMap<String, String> M = new aMap<String, String>();
	aMultiMap<String, String> MM = new aMultiMap<String, String>();
	AbstractDoodad D1;

	aNode N1;
	aNode N2;
	aNode N3;

	public static VariousTests Testo;

	static {
		Testo = new VariousTests();
	}

	private VariousTests() {
		genTestSet();
		genTestList();
		genTestLinkedList();
		genTestMap();
		genTestMultiMap();
		genTestNodes();
	}

	public static void main(String...args)
	{
		Testo.LogThis();
	}
	
	public void LogThis() {

		// logTestSet();
		// logTestList();
		logTestLinkedList();
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
		L.setAt(0, 100);

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
		LL.setAt(0, 100);

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

	public void genAbstractDoodad() {
		this.D1 = new AbstractDoodad() {
			@Override
			public void init() {
				logTo();
			}
		};
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
				aNode next = ((aLink) n.links.get("NEXT")).target;
				Log("    <NEXT> => [" + LL.indexOf(next) + "]" + next);
			}

		}

		// Log(" -0 "+LL.getLast());
		Log("----");
		// Log(LL.first.toLog());
		
		Object o = LL.get(2);
		aNode n = (aNode)o;
		Log(LL.get(2) + "   " + LL.get(2) + "  " + o.getClass() + " " + LL.get(2)+ " " + n.get());
		Log(LL.getNode(2) + "   " + LL.getNode(2)+ "  " + LL.getNode(2).getClass());
		Log(LL.getData(2) + "   " + LL.getData(2)+ "  " + LL.getData(2).getClass());
		LL.clear();
		Log(LL + "  " + LL.getSize());

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

}
