package Metatron.Core;

import static Metatron.Core.M_Utils.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.System.aShell;
import Metatron.Core.System.uApp;
import Metatron.Core.System.A_I.iApplet;
import Metatron.Core.Utils.iCypher;

public class uChumpEngine extends uApp {

	// main loop
	// tracks main window context

	public boolean running = false;

	public static jVM_SysData HostSystemData;
	public static M_Gfx GFX;
	
	
	float current = System.nanoTime();
	
	aShell S1;
	aShell S2;

	static {
		HostSystemData = new jVM_SysData();
		GFX = new M_Gfx();
	}

	@Override
	public void create() {
		this.running = true;
		// initialize REPL
		
		
		while(this.isActive())
		{
			
			deltaTime = (System.nanoTime() - current) / 1000000000f;
			current = System.nanoTime();
			
			
			
			this.step(this.deltaTime);
			
			
		}

	}

	@Override
	public void step(Float delta) {
		// REPL step
		Log("!" + delta);
		Object o =(iCypher.fillPermutations(100,100,100));
		Log(this.deltaTime);
	}

	@Override
	public void resize(Number... basis) {

	}

	@Override
	public aVector size() {
		return null;
	}

	public boolean isActive()
	{
		return this.running;
	}
	
	public static class jVM_SysData{
		
		static String userRootPath = "";
		static String hostOS = "<?>";
		public static aMultiMap SystemProperties;
		
		static {
			hostOS = System.getProperty("os.name").toUpperCase();			 
			SystemProperties = new aMultiMap();
			
			
			for(java.util.Map.Entry o : System.getProperties().entrySet())
				SystemProperties.put(o.getKey(), o.getValue());
			
			
			
			
			
				
		}
		
		public static String logSystemProps()
		{
			return SystemProperties.toLog();
		}
		
	}
	
}
