package Metatron.Core.Primitive;

import java.util.regex.Pattern;

import Metatron.Core.Primitive.A_I.iContain;
import Metatron.Core.Primitive.A_I.iNode;
import Metatron.Core.Primitive.A_I.iToken;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aDictionary.D_Key;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.Primitive.Struct.aSet;

public class aType<T> extends aNode<aSet<aToken<T>>>/* implements iToken<aType>*/{
	
	// instances
	// derivative forms
	// properties, aspects, etc
	
	public aSet<aTypeToken> Enum = new aSet<aTypeToken>();	
	public aSet<T> instances = new aSet<T>();
	
	public aDictionary<Pattern> patterns;
	
	public static final aType Any;
	static {
		Any = new aType("Any");
	}
	
	public aType(String typeName)
	{
		super(typeName, new aSet<aToken<T>>());
		this.type = new aTypeToken(Any,typeName);
	}
	
	
	

	
	public static class aTypeToken extends aToken<_Map.Entry<aType, String>>{
		
		aType context;
		String[] words;
		
		public aTypeToken(aType context, String...strings) {
			this(true, context,"", strings);
		}
		
		public aTypeToken(boolean op, aType context,String delimiter, String...strings)
		{
			this.context = context;
			this.words = new String[strings.length];
			for(int i =0; i < strings.length; i++)
				this.words[i]=strings[i];
		}
		
	}


	
	
}
