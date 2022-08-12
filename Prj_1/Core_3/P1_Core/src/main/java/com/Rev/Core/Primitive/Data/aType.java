package com.Rev.Core.Primitive.Data;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.Primitive._N;
import com.Rev.Core.Primitive.aMap;
import com.Rev.Core.Primitive.aMap.Entry;
import com.Rev.Core.Primitive.aNode;
import com.Rev.Core.Primitive.aSet;
import com.Rev.Core.Primitive.A_I.iCollection;
import com.Rev.Core.Primitive.A_I.iMap;

public class aType extends aNode<aType> implements iType {

	protected aSet<aType> inherits;

	public String label = "";
	protected aSet<String> aliases;
	// protected Class of;
	protected aType of;
	protected aMap<String, iType> variants; // enumerated instances with pre-defined overrides
	protected aMap<String, iType> extensions; // sub-classes, extensions

	protected iMap context;
	/*
	 * public aType(iMap defineIn, String as) { this.label = as; this.aliases = new
	 * aSet<String>(); this.of = aType.class; this.get = this; this.inherits = new
	 * aSet<aType>(); defineIn.put(this,this.of); }
	 */

	public aType(String as, Object of) {
		this.label = as;
		this.aliases = new aSet<String>();
		this.variants = new aMap<String, iType>();
		this.extensions = new aMap<String, iType>();
		this.inherits = new aSet<aType>();

		if (of == null)
			this.of = this;
		if (of instanceof aType && !of.equals(this)) {
			this.of = (aType) of;
			((aType) of).extend(this);
		}

		this.get = this;
	}

	public aType(iCollection defineIn, String as, Object of) {
		this.label = as;
		this.aliases = new aSet<String>();
		this.variants = new aMap<String, iType>();
		this.extensions = new aMap<String, iType>();
		this.inherits = new aSet<aType>();

		if (of == null)
			this.of = this;
		if (of instanceof aType) {
			this.of = (aType) of;
			((aType) of).extend(this);
		}

		this.get = this;

		defineIn.append(this);
	}

	public aType(iMap defineIn, String as, Object of) {
		//true = RHS
		//false = LHS
		this(true, defineIn, as, of);
	}

	public aType(boolean KV, iMap defineIn, String as, Object of) {
		this.label = as;
		this.aliases = new aSet<String>();
		this.variants = new aMap<String, iType>();
		this.extensions = new aMap<String, iType>();
		this.inherits = new aSet<aType>();

		if (of == null)
			this.of = this;
		if (of instanceof aType) {
			this.of = (aType) of;
			((aType) of).extend(this);
		}

		this.get = this;

		if (defineIn != null) {
			this.context = defineIn;
			if (KV)
				defineIn.put(this, this.label);
			else
				defineIn.put(this.label, this);
		}
	}

	public aType(boolean KV, iMap defineIn, String as, Object of, Object defaultValue) {
		this.label = as;
		this.aliases = new aSet<String>();
		this.variants = new aMap<String, iType>();
		this.extensions = new aMap<String, iType>();
		this.inherits = new aSet<aType>();

		if (of == null)
			this.of = this;
		if (of instanceof aType) {
			this.of = (aType) of;
			((aType) of).extend(this);
		}

		this.get = this;

		if (defineIn != null) {
			this.context = defineIn;
			if (KV)
				defineIn.put(this, defaultValue);
			else
				defineIn.put(defaultValue, this);
		}

	}

	public aType(boolean KV, iMap<aType, String> defineIn, String as, aType of, Object defaultValue,
			String... aliases) {
		this(KV, defineIn, as, of, defaultValue);
		this.aliases.append(aliases);
	}

	public aType inherit(aType parentType) {

		if (!parentType.extensions.containsValue(parentType))
			parentType.extend(this);

		return this;
	}

	public aType extend(String subType, Object defaultValue, String... aliases) {
		boolean KV = false; // flimsy, real flimsy
		if (this.context.getKeyType() == aType.class) {
			KV = false;
		}
		if (this.context.getValueType() == aType.class)
			KV = true;
		aType newType = new aType(KV, this.context, subType, this, defaultValue, aliases);
		return newType;
	}

	public aType extend(aType subType) {
		// extends this type with subTyp
		boolean KV = false; // flimsy, real flimsy

		if (subType.of == null)
			subType.of = this;
		this.extensions.put(subType.label, subType);
		subType.inherits.append(this);
		
		this.link("Covers", subType, this);
		subType.link("Inherits", this, this);
		
		return subType;

	}

	@Override
	public boolean is(String of) {
		if (this.label.equals(of) || this.label.toUpperCase().equals(of.toUpperCase()) || this.toString().equals(of)
				|| this.aliases.contains(of) || this.aliases.contains(of.toUpperCase())
				|| this.aliases.contains(of.toLowerCase()))
			return true;
		else
			return false;
	}

	@Override
	public boolean is(Object of) {
		if (of instanceof Class)
			return this.is(((Class) of).getSimpleName());

		if (of instanceof aType)
			return (this == (((aType) of)) || this.isOf(((aType) of)));

		if (of instanceof _N)
			return this.is(((_N) of).Label());

		return this.is(of.getClass().getSimpleName());
	}

	// @Override
	// public Class of() {
	// if (this.of == null)
	// return Class.class;

	// return this.of;
	// }

	@Override
	public aType of() {

		return this.of;
	}

	//////
	// .CONTAINS

	@Override
	public boolean isOf(String n) {
		if (this.aliases.contains(n))
			return true;
		for (aType t : this.inherits) {
			// if (t.of.getSimpleName().equals(n) ||
			// t.of.getSimpleName().toUpperCase().equals(n.toUpperCase()))
			if (t.of != null)
				if (t.of.label.equals(n) || t.of.label.toUpperCase().equals(n.toUpperCase()))
					return true;
			return true;
		}
		return false;
	}

	public boolean isOf(aType t) {
		return this.inherits.contains(t);
	}

	public boolean isOf(Class c) {
		for (aType t : this.inherits) {
			if (t.of.equals(c))
				return true;
		}
		return false;
	}

	@Override
	public boolean isOf(Object of) {

		return this.inherits.contains(of);
	}

	// is x a subtype of this?
	public boolean covers(String s) {
		// aliases
		// variants
		// extensions
		if (s == null)
			return this.covers("null");

		if (this.aliases.contains(s) || this.variants.containsKey(s) || this.extensions.containsKey(s))
			return true;

		for (Object E : this.variants.getValues()) {
			if (E instanceof aType) {
				aType T = (aType) E;
				if (T.covers(s))
					return true;

			}
		}

		for (Object E : this.extensions.getValues()) {
			if (E instanceof aType) {
				aType T = (aType) E;
				if (T.covers(s))
					return true;

			}
		}

		return false;
	}

	public boolean covers(Class c) {
		for (String s : this.aliases) {
			if (s.equals(c.getSimpleName()))
				return true;
		}
		for (Entry<String, iType> e : this.variants.getEntries()) {
			if (e.getValue() instanceof aType)
				if (((aType) e.getValue()).isOf(c))
					return true;
		}
		for (Entry<String, iType> e : this.extensions.getEntries()) {
			if (e.getValue() instanceof aType)
				if (((aType) e.getValue()).isOf(c))
					return true;
		}

		return false;
	}

	public boolean covers(aType t) {

		if (this.variants.containsValue(t) || this.extensions.containsValue(t))
			return true;

		return false;
	}

	public boolean covers(Object o) {

		if (o == null)
			return this.covers("null");
		if (o instanceof aNode) {
			aNode n = (aNode) o;
			return this.covers(n.get());
		}

		return this.covers(o.toString());
	}

	// .CONTAINS
	///////

	@Override
	public aSet<String> aliases() {
		return this.aliases;
	}

	@Override
	public String Label() {
		return this.label;
	}

	@Override
	public String toString() {
		String f = "";
		if (this.of == null && of != this)
			f = "";
		else if (this.inherits.getSize() == 1)
			f = ":<" + this.inherits.get(0).label + ">";
		else
			f = ":<" + this.of.label + ">";
		// f = ":<"+this.of.getSimpleName()+">";

		String s = "<" + this.label + ">" + f;

		return s;
	}

	@Override
	public String toLog() {
		String log = this.toString() + "\n";
		log += " ~{" + this.aliases + "}\n";
		log += " :{" + this.inherits + "}\n";
		log += " >{" + this.variants + "}\n";
		log += " _{" + this.extensions + "}\n";
		//log += " O{" + this.links + "}\n";
		log+="(-)CONNECTIONS\n";
		log += this.logLinksLong();
		return log;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof String) {
			return this.label.equals(other);
		}

		return this.is(other) || this.isOf(other);
	}

}
