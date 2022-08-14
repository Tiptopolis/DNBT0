package Metatron.Core.Primitive.A_I;

import Metatron.Core.Primitive.Struct.aMap;

public interface iEnum<E> extends iType<E>{

	aMap<String, E> getAll();
	
	
}
