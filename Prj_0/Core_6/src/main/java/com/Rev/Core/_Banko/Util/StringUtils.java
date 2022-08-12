package com.Rev.Core._Banko.Util;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {

	
	
	public static String toName(String str)
	{
		String s = str.substring(0,1).toUpperCase();
		String tr = str.substring(1);
		return s+tr;
	}
	
	public static String toMoney(float amt)
	{
		Locale locale = new Locale("en", "US");      
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		return(currencyFormatter.format(amt));
	}
	
}
