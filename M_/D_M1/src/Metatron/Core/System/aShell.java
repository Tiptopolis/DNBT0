package Metatron.Core.System;

import java.math.BigDecimal;
import java.math.BigInteger;

import Metatron.Core.Primitive.aNode;
import Metatron.Core.System.ECS.aEnvironment;
import Metatron.Core.System.Event.iEvent;
import Metatron.Core.System.Event.iEventHandler;

public class aShell extends aNode<aEnvironment> implements iEventHandler{

	
	//base endpoint
	//threaded env w/ sub-environments
	
	Thread shellThread;
	public static float deltaTime = 0f;
	public static BigInteger stepCount = BigInteger.valueOf(0);
	

	@Override
	public void handle(iEvent o) {
		// TODO Auto-generated method stub
		
	}
	
}
