package com.Rev.Core.Primitive.Data;

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

}
