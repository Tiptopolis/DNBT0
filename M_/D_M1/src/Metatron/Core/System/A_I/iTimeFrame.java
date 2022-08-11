package Metatron.Core.System.A_I;

import Metatron.Core.Primitive.A_I.iCycle;
import Metatron.Core.Primitive.A_I.iDisposable;

public interface iTimeFrame<T extends Number> extends iDisposable, iCycle {


	
	public boolean isActive();
	
	public void step(T delta);
	
	public void pause();
	public void resume();
	
	
	
}
