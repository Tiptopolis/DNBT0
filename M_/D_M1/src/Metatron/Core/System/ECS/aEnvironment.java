package Metatron.Core.System.ECS;

import java.math.BigDecimal;
import java.math.BigInteger;

import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.A_I.iToken;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.System.A_I.iTimeFrame;
import Metatron.Core.System.Event.iEvent;
import Metatron.Core.System.Event.iEventHandler;

public class aEnvironment extends aToken<aSet<iEventHandler>> implements iEventHandler {

	// ringbuffer-based timing coils
	// runtime env
	// updated by uCE / floating & integer time
	

	
	public aMultiMap<String, iEventHandler> updateThresholds; //members
	

	public aEnvironment() {
		super();		
		this.value = new aSet<iEventHandler>();
		this.type = aEnvironment.class;
		this.updateThresholds = new aMultiMap<String, iEventHandler>();
	}
	
	public aEnvironment(String name)
	{
		this();
		
	}


	@Override
	public boolean handle(iEvent o) {
		return false;
		
	}
	
	
	
	

}
