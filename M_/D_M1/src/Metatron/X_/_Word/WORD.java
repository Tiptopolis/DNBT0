package Metatron.X_._Word;

import Metatron.Core.Primitive.aToken;
import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Utils.StringUtils;
import Metatron.Core.Utils.iCypher;

public class WORD extends aToken<String> implements CharSequence {

	private static final long serialVersionUID = -4269343879802816385L;

	private aMap<String, String> caseForms;
	

	// bool map
	public WORD(String word) {
		super();
		this.type = String.class;
		this.value = word.toLowerCase();
		StringUtils.stripAll(word, iCypher._DEX);
		this.caseForms = new aMap<String, String>();
		this.caseForms.put("UPPER", word.toUpperCase());
		this.caseForms.put("LOWER", word.toLowerCase());
		this.caseForms.put("PROPER", StringUtils.toName(word));
	}

	@Override
	public int length() {
		return this.value.length();
	}

	@Override
	public char charAt(int index) {
		return this.value.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return this.value.subSequence(start, end);
	}

	public static enum SubType {
		NAME("NAME"), 
		NOUN("NOUN"), 
		TRA_VERB("TRANSITIVE VERB"), 
		NTR_VERB("INTRANSITIVE VERB"), 
		ADJ("ADJECTIVE"),
		ADV("ADVERB");

		public final String longName;

		private SubType(String s) {
			this.longName = s;
		}

	}

}
