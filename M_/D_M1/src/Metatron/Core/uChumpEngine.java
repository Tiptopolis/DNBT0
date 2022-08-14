package Metatron.Core;

import static Metatron.Core.M_Utils.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import Metatron.Core.Math.N_Operator;
import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Math.Primitive.fromGdx.Gfx.Color;
import Metatron.Core.Math.Util.aVectorUtils;
import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.aType;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.A_I.iNode;
import Metatron.Core.Primitive.Data.aModel;
import Metatron.Core.Primitive.Data.aTable;
import Metatron.Core.Primitive.Impl._Chord;
import Metatron.Core.Primitive.Struct._Map.Entry;
import Metatron.Core.Primitive.Util._Types;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aLinkedList;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.System.aShell;
import Metatron.Core.System.uApp;
import Metatron.Core.System.A_I.iApplet;
import Metatron.Core.System.COM.Console.aConsole;
import Metatron.Core.System.ECS.FSM.aState;
import Metatron.Core.System.GUI.aFrame;
import Metatron.Core.Utils.StringUtils;
import Metatron.Core.Utils.iCypher;
import Metatron.W_CMD.WindowsConsoleAdapter;

public class uChumpEngine extends uApp {

	// main loop
	// tracks main window context

	// lang-> {fn,evt,val}
	public static uChumpEngine CORE;
	public boolean running = false;
	public static aFrame MainFrame; //transparent background with app.clickout event

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

	aDictionary<String> D;

	aModel door;
	aTable doors;
	
	public uChumpEngine()
	{
		CORE = this;
	}

	@Override
	public void create() {
		CMD = WindowsConsoleAdapter.WIN;
		 initMainRenderFrame();
		

		S1 = new aShell("S1", this.get());
		S2 = new aShell("S2", this.get());
		S1.shared.put("-o-", 0);

		H = S1;
		O = S2;

		this.running = true;

		M_Console = new aConsole(this);
		door = new aModel("Door", new aValue("DIMENSIONS", new aVector(0f).resize(4)),
				new aValue("COLOR", new Color(1, 1, 1, 1)));
		Log(door.toLog());

		Log("---");
		Log(door.get("color"));
		// Log(1/0);

		// initialize REPL
		// Log(CMD.Main.directory());
		// Map<String, String> environment = CMD.Main.environment();
		// environment.forEach((key, value) -> Log(key + value));
		_Chord X = new _Chord("A", "B", "A&B");
		Log(X);
		Log(X.parse());
		Log(X.parseAgainst("A B A&B"));

		aType an = new aType("Archetype");

		Log(an.toLog());

		Log(_Types.ALL.toLog());
		// Log(_Types.ALL.size());
		// Log(_Types.ALL.keys.size());

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

		aList<aVector> Permutes_8Bit = new aList<aVector>(iCypher.fillPermutations(2, 2, 2, 2, 2, 2, 2, 2));
		// Log(iCypher.fillPermutations(2, 2, 2, 2, 2, 2, 2, 2));
		// Log(Permutes_8Bit.toLog());

		for (aVector v : Permutes_8Bit)
			Log(v.toJoinedString());
		Log("\n\n\n");

		/*
		 * for (aVector v : B) Log(v.toJoinedString());
		 */

		// aList<aVector> B = (aList<aVector>)
		// aVectorUtils.sortElementsAscending(Permutes_8Bit.cpy()); //Z-patterning?
		// aList<aVector> B = (aList<aVector>)
		// aVectorUtils.sortSumAscending(Permutes_8Bit.cpy());

		aList<aVector> B = (aList<aVector>) aVectorUtils.sortMagnitudeAscending(Permutes_8Bit.cpy()); // normal binary
																										// lol

		for (aVector v : B) {
			Log(v.toJoinedString());
			// if(Permutes_8Bit.get(B.indexOf(v))!= v)
			// Log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}

		Log(iCypher.rdxComp(iCypher._HEX, 0) + " -> " + iCypher.rdxComp(iCypher._HEX, 15));

		////Log(1 / 0);

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
			Log(S1.toLog());
			Log(S2.toLog());
			// Log("!" + CMD);
			// M_Console.input(":LOG");
		}

	}

	@Override
	public void resize(Number... basis) {

	}

	@Override
	public void dispose() {
		super.dispose();
		Log("X_X");
		System.exit(0);
	}

	@Override
	public aVector size() {
		return null;
	}

	public boolean isActive() {
		return this.running;
	}

	
	private static void initMainRenderFrame()
	{
		aFrame f = new aFrame("uChumpEngine");
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setSize(250,250);
        f.setVisible(true);
        MainFrame = f;
        
        f.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
            	Log(" >>>>>> " + e);
               uChumpEngine.CORE.dispose();
               
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

}
