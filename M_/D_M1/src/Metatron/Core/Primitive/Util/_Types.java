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
import Metatron.Core.Primitive.Struct._Map.Entry;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aDictionary.D_Key;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aMultiMap;
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

	public static aType register(Object context, String label, aType... t) {
		if (ALL == null)
			ALL = new aDictionary<aType>();

		for (aType T : t) {
			ALL.put(context, label, T);
		}
		return null;
	}

	public static Object getA(Object context, String label) {
		return ALL.get(context, label);
	}

	public static Comparator<Object> isA(Object a, Object b) {
		return new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {

				if (M_Utils.instanceOf(o2.getClass()).test(o1.getClass()))
					return 0;
				if (o1 instanceof aNode) {
					if (o1.equals(o2))
						return 1;
				}

				return -1;
			}

		};
	}

	public static aMultiMap<D_Key, iType> findType(String name) {
		aMultiMap<D_Key, iType> res = new aMultiMap<D_Key, iType>();
		for (Entry E : ALL) {
			if (E.getValue().equals(name) || E.getValue().equals(name.toLowerCase())
					|| E.getValue().equals(name.toUpperCase()))
				res.put((D_Key) E.getKey(), E.getValue());
		}
		return res;
	}

	public static aSet<iType> findType(Object context, String label, String name) {
		aMultiMap<D_Key, iType> filter = findType(name);
		aSet<iType> res = new aSet<iType>();
		for (Entry E : filter)
			if (((D_Key) E.getKey()).equals(context, label))
				res.append((iType) E.getValue());
		return res;
	}



	// java primitives
	public static enum jType implements iEnum<iType> {
		NULL(1, null, new D_Key("JAVA", "PRIMITIVE"), null, true, "Null", "null"),
		VOID(1, Void.class, new D_Key("JAVA", "PRIMITIVE"), null, true, "Void", "void"),
		BOOLEAN(1, Boolean.class, new D_Key("JAVA", "PRIMITIVE"), false, false, "Boolean", "bool"),
		BYTE(8, Byte.class, new D_Key("JAVA", "NUMBER"), (byte) 0, true, "Byte", "byte", "b"),
		CHARACTER(16, Character.class, new D_Key("JAVA", "PRIMITIVE"), (char) 0, false, "Character", "char"),
		SHORT(16, Short.class, new D_Key("JAVA", "NUMBER"), (short) 0, true, "Short", "short", "s"),
		INTEGER(32, Integer.class, new D_Key("JAVA", "NUMBER"), 0, true, "Integer", "int", "i"),
		LONG(64, Long.class, new D_Key("JAVA", "NUMBER"), 0l, true, "Long", "long", "l"),
		FLOAT(32, Float.class, new D_Key("JAVA", "NUMBER"), 0f, true, "Float", "float", "f"),
		DOUBLE(64, Double.class, new D_Key("JAVA", "NUMBER"), 0d, true, "Double", "double", "d"),
		OBJECT(Float.NaN, Object.class, new D_Key("JAVA", "STRUCT"), null, false, "Object", "obj"),
		STRING(Float.NaN, String.class, new D_Key("JAVA", "STRUCT"), "", false, "String", "str"),
		ARRAY(Float.NaN, Object[].class, new D_Key("JAVA", "STRUCT"), new Object[0], false, "Array", "[]", "...");

		public static aMap<String, iType> ALL;
		public static aSet<jType> TYPES;
		private static aSet<jType> _Numeric = new aSet<jType>();

		public final Number size;
		protected final Class c;
		public final aSet<String> name;
		public final boolean numeric;
		protected final Object defaultVal;
		public D_Key def;
		private final aType T;

		private jType(Number bitLen, Class c, D_Key def, Object defVal, boolean num, String... names) {
			this.size = bitLen;
			this.c = c;
			this.name = new aSet<String>();
			this.numeric = num;

			for (String n : names)
				this.name.append(n);

			this.def = def;
			this.defaultVal = defVal;

			String n = "NUMERIC";
			if (!num)
				n = "NONNUMERIC";

			this.T = new aType(def.getKey(), def.getValue(), label(), defaultVal);
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
		public int size() {
			// bit size
			return this.size.intValue();
		}

		@Override
		public String toString() {
			return "<" + this.name() + ">";
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
			return jType.TYPES.indexOf(member);
		}

		@Override
		public void remove(Integer at) {

		}

		@Override
		public boolean contains(iType entry) {

			return false;
		}

		@Override
		public boolean isEmpty() {

			return false;
		}

		@Override
		public void clear() {

		}

		@Override
		public <N, X> iMap<N, X> toMap() {

			return null;
		}

	}
}
