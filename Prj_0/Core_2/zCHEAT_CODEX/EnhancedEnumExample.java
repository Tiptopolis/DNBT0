package Core.zCHEAT_CODEX;

public class EnhancedEnumExample {

	
	
	public static enum Priority implements ExampleInterface
	{
		NONE(0),
		LOW(1),
		MEDIUM(2),
		HIGH(3)
		;
		
		private int p;
		
		private Priority(int P)
		{
			this.p=P;
		}

		@Override
		public void doThing1() {
			System.out.println(this.p);
			
		}
		
	}
	
	public interface ExampleInterface
	{
		public default void doThing0()
		{
			this.doThing1();
		}
		
		public void doThing1();
	}
}
