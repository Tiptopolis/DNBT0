package Metatron.Core.Primitive.Util;

import java.util.Comparator;

import Metatron.Core.M_Utils;
import Metatron.Core.uChumpEngine;
import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.aType;
import Metatron.Core.Primitive.A_I.iEnum;
import Metatron.Core.Primitive.A_I.iMap;
import Metatron.Core.Primitive.A_I.iType;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aDictionary.D_Key;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.Utils.StringUtils;

public class _Types {

	public static aDictionary<aType> ALL;

	public static aType register(aType... t) {
		if (ALL == null)
			ALL = new aDictionary<aType>();

		for (aType T : t) {
			ALL.put("METATRON", T.label, T);
		}
		return null;
	}

	public static Object getA(Object context, String label) {
		return ALL.get(context, label);
	}

	
	public static Comparator<Object> isA(Object a,Object b)
	{
		return new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				
				if(M_Utils.instanceOf(o2.getClass()).test(o1.getClass()))
				return 0;
				if(o1 instanceof aNode)
				{
					if(o1.equals(o2))
						return 1;
				}
				
				
				return -1;
			}
			
		};
	}
	
	
	
	public static enum jType_Data {
		PRIMITIVE, STRUCT;

	}

	// java primitives
	public static enum jType implements iEnum<iType> {
		NULL(null, new D_Key("JAVA", "TYPE:NONNUMERIC"), null, true, jType_Data.PRIMITIVE, "null"),
		VOID(Void.class, new D_Key("JAVA", "TYPE:NONNUMERIC"), null, true, jType_Data.PRIMITIVE, "void"),
		BYTE(Byte.class, new D_Key("JAVA",
				"TYPE:NUMERIC"), (byte) 0, true, jType_Data.PRIMITIVE, "byte", "Byte", "b"),
		BOOLEAN(Boolean.class, new D_Key("JAVA", "TYPE:NONNUMERIC"), false, false, jType_Data.PRIMITIVE, "bool", "Boolean"),
		SHORT(Short.class, new D_Key("JAVA", "TYPE"), (short) 0, true, jType_Data.PRIMITIVE, "short", "Short", "s"),
		INTEGER(Integer.class, new D_Key("JAVA", "TYPE"), 0, true, jType_Data.PRIMITIVE, "int", "Integer", "i"),
		LONG(Long.class, new D_Key("JAVA", "TYPE"), 0l, true, jType_Data.PRIMITIVE, "long", "Long", "l"),
		FLOAT(Float.class, new D_Key("JAVA", "TYPE"), 0f, true, jType_Data.PRIMITIVE, "float", "Float", "f"),
		DOUBLE(Double.class, new D_Key("JAVA", "TYPE"), 0d, true, jType_Data.PRIMITIVE, "double", "Double", "d"),
		CHARACTER(Character.class, new D_Key("JAVA", "TYPE"), (char) 0, false, jType_Data.PRIMITIVE, "char",
				"Character");

		public static aMap<String, iType> ALL;
		public static aSet<jType> TYPES;
		private static aSet<jType> _Numeric = new aSet<jType>();
		

		protected final Class c;
		public final aSet<String> name;
		public final boolean numeric;
		protected final Object defaultVal;
		public aSet<D_Key> aliases;

		private jType(Class c, D_Key def, Object defVal, boolean num, jType_Data dType, String... names) {
			this.c = c;
			this.name = new aSet<String>();
			this.numeric = num;

			for (String n : names)
				this.name.append(n);

			this.defaultVal = defVal;
			reg(this);
		}

		private static void reg(jType t) {
			if (jType.ALL == null)
				jType.ALL = new aMap<String, iType>();
			if (TYPES == null)
				TYPES = new aSet<jType>();

			jType.TYPES.append(t);
			jType.ALL.put(t.name(), t);

		}

		public static _Map.Entry<jType, Object> getA(Object o) {
			if (o instanceof jType)
				return new _Map.Entry<jType, Object>(((jType) o), ((jType) o).defaultVal);

			if (o instanceof String)
				for (jType t : TYPES)
					if (StringUtils.contains(t.name, "" + o))
						return new _Map.Entry<jType, Object>(t, t.defaultVal);

			return null;
		}

		public static Object getNew(Object o) {
			if (o instanceof jType)
				return ((jType) o).defaultVal;

			if (o instanceof String)
				for (jType t : TYPES)
					if (StringUtils.contains(t.name, "" + o))
						return t.defaultVal;

			return null;
		}

		@Override
		public aMap<String, iType> getAll() {

			return jType.ALL;
		}

		public static aMap<String, iType> getItems() {
			return jType.ALL;
		}

		@Override
		public String label() {
			return this.name();
		}

		
		@Override
		public String toString() {
			return "<" + this.name() + ">";
		}

		@Override
		public void extend(iType sub)
		{
			
		}

		@Override
		public void appendAll(iType... entries) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public iType get(Integer index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer indexOf(Object member) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove(Integer at) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean contains(iType entry) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public <N, X> iMap<N, X> toMap() {
			// TODO Auto-generated method stub
			return null;
		}

		


	}
}
