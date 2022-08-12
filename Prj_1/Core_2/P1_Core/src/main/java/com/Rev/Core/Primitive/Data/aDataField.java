package com.Rev.Core.Primitive.Data;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.Primitive.aNode;
import com.Rev.Core.Primitive.aSet;

public class aDataField<Y> extends aNode<Y> {

	//table column
	public String label;
	public aDataType T;



	public aDataField(String label, aDataType type) {
		super();
		this.label = label;
		this.T = type;
		this.init(this.T);
	}

	public aDataField(String label, Y data) {
		super();
		this.label = label;
		if(data instanceof CharSequence)
			this.T = parseType(""+data);
		else
		this.T = parseType(data.getClass().getSimpleName());
		this.get = data;
	}
	
	

	private aDataType parseType(String type) {
		if (aDataType.NAN.is(type)) {
			return this.T = aDataType.NAN;
		}
		if (aDataType.STR.is(type)) {
			return this.T = aDataType.STR;
		}
		if (aDataType.INT.is(type)) {
			return this.T = aDataType.INT;
		}
		if (aDataType.FLT.is(type)) {
			return this.T = aDataType.FLT;
		}
		if (aDataType.OBJ.is(type)) {
			return this.T = aDataType.OBJ;
		}
		return this.T = aDataType.OBJ;
	}

	private void init(aDataType T) {
		if (T.is("STR"))
			this.set((Y) " ");

		if (T.is("INT")) {
			this.set((Y) new Integer(0));
		}
		if (T.is("FLT")) {
			this.set((Y) new Float(0f));
		}
		if (T.is("OBJ") || this.T.is("NAN")) {			
			//this.get = null;
			this.set(null);
		}

		//Log(" aDataField}>=-  " + T + "    " + this.T + " -> " + this.get());
	}

	@Override
	public String toString() {
		String f = "";
		if (this.get != this)
			f = this.get.toString();

		String s = this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode()) + "=["
				+ this.label + "](" + "<" + this.get.getClass().getSimpleName() + ">" + f
				+ ")";

		return s;
	}

	public static enum aDataType {
		NAN("nan", "null", "void"), OBJ("Object"),
		STR("str", "String", "char", "Character", "char[]", "CharSequence", "VARCHAR"), INT("int", "Integer", "i"),
		FLT("float", "double", "long", "short", "f", "d", "l", "s");

		private aSet<String> aliases = new aSet<String>();

		private aDataType(String... aliases) {
			this.aliases.append(aliases);
		}

		public boolean is(String s) {
			if (this.toString().equals(s) || this.aliases.contains(s) || this.aliases.contains(s.toUpperCase())
					|| this.aliases.contains(s.toLowerCase()))
				return true;
			else
				return false;
		}

		public Object of(String s) {
			if (NAN.aliases.contains(s) || NAN.aliases.contains(s.toUpperCase())
					|| NAN.aliases.contains(s.toLowerCase()))
				return null;
			if (OBJ.aliases.contains(s) || OBJ.aliases.contains(s.toUpperCase())
					|| OBJ.aliases.contains(s.toLowerCase()))
				return Object.class;
			if (STR.aliases.contains(s) || STR.aliases.contains(s.toUpperCase())
					|| STR.aliases.contains(s.toLowerCase()))
				return String.class;
			if (INT.aliases.contains(s) || INT.aliases.contains(s.toUpperCase())
					|| INT.aliases.contains(s.toLowerCase()))
				return Integer.class;
			if (FLT.aliases.contains(s) || FLT.aliases.contains(s.toUpperCase())
					|| FLT.aliases.contains(s.toLowerCase()))
				return Float.class;

			return null;
		}

	}

}