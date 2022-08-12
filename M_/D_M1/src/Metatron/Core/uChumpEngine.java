package Metatron.Core;

import static Metatron.Core.M_Utils.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import Metatron.Core.Math.N_Operator;
import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.A_I.iNode;
import Metatron.Core.Primitive.Struct._Map.Entry;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.System.aShell;
import Metatron.Core.System.uApp;
import Metatron.Core.System.A_I.iApplet;
import Metatron.Core.System.COM.Console.aConsole;
import Metatron.Core.System.ECS.FSM.aState;
import Metatron.Core.Utils.iCypher;

public class uChumpEngine extends uApp {

	// main loop
	// tracks main window context

	// lang-> {fn,evt,val}

	public boolean running = false;

	public static aConsole M_Console;

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

	@Override
	public void create() {

		S1 = new aShell("S1", this.get());
		S2 = new aShell("S2", this.get());
		S1.shared.put("-o-", 0);

		H = S1;
		O = S2;

		this.running = true;

		M_Console = new aConsole(this);

		// initialize REPL

		while (this.isActive()) {

			deltaTime = (System.nanoTime() - current) / 1000000000f;
			current = System.nanoTime();

			try {
				this.next();
			} catch (RuntimeException r) {
				Log(r.getStackTrace());
				// Log(Thread.currentThread().getStackTrace());
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
			Log("# "+P.hashCode());
			// Log(S1.get("png"));
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
