package com.Rev.P1_Core.Util;

import static com.Rev.P1_Core.AppUtils.*;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {

	public static final String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	public static String toName(String str) {
		String s = str.substring(0, 1).toUpperCase();
		String tr = str.substring(1);
		return s + tr;
	}

	public static String toMoney(float amt) {
		Locale locale = new Locale("en", "US");
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		return (currencyFormatter.format(amt));
	}

	public static boolean validAmount(String amount) {
		String alph = DefaultResources.ENGLISH_LETTERS;
		for (int i = 0; i < alph.length(); i++) {
			if (amount.contains(alph.substring(i, i + 1)))
				return false;
		}

		return true;
	}

	public static boolean validEmail(String emailAddress) {
		return patternMatches(emailAddress, emailRegexPattern);
	}
	

	public static boolean patternMatches(String emailAddress, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(emailAddress).matches();
	}

	//public static void main(String... args) {
		//String a = "25.01";
		//Log(a + " " + validAmount(a));
	//}

}
