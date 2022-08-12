package com.Rev.Core.Primitive.Data;

import static com.Rev.Core.AppUtils.*;


import com.Rev.Core.Primitive.aList;

public class DATA_CHEATZ {

	public static String buildArgsString(int length) {
		String args = "";

		args += "(";
		for (int i = 0; i < length; i++) {
			args += "?";
			if (i != length - 1)
				args += ",";
		}
		args += ")";

		return args;
	}

	// CSV array
	public static<T> aList<T> resolveCsvToList(T type, String input) {
		String starts = "({[<";
		String ends = ")}]>";
		int first = 0;
		int last = input.length() - 1;

		aList result = new aList();

		StringBuilder process = new StringBuilder(input);
		Log(input + "    <<<<<");
		if (starts.contains("" + input.charAt(first)) && ends.contains("" + input.charAt(last))) {
			process.deleteCharAt(last);
			process.deleteCharAt(first);
			Log(process + "    >>>>>");
		}
		last = process.length() - 1;
		
		String[] s = process.toString().split(",");
		for(int i =0; i < s.length-1; i++)
			result.append(s[i]);
		
		
		aList<T> Result = new aList<T>();
		
		return Result;
	}
	
	
	
}
