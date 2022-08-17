package Metatron.Core.Primitive.Data;

import static Metatron.Core.M_Utils.*;

import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.Data.BAK.aModel;
import Metatron.Core.Primitive.Struct._Array;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aLinkedSet;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.System.ECS.aEntity;

public class aTable<E> extends aList<aSet<aValue>> {

	//model is the table header
	//each item in the header is a list
	//interfaced with via 'Forms' or 'Models'
	
	//get ($,#)
	
	
	//__|$1|$2__________
	//#1| 
    //#2|
	
	
	protected aModel model;
	_Array<Integer> relFlags; 
	
	
	
	public aTable(aModel model)
	{
		this.model = model;
		this.relFlags = new _Array<Integer>();
		this.pre();
	}
	
	private void pre()
	{
		for(aValue V : this.model.get())
		{
			if(V.get() instanceof iCollection)
			{
				
			}
		}
	}
	
	public class T_Entry extends _Map.Entry<Integer, E>
	{
		private int type;
		
		
		
		T_Entry(Integer k, E v)
		{
			super(k,v);
		}
	}
}
