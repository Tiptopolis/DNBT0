package Metatron.Core._M;

import Metatron.Core.Math.Primitive.aVector;

public class M_Gfx {

	//replaces Gdx.graphics. for Camera projection etc
	//w/h of main render context
	public static aVector<Integer> ScreenSize = new aVector<Integer>(0,0);
	
	public static int getWidth()
	{
		return ScreenSize.get(0).intValue();
	}
	public static int getHeight()
	{
		return ScreenSize.get(1).intValue();
	}
}
