package Metatron.Core.Primitive.Data;

import Metatron.Core.Primitive.aLink;
import Metatron.Core.Primitive.Struct._Map;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct._Map.Entry;

public class _Model extends aMap<_Map.Entry<String, Class>, Object> {

	//label, class, defaultVal;
	
	public String label;
	
	public _Model(String label) {
		super();
		this.label = label.toUpperCase();
	}

	public void put(String label, Object val)
	{
		this.put(label,val.getClass(),val);
	}
	
	public void put(String label, Class type, Object val)
	{
		super.put(new Entry(label,type),val);
	}
	
	
	@Override
	public String toString() {

		return this.label;
	}

	public String toLog() {
		String log = "<" + this.label + "> {\n";
		
		if (!this.isEmpty()) {
			log += "  [SHARED]{\n";
			for(Entry E : this) {		
				Entry K = (Entry)E.getKey();
				//log+= E+"\n";
				log += "  * "+K.getKey() + ":<"+((Class)K.getValue()) .getSimpleName()+">|[" + E.getValue()+"]\n";
			}
			
		}
		log += "}\n";
		return log;
	}
}
