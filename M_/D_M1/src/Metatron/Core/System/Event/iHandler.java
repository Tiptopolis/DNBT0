package Metatron.Core.System.Event;

import Metatron.Core.Primitive.iFunctor;

public interface iHandler<T> {

	public boolean handle(T o);
	
	

	public default boolean handle(String o)
	{
		
		return false;
	}
	

	
	public default boolean isActive()
	{
		return true;
	}
	

	
	public default void invoke(T o, iFunctor f)
	{
		f.accept(o);
		
	}
	


}