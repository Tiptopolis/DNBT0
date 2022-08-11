package Metatron.Core.System.Event;

public interface iHandler<T> {

	public void handle(T o);
	

	public default void handle(String o)
	{
		
	}
	
	public default boolean handled(T o)
	{
		this.handle(o);
		return false;
	}
	
	public default boolean handled(String o)
	{
		this.handle(o);
		return false;
	}
	
	public default boolean isActive()
	{
		return true;
	}
	

}