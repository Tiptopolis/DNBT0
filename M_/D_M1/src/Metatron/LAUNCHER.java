package Metatron;

import static Metatron.Core.M_Utils.*;

import javax.swing.SwingUtilities;

import Metatron.Core.uChumpEngine;
import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Math.Util.aVectorUtils;
import Metatron.Core.Primitive.Struct._RingBuffer;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Util.aStats;
import Metatron.Core.Utils.iCypher;

public class LAUNCHER {

	public static uChumpEngine CORE;

	public static void main(String... args) {

		if (CORE == null)
			CORE = new uChumpEngine();

		Log(args);
		Log("!!!");

		T1();

		CORE.create();
		
		// Log(uChumpEngine.HostSystemData.logSystemProps());

	}

	// Excel sheet as DAO

	public static void T1() {
		Log(new aVector(1, 2, 3, 1, 2, 3, 6, 6, 6, 9, 9, 9));
		Log(new aVector(1, 2, 3, 1, 2, 3, 6, 6, 6, 9, 9, 9).mul(2));

		aList<aVector> Permutes_8Bit = new aList<aVector>(iCypher.fillPermutations(2, 2, 2, 2, 2, 2, 2, 2));
		Log(iCypher.fillPermutations(2, 2, 2, 2, 2, 2, 2, 2));

		aList<aVector> B = (aList<aVector>) aVectorUtils.sortMagnitudeAscending(Permutes_8Bit.cpy()); // normal binary
																										// lol

		_RingBuffer<Integer> Oroboros = new _RingBuffer<Integer>();
		Oroboros.max = 8;
		// for (aVector v : B) {
		for (int i = 0; i < B.size(); i++) {
			aVector v = B.get(i);
			Oroboros.append((i % Oroboros.max) + 1);
			Log("[" + (i) + "] " + v.toJoinedString() + "        " + Oroboros);
			// if(Permutes_8Bit.get(B.indexOf(v))!= v)
			// Log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		}

		aStats st = new aStats(1, 2, 3, 1, 2, 3, 6, 6, 6, 9, 9, 9);
		Log(st.toLog());

	}

}
