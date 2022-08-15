package Metatron.Core.System;

import static Metatron.Core.M_Utils.*;


import java.math.BigInteger;

import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.System.ECS.aEnvironment;
import Metatron.Core.System.ECS.FSM.iState;
import Metatron.Core.System.Event.iEvent;

public class aShell extends aNode<aEnvironment>  {

	// base endpoint
	// threaded env w/ sub-environments

	Thread shellThread;
	public static float deltaTime = 0f;
	public static BigInteger stepCount = BigInteger.valueOf(0);

	public aMap<String, iFunctor> Commands;
	public aDictionary<iState> FSM;

	public aShell() {
		this("aShell", new aEnvironment());
	}

	public aShell(String label) {
		this(label, new aEnvironment());
	}

	public aShell(String label, aEnvironment e) {
		super(label, e);
	}

	@Override
	public boolean handle(iEvent o) {

		// switch for functor type
		// add into iHandler method for handling (iEvent,iFunctor)
		Log(this.label+"  >>  " + o);
		return false;
		
	}

}
