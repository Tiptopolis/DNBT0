package Metatron.Core.Primitive.Data;

import Metatron.Core.Primitive.aLink;
import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.A_I.iCollection;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Utils.StringUtils;
import Metatron.Core.Primitive.Struct._Map.Entry;

public class _Model extends aMap<_Map.Entry<String, Class>, Object> {

	//label, class, defaultVal;
	//basic EVA model
	
	
	public _Model(String label) {
		super();
		
	}

	
	
	public Entry get(String label)
	{
		for(Entry<_Map.Entry<String, Class>, Object> E : this)
		{
			String key = E.getKey().getKey();
			if(StringUtils.isFormOf(label, key) || StringUtils.isFormOf(key,label))
				return E;
		}
		return null;
	}
		
	public void put(String label, Object val)
	{
		this.put(label,val.getClass(),val);
	}
	
	public void put(String label, Class type, Object val)
	{
		super.put(new Entry(label,type),val);
	}
	
	public void set(String label, Object val)
	{
		for(Entry<_Map.Entry<String, Class>, Object> E : this)
		{
			String key = E.getKey().getKey();
			if(StringUtils.isFormOf(label, key) || StringUtils.isFormOf(key,label))
				E.set(val);
		}
	}
	
	public Object rtn(String args)
	{
		Object out = null;//void lol
		String tmp = ""+args;
		
		//? -> is/has
		if(args.startsWith("?"))
		{
			boolean o = false;
			tmp=""+args.substring(0);
		}
		if(args.endsWith("?"))
		{
			boolean o = false;
			tmp=""+args.substring(0,args.length()-1);
		}
		
		
		return out;
	}
	
	
	@Override
	public String toString() {

		return this.toLog();
	}

	public String toLog() {
		String log = "<" + this.getClass().getSimpleName() + "> {\n";
		
		if (!this.isEmpty()) {
			log += "  [SHARED]{\n";
			for(Entry E : this) {		
				Entry K = (Entry)E.getKey();
				//log+= E+"\n";
				String i = "(",o=")";
				if(E.getValue() instanceof iCollection)
				{
					i="{";o="}";
				}
					log += "  * ["+K.getKey() + "] = <"+((Class)K.getValue()) .getSimpleName()+">(" + E.getValue()+")\n";
			}
			
		}
		log += "}\n";
		return log;
	}
	
	//Field State Token
	public static class FST extends aToken{
		public FST(Object k,Class v)
		{
			this.value=k;
			this.type=v;
		}
	}
}
