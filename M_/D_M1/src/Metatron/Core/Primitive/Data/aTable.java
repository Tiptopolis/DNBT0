package Metatron.Core.Primitive.Data;

import static Metatron.Core.M_Utils.*;

import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.Struct.aLinkedSet;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.System.ECS.aEntity;

public class aTable extends aNode<aLinkedSet<aModel>> {

	//model is the table header
	//each item in the header is a list
	//interfaced with via 'Forms' or 'Models'
	
	//get (#,$)
	
	protected aModel model;
	
	public aTable()
	{
		super("NewTable",new aLinkedSet<aModel>());
	}

	public aTable(String label, aModel m)
	{
		super(label,new aLinkedSet<aModel>());
		this.model = m;
	}
	
	public aTable(String label, aValue... vals)
	{
		super(label,new aLinkedSet<aModel>());
		this.model = new aModel(label+"_MDL",vals);
	}
	
}
