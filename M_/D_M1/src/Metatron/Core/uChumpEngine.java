package Metatron.Core;

import static Metatron.Core._M.M_Utils.*;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.WindowConstants;

import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Math.Primitive.fromGdx.Gfx.Color;
import Metatron.Core.Math.Util._Constraint;
import Metatron.Core.Math.Util._Maths;
import Metatron.Core.Math.Util.aVectorUtils;
import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.aType;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.Data._Model;
import Metatron.Core.Primitive.Data.aTable;
import Metatron.Core.Primitive.Data.BAK.XaModel;
import Metatron.Core.Primitive.Impl._Chord;
import Metatron.Core.Primitive.Util._Types;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.System.aShell;
import Metatron.Core.System.uApp;
import Metatron.Core.System.COM.Console.aConsole;
import Metatron.Core.System.ECS.FSM.aState;
import Metatron.Core.System.Script._CommandCodex;
import Metatron.Core.System.UI.aFrame;
import Metatron.Core.System.UI.Utils.SwingUtils;
import Metatron.Core.Utils.StringUtils;
import Metatron.Core.Utils.iCypher;
import Metatron.Core._M.M_Gfx;
import Metatron.Core._M.M_Net;
import Metatron.Core._M.M_Sys;
import Metatron.W_CMD.WindowsConsoleAdapter;
import Metatron.X_._BF.aBF;
import Metatron.X_._BF.sux.XBF_Script;
import Metatron.X_._BF.sux.XaBF_Script;

public class uChumpEngine extends uApp {

	// main loop
	// tracks main window context

	// lang-> {fn,evt,val}
	public static uChumpEngine CORE;
	public boolean running = false;
	public static aFrame MainFrame; // transparent background with app.clickout event

	public static aConsole M_Console;
	public static WindowsConsoleAdapter CMD;

	public static jVM_SysData HostSystemData;
	public static M_Gfx GFX;

	float current = System.nanoTime();
	float ellapsed = 0;

	aShell S1;
	aShell S2;
	aShell H;
	aShell O;

	aState s0;
	aState s1;
	aState s2;

	static {
		HostSystemData = new jVM_SysData();
		GFX = new M_Gfx();
	}

	public uChumpEngine() {
		CORE = this;
	}

	@Override
	public void create() {
		M_Net mn = new M_Net();
		CMD = WindowsConsoleAdapter.WIN;
		initMainRenderFrame();

		S1 = new aShell("S1", this.get());
		S2 = new aShell("S2", this.get());
		S1.shared.put("-o-", 0);

		H = S1;
		O = S2;

		this.running = true;

		M_Console = new aConsole(this);

		T1();
		T2();
		// T3_BF();
		// T4();
		T5();

		Log(M_Sys.toLog());

		// M_Console.input(":TERMINATE");

		Log(CMD.toLog());
		D = new aDictionary<String>();
		D.put("DBTN", "" + 0, "RF-FRM");
		D.put("DBTN", "" + 1, "HMF");
		Log(D.toLog());

		while (this.isActive()) {

			deltaTime = (System.nanoTime() - current) / 1000000000f;
			current = System.nanoTime();

			try {
				this.next();
			} catch (RuntimeException r) {
				Log(r.getStackTrace());
				this.running = false;
			}

		}
		if (!this.running)
			this.dispose();

	}

	@Override
	public void next() {
		ellapsed += this.deltaTime;
		this.step(this.deltaTime);
	}

	@Override
	public void step(Float delta) {
		// Log("!");

		if (ellapsed >= .5f) {
			Object P = H.get("-o-");
			int p = (int) P;
			H.set("-o-", (int) H.get("-o-") + 1);

			// simple io port?
			if (p >= 5) {
				H.set("-o-", 0);
				O.takeFrom(H, "-o-");

				aShell o = O;
				aShell h = H;
				H = o;
				O = h;
			}
			ellapsed = 0f;
			// Log(SwingUtils.mousePos());
			// Log(S1.toLog());
			// Log(S2.toLog());
			// Log(M_Gfx.ScreenSize);

		}

	}

	@Override
	public void resize(Number... basis) {
		// Log(1/0);
		M_Gfx.ScreenSize = SwingUtils.getRootWindowSize();
	}

	@Override
	public void dispose() {
		super.dispose();
		Log("X_X");
		if (M_Net.Root!= null && M_Net.Root.isActive())
			try {
				M_Net.Root.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		System.exit(0);
	}

	@Override
	public aVector size() {
		return null;
	}

	public boolean isActive() {
		return this.running;
	}

	private static void initMainRenderFrame() {
		aFrame f = new aFrame("uChumpEngine");
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setSize(800, 480);
		f.setVisible(true);
		MainFrame = f;

		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Log(" >>>>>> " + e);
				uChumpEngine.CORE.dispose();

			}
		});

		f.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				uChumpEngine.CORE.resize(null);
			}
		});
	}

	public static class jVM_SysData {

		static String userRootPath = "";
		static String hostOS = "<?>";
		public static aMultiMap SystemProperties;

		static {
			hostOS = System.getProperty("os.name").toUpperCase();
			SystemProperties = new aMultiMap();

			for (java.util.Map.Entry o : System.getProperties().entrySet())
				SystemProperties.put(o.getKey(), o.getValue());

		}

		public static String logSystemProps() {
			return SystemProperties.toLog();
		}

	}

	///////////////////////////////////////
	// TESTS
	aDictionary<String> D;

	XaModel door;
	aTable doors;

	private void T1() {
		door = new XaModel("Door", new aValue("DIMENSIONS", new aVector(0f).resize(4)),
				new aValue("COLOR", new Color(1, 1, 1, 1)));
		Log(door.toLog());

		Log("---");
		Log(door.get("color"));

		Log(SwingUtils.mapComponentLocations(MainFrame).toLog());
		// Log(1/0);

		// initialize REPL
		// Log(CMD.Main.directory());
		// Map<String, String> environment = CMD.Main.environment();
		// environment.forEach((key, value) -> Log(key + value));
		_Chord X = new _Chord("A", "B", "A&B");
		Log(X);
		Log(X.parse());
		Log(X.parseAgainst("A B A&B"));
	}

	private void T2() {
		aType an = new aType("Archetype");

		Log(an.toLog());

		Log(_Types.ALL.toLog());
		// Log(_Types.ALL.size());
		// Log(_Types.ALL.keys.size());

		Log(_Types.getA("ANY", "Archetype"));
		Log();
		Log(_Types.jType.ALL);
		Log(_Types.jType.TYPES);
		Log("______________");
		Object x = _Types.jType.getA("int");
		Log(x + " :: " + x.getClass());
		Log(_Types.jType.getNew("int"));
		Log(_Types.jType.getItems());

		Log(new aNode(1).is(Integer.class));
		Log(new aNode(1).is(_Types.jType.getA("int")));
		Log(new aNode(1).is(_Types.jType.getA("Integer")));
		Log(new aNode(1.0).is(_Types.jType.getA("int")));

		Log(_Types.ALL.toLog());

		_Types.getA("JAVA", "int");
	}

	private void T3_BF() {
		Log("__________________________________________________________________________________________________________");
		// ++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.
		String b = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";

		Log(b);
		// aBF B = new aBF("++>+++++[<+>-]");

		aBF B = new aBF(b);

		B.execute();

		Log("-------------------------------");
		Log(B.getMemory());
		Log(":->:");
		Log("* " + B.lastOut);
		Log("* " + B.lastMemory);
		Log(B.toHexString());
		Log(B.toBinString());
		// Log(B.toC());
		Log();
	}

	private void T4() {

		/*
		 * aLinkedList<Integer> LL= new aLinkedList<Integer>(1,2,3,4,5,6,7,8,8,1,2,3);
		 * Log(LL.toLog());
		 * 
		 * aNode N1 = new aNode("N1",1); aNode N2 = new aNode("N2",2); aNode N3 = new
		 * aNode("N3",3); aNode N4 = new aNode("N4",4);
		 * 
		 * N1.link("-!", "NEXT", N2); N2.link("-!", "NEXT", N3); N3.link("-!", "NEXT",
		 * N4); N1.link("-!", "NEXT", N3); N1.link("-!", "NEXT", N4); Log(N1.toLog());
		 * Log(N1.links.toLog());
		 */

		Log(iCypher.hasCharsOf("THICC ASS HOES", "CHAOS"));

		Log("\n\n\n\n\n\n\n\n\n");

		Log(iCypher.permutation("GUAC"));
		Log(StringUtils.split("123456789123456789123456789666", 3));

		Log(StringUtils.backFill(8, "0", "a", "a", "a", "a", "a", "a", "a", "a"));

		Log(iCypher.rdxComp(16));

		/*
		 * aList<aVector> Permutes_8Bit = new aList<aVector>(iCypher.fillPermutations(2,
		 * 2, 2, 2, 2, 2, 2, 2));
		 * 
		 * 
		 * for (aVector v : Permutes_8Bit) Log(v.toJoinedString()); Log("\n\n\n");
		 * 
		 * 
		 * 
		 * aList<aVector> B = (aList<aVector>)
		 * aVectorUtils.sortMagnitudeAscending(Permutes_8Bit.cpy()); // normal binary //
		 * lol
		 * 
		 * for (aVector v : B) { Log(v.toJoinedString()); //
		 * if(Permutes_8Bit.get(B.indexOf(v))!= v) //
		 * Log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"); }
		 */

		Log(iCypher.rdxComp(iCypher._HEX, 0) + " -> " + iCypher.rdxComp(iCypher._HEX, 15));

		//// Log(1 / 0);
	}

	private void T5() {
		_Model M = new _Model("door");
		M.put("DIMENSIONS", new aVector());
		M.put("COLOR", new Color());
		M.put("OPEN", false);

		Log(M.toLog());
		_CommandCodex C = new _CommandCodex("DOOR_");
		iFunctor.Effect<_Model> F;

		F = (a) -> {
			boolean b = (boolean) a.read("OPEN");
			a.set("OPEN", !b);
			return a;
		};

		C.get().put(M, ">", F);
		Log(C.get().toLog());

		Log(M.toLog());

		C.getCommand(">").apply(M);
		Log(M.toLog());
		aValue V = new aValue("DIM", new aVector(0, 0, 0));
		Log(V.toEVA());
		Log();

		aValue<Number> tN = new aValue<Number>("", 1000);
		tN.apply(_Constraint.MIN(666));
		Log(tN.get());

	}

}
