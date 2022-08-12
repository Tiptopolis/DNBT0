package com.Rev.Core._MonDexApp;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.App;
import com.Rev.Core.Console.Console;
import com.Rev.Core.Primitive.aLink;
import com.Rev.Core.Primitive.aMap;
import com.Rev.Core.Primitive.aMap.Entry;
import com.Rev.Core.Primitive.aNode;
import com.Rev.Core.Primitive.A_I.iCollection;
//import com.Rev.Core.Primitive.Data.DATA_CHEATZ;
import com.Rev.Core.Primitive.Data.aType;
import com.Rev.Core._Math.aVector;
import com.Rev.Core._MonDexApp.Data._Creature;

public class MonDexApp extends App {

	public MonDirector Management;

	private static aMap<String, aType> RegisteredTypes = new aMap<String, aType>(); //object is usage symbol

	public MonDexApp() {
		super();

	}

	@Override
	public void init() {
		super.init();
		Log("-<>-");
		gT1();
		this.Management = new MonDirector();
		//Log(this.getClass().getSimpleName());
		Log(">--<");
		Log(_Creature.Creature);		
		//Log(DATA_CHEATZ.buildArgsString(5));
		lT1();
		logDex();

		UI = new MonDexUI();

	}

	@Override
	public void dispose() {
		super.dispose();
		RegisteredTypes.clear();
	}

	//////////////////////////////////////
	aNode n1;
	aNode n2;
	aNode n3;
	aNode n4;
	aNode n5;

	public void gT1() {
		aVector a = new aVector(4, 0, 4);
		aType T = new aType(RegisteredTypes, "Thing", Object.class);
		n1 = new aNode("WomboChombo");
		n2 = new aNode(1);
		n3 = new aNode(a);
		n4 = new aNode(T);
		// n5 = new aDataField("TestValue", 4f);
	}

	public void lT1() {
		Log(n1);
		Log(n2);
		Log(n3);
		Log(n4.toLog());
		// Log(n5);
		// Log(aDataField.aDataType.OBJ.aliases());
		// for (iType t : aDataField.aDataType.FLT.aliases()) {
		// Log(t + " : Aliases");
		// Log(aDataField.aDataType.FLT.aliases().getValues());
		// }
		// Log("> "+((aDataField)n5).get());
		// Log();
		

		aType T = new aType(RegisteredTypes, "Thing", Object.class);
		aType T1 = new aType(RegisteredTypes, "ThingA", T);
		aType T2 = new aType(RegisteredTypes, "ThingB", T);
		
		Log(RegisteredTypes);
		Log("0______________");
		Log(T.toLog());
		
		/*for(Entry<String, iCollection<aNode>> l : T.links)
		{
			Log(l);
			
			for(Object o : l.getValue())
			{
				//Log("["+l.getValue().indexOf(o) + "]:"+o + "  " + o.getClass());
				aLink L = (aLink)o;
				Log("["+l.getValue().indexOf(o) + "]:"+L.toLog() );
			}
			Log(">>>>>>>");
		}*/
		Log("1______________");
		Log(T1.toLog());
		Log("2______________");
		Log(T2.toLog());
		Log("x______________");

		

	}

	aMap<String, aType> Dex;

	public void logDex() {
		Dex = new aMap<String, aType>();
		aType Mon = new aType(true, null, "Mon", aType.class);

		aType newMon = new aType(false,Dex, "BLB", Mon);
		newMon = new aType(false,Dex, "CHR", Mon);
		newMon = new aType(false,Dex, "SQR", Mon);

		Log(Mon.toLog());
		//for (Entry<String, aType> E : Dex) {
			//if (E.getValue() != null) {
				//Log(E);
			//}
		//}
		

		Log(Dex.get("BLB"));
		Log(Dex.keyOf(Dex.get("BLB")));
		Log(Dex.get("BLB").is(Dex.get("BLB")));
		Log(Dex.get("BLB").is(Mon));
		Log(Dex.get("BLB").is(Dex.get("CHR")));
		
		/*for(Entry<String, iCollection<aNode>> l : Mon.links)
		{
			Log(l);
			
			for(Object o : l.getValue())
			{
				//Log("["+l.getValue().indexOf(o) + "]:"+o + "  " + o.getClass());
				aLink L = (aLink)o;
				Log("["+l.getValue().indexOf(o) + "]:"+L.toLog() );
			}
			Log(">>>>>>>");
		}*/
	}

}
