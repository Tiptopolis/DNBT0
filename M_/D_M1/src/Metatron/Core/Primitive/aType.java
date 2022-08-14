package Metatron.Core.Primitive;

import static Metatron.Core.M_Utils.instanceOf;

import java.util.function.Supplier;
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
import Metatron.Core.Primitive.Util._Types;

public class aType<T> extends aNode<aSet<aToken<T>>> implements iType<T>/* implements iToken<aType> */ {

	// instances
	// derivative forms
	// properties, aspects, etc

	// Primary Form -> WORD
	// Path/Variant form WORD:OF
	// form adhoc paths from types using : as seperator

	private T defaultNew;

	public aSet<T> instances = new aSet<T>();

	public aSet<aType> inherits;
	public aSet<aType> extensions;

	// public aDictionary<Pattern> patterns;

	Supplier isNumeric = () -> instanceOf(Number.class).test(this.defaultNew);

	public static final aType Any;
	static {
		Any = new aType("Any");
	}

	public aType(String typeName) {
		super(typeName, new aSet<aToken<T>>());
		this.label = typeName;
		this.type = typeName;// pull token from _Types?

		_Types.register(this);
	}

	public aType(String typeName, T defaultVal) {
		super(typeName, new aSet<aToken<T>>());
		this.label = typeName;
		this.type = typeName;// pull token from _Types?
		this.defaultNew = defaultVal;
		_Types.register(this);
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
	public void extend(iType sub)
	{
		
	}

	@Override
	public void appendAll(T... entries) {
		for (T t : entries)
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
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (other instanceof Class) {

			if (instanceOf(other).test(this.defaultNew))
				return true;
		}

		if (other instanceof aType) {
			aType T = (aType) other;
			if (this.label.equals(T.label))
				return true;
		}

		return false;
	}

	@Override
	public <N, X> iMap<N, X> toMap() {

		return null;
	}

}
