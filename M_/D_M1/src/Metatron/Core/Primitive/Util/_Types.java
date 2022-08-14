package Metatron.Core.Primitive.Util;

import Metatron.Core.uChumpEngine;
import Metatron.Core.Primitive.aType;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.Primitive.Struct.aSet;
import Metatron.Core.Utils.StringUtils;

public class _Types {

	public static aDictionary<aType> ALL;

	public static aType register(aType... t) {
		if (ALL == null)
			ALL = new aDictionary<aType>();

		for (aType T : t) {
			ALL.put("ANY", T.label, T);
		}
		return null;
	}

	public static Object getA(Object context, String label) {
		return ALL.get(context, label);
	}

	public static enum jType_Data {
		PRIMITIVE, STRUCT;

	}

	// java primitives
	public static enum jType {
		NULL(null, null, true, jType_Data.PRIMITIVE, "null"),
		VOID(Void.class, null, true, jType_Data.PRIMITIVE, "void"),
		BYTE(Byte.class, (byte) 0, true, jType_Data.PRIMITIVE, "byte", "Byte", "b"),
		BOOLEAN(Boolean.class, false, false, jType_Data.PRIMITIVE, "bool", "Boolean"),
		SHORT(Short.class, (short) 0, true, jType_Data.PRIMITIVE, "short", "Short", "s"),
		INTEGER(Integer.class, 0, true, jType_Data.PRIMITIVE, "int", "Integer", "i"),
		LONG(Long.class, 0l, true, jType_Data.PRIMITIVE, "long", "Long", "l"),
		FLOAT(Float.class, 0f, true, jType_Data.PRIMITIVE, "float", "Float", "f"),
		DOUBLE(Double.class, 0d, true, jType_Data.PRIMITIVE, "double", "Double", "d"),
		CHARACTER(Character.class, (char) 0, false, jType_Data.PRIMITIVE, "char", "Character");

		public static aDictionary<jType> ALL;
		public static aSet<jType> TYPES;
		private static aSet<jType> _Numeric = new aSet<jType>();

		public final aSet<String> name;
		public final boolean numeric;
		protected final Object defaultVal;

		private jType(Class c, Object defVal, boolean num, jType_Data dType, String... names) {
			this.name = new aSet<String>();
			this.numeric = num;
			for (String n : names)
				this.name.append(n);

			this.defaultVal = defVal;
			reg(this);
		}

		private static void reg(jType t) {
			if (ALL == null)
				ALL = new aDictionary<jType>();
			if (TYPES == null)
				TYPES = new aSet<jType>();

			jType.TYPES.append(t);
			// ALL.put(jType.class, "", t);

		}

		public static _Map.Entry<jType,Object> getA(Object o) {
			if (o instanceof jType)
				return new _Map.Entry(((jType) o),((jType) o).defaultVal);
				
			if (o instanceof String)
				for (jType t : TYPES)
					if (StringUtils.contains(t.name, "" + o))
						return new _Map.Entry(t,t.defaultVal);

			return null;
		}

		@Override
		public String toString() {
			return "<" + this.name() + ">";
		}
	}
}
