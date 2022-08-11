package Metatron.Core.System.ECS;

import java.util.function.Supplier;

import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aMap;

public class aComponent extends aToken<aNode> {

	public Supplier<aNode> Owner = () -> this.value;
	public aDictionary<aValue> data = new aDictionary<aValue>();

	public aComponent(String name, aNode owner) {
		if (owner.shared == null)
			owner.shared = new aMap<String, Object>();

		owner.shared.put("COMPONENTS", this);
	}

}
