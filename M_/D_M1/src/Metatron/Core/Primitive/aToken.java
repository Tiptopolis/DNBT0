package Metatron.Core.Primitive;

import Metatron.Core.Primitive.A_I.iToken;
import Metatron.Core.Primitive.Struct._Object;
import Metatron.Core.Primitive.Struct.aDictionary;


public class aToken<T> extends _Object implements iToken<T>{

	
	public T value;
	public Object type;
	
	
	
	public static String getType(aToken T) {
		return "";
	}

	public String getName(aToken T) {
		return "";
	}



	@Override
	public T get() {
		return this.value;
	}

	@Override
	public String type() {
		return this.type.toString();
	}

	@Override
	public String toString() {
		return "[" + this.get() + "]<" + this.type() + ">";
	}
}