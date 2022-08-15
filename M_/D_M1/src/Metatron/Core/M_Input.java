package Metatron.Core;

import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.System.A_I.iDevice;
import Metatron.Core.System.ECS.FSM.iState;
import Metatron.Core.System.Event.iEvent;
import Metatron.Core.System.Event.iEventHandler;

public class M_Input {
	
	
	
	public aMap<iDevice, iState> devices;
	
	public aMultiMap<iDevice, iEventHandler> listeners;
	
	public void update(float deltaTime)
	{
		
	}

	
	public static class Mouse implements iDevice
	{

		@Override
		public aMultiMap<iState, iEvent> modes() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public iState mode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void mode(iState set) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
