package Metatron.Core.System.A_I;

import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.System.ECS.FSM.iState;
import Metatron.Core.System.Event.iEvent;

public interface iDevice {

	//event generator
	//state -> event
	public aMultiMap<iState,iEvent> modes();
	public iState mode();
	public void mode(iState set);
}
