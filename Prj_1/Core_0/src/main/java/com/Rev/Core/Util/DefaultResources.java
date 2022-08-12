package com.Rev.Core.Util;

public class DefaultResources {

	public static final String DEFAULT_FITTING = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

	public static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public static final String HexDigits = "0123456789ABCDEF";
	public static final String HEX = "0123456789ABCDEF";

	public static final String PERMISSIBLE_CHARS = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmno"
			+ "pqrstuvwxyz{|}~¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿À�?ÂÃÄÅÆÇÈÉÊËÌ�?Î�?�?ÑÒÓÔÕÖ×ØÙÚÛÜ�?Þßàá"
			+ "âãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿĀ�?ĂăĄąĆćĈĉĊċČ�?Ď�?�?đĒēĔĕĖėĘęĚěĜ�?ĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİı"
			+ "ĴĵĶķĹĺĻļĽľĿŀ�?łŃńŅņŇňŊŋŌ�?Ŏ�?�?őŒœŔŕŖŗŘřŚśŜ�?ŞşŠšŢţŤťŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽžſƒǺǻǼǽǾǿ"
			+ "ȘșȚțȷˆˇˉˋ˘˙˚˛˜�?΄΅Ά·ΈΉΊΌΎ�?�?ΑΒΓΔΕΖΗΘΙΚΛΜ�?ΞΟΠΡΣΤΥΦΧΨΩΪΫάέήίΰαβγδεζηθικλμνξοπ�?ςστυ"
			+ "φχψωϊϋό�?ώЀ�?ЂЃЄЅІЇЈЉЊЋЌ�?Ў�?�?БВГДЕЖЗИЙКЛМ�?ОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопр�?туфхц"
			+ "чшщъыь�?ю�?�?ёђѓєѕіїјљњћќ�?ўџѴѵ�?ґẀ�?ẂẃẄẅỲỳ–—‘’‚‛“�?„†‡•…‰‹›�?�₤€№™Ω℮�?↑→↓∆−√≈"
			+ "─│┌�?└┘├┤┬┴┼�?║╒╓╔╕╖╗╘╙╚╛╜�?╞╟╠╡╢╣╤╥╦╧╨╩╪╫╬■□▲▼○�?◦♀♂♠♣♥♦♪";

	public static final String BOX_DRAWING_SINGLE = "─│┌�?└┘├┤┬┴┼";
	public static final String BOX_DRAWING_DOUBLE = "�?║╔╗╚�?╠╣╦╩╬";
	public static final String BOX_DRAWING = "─│┌�?└┘├┤┬┴┼�?║╒╓╔╕╖╗╘╙╚╛╜�?╞╟╠╡╢╣╤╥╦╧╨╩╪╫╬";
	public static final String VISUAL_SYMBOLS = "�?↑→↓■□▲▼○�?◦♀♂♠♣♥♦♪";
	public static final String DIGITS = "0123456789";
	public static final String MARKS = "~`^'¨¯°´¸ˆˇˉˋ˘˙˚˛˜�?΄΅‘’‚‛";

	public static final String GROUPING_SIGNS_OPEN = "([{<«‘‛“‹";
	public static final String GROUPING_SIGNS_CLOSE = ")]}>»’’�?›";
	public static final String COMMON_PUNCTUATION = "!\"%&'*+,-./:;<>?•…–—";
	public static final String MODERN_PUNCTUATION = "@\\^_`|~¦©®™´№♀♂♪";
	public static final String UNCOMMON_PUNCTUATION = "§¶¨ªº¯°·¸¡¿·‚„†‡";
	public static final String TECHNICAL_PUNCTUATION = "#%'*+,-./<=>^|¬°µ±¹²³�?�¼½¾×÷‰№Ω℮∆−√≈";
	public static final String PUNCTUATION = COMMON_PUNCTUATION + MODERN_PUNCTUATION + UNCOMMON_PUNCTUATION
			+ TECHNICAL_PUNCTUATION + GROUPING_SIGNS_OPEN + GROUPING_SIGNS_CLOSE;
	public static final String CURRENCY = "$¢£¤¥₤€";
	public static final String SPACING = " ";

	public static final String ENGLISH_LETTERS_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ENGLISH_LETTERS_LOWER = "abcdefghijklmnopqrstuvwxyz";
	public static final String ENGLISH_LETTERS = ENGLISH_LETTERS_UPPER + ENGLISH_LETTERS_LOWER;

	public static final String LATIN_EXTENDED_LETTERS_UPPER = "À�?ÂÃÄÅÆÇÈÉÊËÌ�?Î�?�?ÑÒÓÔÕÖØÙÚÛÜ�?ÞĀĂĄĆĈĊČĎ�?ĒĔĖĘĚĜĞĠĢĤĦĨĪĬĮİĴĶĹĻĽĿ�?ŃŅŇŊŌŎ�?ŒŔŖŘŚŜŞŠŢŤŨŪŬŮŰŲŴŶŸŹŻŽǺǼǾȘȚẀẂẄỲßSFJ";
	public static final String LATIN_EXTENDED_LETTERS_LOWER = "àáâãäåæçèéêëìíîïðñòóôõöøùúûüýþ�?ăąćĉċ�?�?đēĕėęě�?ğġģĥħĩīĭįıĵķĺļľŀłńņňŋ�?�?őœŕŗřś�?şšţťũūŭůűųŵŷÿźżžǻǽǿșț�?ẃẅỳßſƒȷ";
	public static final String LATIN_EXTENDED_LETTERS = LATIN_EXTENDED_LETTERS_UPPER + LATIN_EXTENDED_LETTERS_LOWER;

	public static final String LATIN_LETTERS_UPPER = ENGLISH_LETTERS_UPPER + LATIN_EXTENDED_LETTERS_UPPER;
	public static final String LATIN_LETTERS_LOWER = ENGLISH_LETTERS_LOWER + LATIN_EXTENDED_LETTERS_LOWER;
	public static final String LATIN_LETTERS = LATIN_LETTERS_UPPER + LATIN_LETTERS_LOWER;

	public static final String GREEK_LETTERS_UPPER = "ΑΒΓΔΕΖΗΘΙΚΛΜ�?ΞΟΠΡΣΣΤΥΦΧΨΩΆΈΉΊΌΎ�?ΪΫΪΫ";

	public static final String GREEK_LETTERS_LOWER = "αβγδεζηθικλμνξοπ�?ςστυφχψωάέήίό�?ώϊϋ�?ΰ";

	public static final String GREEK_LETTERS = GREEK_LETTERS_UPPER + GREEK_LETTERS_LOWER;

	public static final String CYRILLIC_LETTERS_UPPER = "�?БВГДЕЖЗИЙКЛМ�?ОПРСТУФХЦЧШЩЪЫЬЭЮЯЀ�?ЂЃЄЅІЇЈЉЊЋЌ�?Ў�?Ѵ�?";
	public static final String CYRILLIC_LETTERS_LOWER = "абвгдежзийклмнопр�?туфхцчшщъыь�?ю�?�?ёђѓєѕіїјљњћќ�?ўџѵґ";
	public static final String CYRILLIC_LETTERS = CYRILLIC_LETTERS_UPPER + CYRILLIC_LETTERS_LOWER;

	public static final String LETTERS_UPPER = LATIN_LETTERS_UPPER + GREEK_LETTERS_UPPER + CYRILLIC_LETTERS_UPPER;
	public static final String LETTERS_LOWER = LATIN_LETTERS_LOWER + GREEK_LETTERS_LOWER + CYRILLIC_LETTERS_LOWER;
	public static final String LETTERS = LETTERS_UPPER + LETTERS_LOWER;
	public static final String LETTERS_AND_NUMBERS = LETTERS + DIGITS;

	static final String NEHE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\n" //
			+ "abcdefghijklmnopqrstuvwxyz\n1234567890 \n" //
			+ "\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*\u0000\u007F";

	public static final String VECTOR_LABELS = "xyzwabcdefghijklmnopqrstuv";
	
	public static final String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

}
