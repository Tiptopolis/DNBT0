package Metatron.Core.Primitive;

import static Metatron.Core.M_Utils.instanceOf;

import java.util.regex.Pattern;

import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.A_I.iContain;
import Metatron.Core.Primitive.A_I.iGroup;
import Metatron.Core.Primitive.A_I.iMap;
import Metatron.Core.Primitive.A_I.iNode;
import Metatron.Core.Primitive.A_I.iToken;
import Metatron.Core.Primitive.A_I.iType;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aDictionary.D_Key;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.Primitive.Struct.aSet;

public class aType<T> extends aNode<aSet<aToken<T>>> implements iType<T>/* implements iToken<aType> */ {

	// instances
	// derivative forms
	// properties, aspects, etc
	
	//Primary Form -> WORD
	//Path/Variant form WORD:OF
	//form adhoc paths from types using : as seperator

	public aSet<aTypeToken> Enum = new aSet<aTypeToken>();
	public aSet<T> instances = new aSet<T>();
	
	public aSet<aType> inherits;
	public aSet<aType> extensions;
	

	public aDictionary<Pattern> patterns;

	public static final aType Any;
	static {
		Any = new aType("Any");
	}

	public aType(String typeName) {
		super(typeName, new aSet<aToken<T>>());
		this.label = typeName;
		this.type = new aTypeToken(Any, typeName);
	}

	public static class aTypeToken extends aToken<_Map.Entry<aType, String>> {

		aType context;
		String[] words;

		public aTypeToken(aType context, String... strings) {
			this(true, context, "", strings);
		}

		public aTypeToken(boolean op, aType context, String delimiter, String... strings) {
			this.context = context;
			this.words = new String[strings.length];
			for (int i = 0; i < strings.length; i++)
				this.words[i] = strings[i];
		}

	}

	@Override
	public String toToken() {
		String tag = "";
	
		tag = "<" + this.label + ">";

		
		return tag;
	}
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public void append(T entry) {
		this.instances.append(entry);
		
	}

	@Override
	public void appendAll(T... entries) {
		for(T t : entries)
			this.append(t);
		
	}

	@Override
	public T get(Integer index) {
		return this.instances.get(index);
	}

	@Override
	public Integer indexOf(Object member) {
		return this.instances.indexOf(member);
	}

	@Override
	public void remove(Integer at) {
		this.instances.remove(at);
		
	}

	@Override
	public boolean contains(T entry) {
		return this.instances.contains(entry);
	}

	@Override
	public boolean isEmpty() {
		return this.instances.isEmpty();
	}

	@Override
	public int size() {
		
		return this.instances.size();
	}

	@Override
	public void clear() {
		this.instances.clear();
		
	}

	@Override
	public <N, X> iMap<N, X> toMap() {


		return null;
	}

}
